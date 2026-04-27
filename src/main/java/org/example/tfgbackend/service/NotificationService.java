package org.example.tfgbackend.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.example.tfgbackend.model.Notificacion;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.NotificacionRepository;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private NotificacionRepository notificacionRepo;


    public void enviarAUsuario(Usuario usuario, String titulo, String cuerpo, String pantalla) {

        //Guardar en BD (siempre, aunque no tenga token FCM)
        try {
            Notificacion n = new Notificacion();
            n.setUsuario(usuario);
            n.setTitulo(titulo);
            n.setCuerpo(cuerpo);
            n.setPantalla(pantalla);
            n.setLeida(false);
            notificacionRepo.save(n);
        } catch (Exception e) {
            System.err.println("Error guardando notificación en BD: " + e.getMessage());
        }

        // Enviar push FCM (solo si tiene token)
        String token = usuario.getFcmToken();
        if (token == null || token.isBlank()) {
            System.out.println("FCM: Usuario " + usuario.getId()
                    + " sin token, notificación solo guardada en BD");
            return;
        }

        try {
            Message.Builder builder = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(titulo)
                            .setBody(cuerpo)
                            .build())
                    .putData("titulo", titulo)
                    .putData("cuerpo", cuerpo);

            if (pantalla != null) {
                builder.putData("pantalla", pantalla);
            }

            String messageId = FirebaseMessaging.getInstance().send(builder.build());
            System.out.println("FCM: Notificación enviada OK -> " + messageId);

        } catch (Exception e) {
            System.err.println("FCM: Error al enviar push a usuario "
                    + usuario.getId() + ": " + e.getMessage());

            if (e.getMessage() != null && (
                    e.getMessage().contains("not a valid FCM registration token") ||
                            e.getMessage().contains("Requested entity was not found") ||
                            e.getMessage().contains("UNREGISTERED"))) {
                usuario.setFcmToken(null);
                usuarioRepo.save(usuario);
                System.out.println("FCM: Token inválido eliminado para usuario " + usuario.getId());
            }
        }
    }


    public void notificarCitaCambioEstado(Usuario clienteUsuario, String estadoNuevo, String fecha) {
        String titulo;
        String cuerpo;

        switch (estadoNuevo) {
            case "CONFIRMADA" -> {
                titulo = "Cita confirmada";
                cuerpo = "Tu cita del " + fecha + " ha sido confirmada por el taller.";
            }
            case "CANCELADA" -> {
                titulo = "Cita cancelada";
                cuerpo = "Tu cita del " + fecha + " ha sido cancelada.";
            }
            default -> {
                titulo = "Actualización de cita";
                cuerpo = "Tu cita del " + fecha + " ha cambiado a: " + estadoNuevo;
            }
        }

        enviarAUsuario(clienteUsuario, titulo, cuerpo, "citas");
    }

    public void notificarReparacionCambioEstado(Usuario clienteUsuario, String estado,
                                                String vehiculo) {
        String titulo;
        String cuerpo;

        switch (estado) {
            case "PRESENTADA" -> {
                titulo = "Nueva reparación presentada";
                cuerpo = "Se ha creado una reparación para tu " + vehiculo
                        + ". Revisa los detalles y acéptala o recházala.";
            }
            case "EN_PROCESO" -> {
                titulo = "Reparación en curso 🛠";
                cuerpo = "Tu " + vehiculo + " ya está siendo reparado.";
            }
            case "TERMINADA" -> {
                titulo = "Reparación terminada";
                cuerpo = "La reparación de tu " + vehiculo
                        + " ha terminado. ¡Ya puedes valorarla!";
            }
            case "CONFIRMADA" -> {
                titulo = "Reparación confirmada";
                cuerpo = "La reparación de tu " + vehiculo
                        + " ha sido confirmada. Se ha generado la factura.";
            }
            default -> {
                titulo = "Actualización de reparación";
                cuerpo = "Tu reparación de " + vehiculo + " ha cambiado a: " + estado;
            }
        }

        enviarAUsuario(clienteUsuario, titulo, cuerpo, "reparaciones");
    }

    public void notificarFacturaGenerada(Usuario clienteUsuario, String numeroFactura,
                                         String total) {
        enviarAUsuario(clienteUsuario,
                "Nueva factura generada",
                "Factura " + numeroFactura + " por " + total + " €. "
                        + "Puedes pagarla desde la app.",
                "facturacion"
        );
    }

    public void notificarPagoConfirmado(Usuario clienteUsuario, String numeroFactura,
                                        String total) {
        enviarAUsuario(clienteUsuario,
                "Pago recibido ",
                "Tu pago de " + total + " € (factura " + numeroFactura
                        + ") se ha procesado correctamente.",
                "facturacion"
        );
    }
}