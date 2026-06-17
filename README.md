# AutoElite — Backend 

![Java](https://img.shields.io/badge/Java%2017-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=black)
![Stripe](https://img.shields.io/badge/Stripe-635BFF?style=flat-square&logo=stripe&logoColor=white)
![License](https://img.shields.io/badge/Uso-Académico-lightgrey?style=flat-square)

API REST en Spring Boot que da servicio a los clientes de **AutoElite**, un sistema de gestión integral para talleres mecánicos desarrollado como Proyecto de Fin de Grado (TFG) en DAM. Este backend es consumido tanto por la app Android (Kotlin + Jetpack Compose) como por el panel de administración en JavaFX.

## Funcionalidades

- Autenticación delegada en **Firebase Auth**: verificación de ID tokens en cada petición y propagación del rol (`CLIENTE`, `OFICINA`, `JEFE`) vía custom claims
- Gestión de citas con cálculo de franjas horarias disponibles (cada 30 min) y filtros por hoy / semana
- Gestión de reparaciones con máquina de estados: `PRESENTADA → EN_PROCESO / RECHAZADA → TERMINADA → CONFIRMADA`, con generación automática de factura al confirmarse
- Facturación con descarga en **PDF** (generado con OpenPDF) y cálculo de puntos de fidelización (1 punto por euro pagado)
- Pagos online con **Stripe** (Payment Intents + Ephemeral Keys para el Payment Sheet de la app)
- CRM de fidelización: acumulación y canjeo de puntos, con histórico de movimientos
- Notificaciones push vía **Firebase Cloud Messaging**, con persistencia en BD y limpieza automática de tokens inválidos
- Control de inventario de piezas con alertas de stock bajo
- Dashboard y estadísticas mensuales: ingresos, ticket medio, valoración media, rendimiento por mecánico
- Gestión de clientes, vehículos, mecánicos y empleados (alta, baja lógica, edición)
- Valoraciones de reparaciones terminadas/confirmadas

## Stack técnico

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot (Web, Data JPA, Security, Validation) |
| Base de datos | MySQL (vía `mysql-connector-j`) |
| Autenticación | Firebase Admin SDK |
| Notificaciones | Firebase Cloud Messaging |
| Pagos | Stripe Java SDK |
| Generación de PDF | OpenPDF |
| Boilerplate | Lombok |
| Tests | JUnit 5 + Mockito |

> Nota: el `pom.xml` incluye también el driver de PostgreSQL junto al de MySQL. Si solo usas una de las dos bases de datos, vale la pena quitar la dependencia que no corresponda para que el stack quede más claro de un vistazo.

## Arquitectura

El proyecto sigue una arquitectura por capas clásica de Spring Boot:

- **`controller`** — endpoints REST organizados por dominio (citas, clientes, reparaciones, facturas, pagos, piezas, mecánicos, empleados, notificaciones, CRM, dashboard, estadísticas, vehículos, valoraciones, auth)
- **`service`** — lógica de negocio, una clase de servicio por dominio
- **`repository`** — interfaces `JpaRepository` con consultas derivadas y `@Query` personalizadas
- **`model`** — entidades JPA (`Usuario`, `Cliente`, `Oficina`, `Vehiculo`, `Cita`, `Reparacion`, `ReparacionPieza`, `Pieza`, `Factura`, `Valoracion`, `Notificacion`, `PuntosHistorial`, `Mecanico`)
- **`dto`** — DTOs de entrada (`request`) y salida (`response`) para no exponer las entidades directamente
- **`util`** — mappers manuales entre entidad y DTO de respuesta
- **`config`** — configuración de seguridad, CORS, Firebase y Stripe
- **`exception`** — manejador global de excepciones (`@RestControllerAdvice`) con respuestas de error consistentes

## Seguridad

Cada petición pasa por un `FirebaseTokenFilter` que valida el ID token de Firebase enviado en el header `Authorization: Bearer`, extrae el UID y el rol (custom claim) y los inyecta en el contexto de seguridad. `SecurityConfig` deja las rutas administrativas (`/api/piezas/**`, `/api/mecanicos/**`, `/api/estadisticas/**`) restringidas a los roles `OFICINA` y `JEFE`, mientras que el resto de endpoints solo exige estar autenticado. Las sesiones son **stateless** (no hay sesión de servidor) y el registro inicial (`/api/auth/register`) es la única ruta pública.

## Endpoints principales

| Base path | Descripción |
|---|---|
| `/api/auth` | Registro, perfil propio, actualización de perfil, token FCM |
| `/api/citas` | CRUD de citas, horas disponibles, confirmar/cancelar/finalizar |
| `/api/clientes` | CRUD de clientes, perfil del cliente autenticado |
| `/api/vehiculos` | CRUD de vehículos, búsqueda por matrícula |
| `/api/reparaciones` | CRUD de reparaciones, cambio de estado, aceptar/rechazar (Android) |
| `/api/facturas` | Listado, descarga en PDF, marcar como pagada |
| `/api/pagos` | Creación y confirmación de Payment Intents de Stripe |
| `/api/piezas` | Inventario de piezas, ajuste de stock |
| `/api/mecanicos` | CRUD de mecánicos, activar/desactivar |
| `/api/empleados` | Gestión de personal de oficina |
| `/api/notificaciones` | Listado, no leídas, marcar como leídas |
| `/api/crm` | Canjeo de puntos de fidelización |
| `/api/dashboard` | Resumen operativo del día |
| `/api/estadisticas` | Estadísticas mensuales |
| `/api/valoraciones` | Valoraciones de reparaciones |

## Tests

Suite de más de 140 tests unitarios con JUnit 5 y Mockito que cubre prácticamente todos los servicios: casos felices, ausencia de recursos y fallos de concurrencia (`OptimisticLockingFailureException`) en cada operación de escritura relevante.

```bash
./mvnw test
```

## Configuración

Por seguridad, `application.properties` y el JSON de la cuenta de servicio de Firebase están excluidos del repositorio (`.gitignore`). Para levantar el proyecto en local, crea tu propio `src/main/resources/application.properties` con, al menos:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/autoelite
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

# Firebase (coloca tu firebase-service-account.json en src/main/resources/)
firebase.credentials.path=classpath:firebase-service-account.json

# Stripe
stripe.secret-key=sk_test_xxxxxxxx
stripe.publishable-key=pk_test_xxxxxxxx

# CORS (opcional, ya tiene un valor por defecto para desarrollo local)
cors.allowed-origins=http://localhost:3000,http://localhost:5173
```

## Instalación y ejecución

```bash
git clone <url-de-este-repositorio>
cd TFG-Backend
# crea application.properties como se indica arriba
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080`.

## Autoría

Proyecto de Fin de Grado — DAM, I.E.S. Laguna de Joatzel.

- **Sergio** — desarrollo del backend (parte) y panel de administración en JavaFX
- **Hiba Samraoui** — desarrollo del backend (parte) y app Android
- Tutor: Alain Fernández Fernández

## Licencia

Proyecto desarrollado con fines académicos. Sin licencia de uso comercial.
