package org.example.tfgbackend.service;

import org.example.tfgbackend.model.Notificacion;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.NotificacionRepository;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private UsuarioRepository mockUsuarioRepo;
    @Mock
    private NotificacionRepository mockNotificacionRepo;

    @InjectMocks
    private NotificationService notificationServiceUnderTest;

    @Test
    void testEnviarAUsuario() {
        // Setup
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        usuario.setFcmToken("fcmToken");

        // Run the test
        notificationServiceUnderTest.enviarAUsuario(usuario, "titulo", "cuerpo", "pantalla");

        // Verify the results
        verify(mockNotificacionRepo).save(any(Notificacion.class));
    }

    @Test
    void testEnviarAUsuario_NotificacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        usuario.setFcmToken("fcmToken");

        when(mockNotificacionRepo.save(any(Notificacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        notificationServiceUnderTest.enviarAUsuario(usuario, "titulo", "cuerpo", "pantalla");

        // Verify the results
    }

    @Test
    void testEnviarAUsuario_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        usuario.setApellidos("apellidos");
        usuario.setEmail("email");
        usuario.setFcmToken("fcmToken");

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> notificationServiceUnderTest.enviarAUsuario(usuario, "titulo", "cuerpo",
                "pantalla")).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testNotificarCitaCambioEstado() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        // Run the test
        notificationServiceUnderTest.notificarCitaCambioEstado(clienteUsuario, "estadoNuevo", "fecha");

        // Verify the results
        verify(mockNotificacionRepo).save(any(Notificacion.class));
    }

    @Test
    void testNotificarCitaCambioEstado_NotificacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockNotificacionRepo.save(any(Notificacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        notificationServiceUnderTest.notificarCitaCambioEstado(clienteUsuario, "estadoNuevo", "fecha");

        // Verify the results
    }

    @Test
    void testNotificarCitaCambioEstado_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> notificationServiceUnderTest.notificarCitaCambioEstado(clienteUsuario, "estadoNuevo",
                "fecha")).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testNotificarReparacionCambioEstado() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        // Run the test
        notificationServiceUnderTest.notificarReparacionCambioEstado(clienteUsuario, "estado", "vehiculo");

        // Verify the results
        verify(mockNotificacionRepo).save(any(Notificacion.class));
    }

    @Test
    void testNotificarReparacionCambioEstado_NotificacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockNotificacionRepo.save(any(Notificacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        notificationServiceUnderTest.notificarReparacionCambioEstado(clienteUsuario, "estado", "vehiculo");

        // Verify the results
    }

    @Test
    void testNotificarReparacionCambioEstado_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> notificationServiceUnderTest.notificarReparacionCambioEstado(clienteUsuario, "estado",
                "vehiculo")).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testNotificarFacturaGenerada() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        // Run the test
        notificationServiceUnderTest.notificarFacturaGenerada(clienteUsuario, "numeroFactura", "total");

        // Verify the results
        verify(mockNotificacionRepo).save(any(Notificacion.class));
    }

    @Test
    void testNotificarFacturaGenerada_NotificacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockNotificacionRepo.save(any(Notificacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        notificationServiceUnderTest.notificarFacturaGenerada(clienteUsuario, "numeroFactura", "total");

        // Verify the results
    }

    @Test
    void testNotificarFacturaGenerada_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> notificationServiceUnderTest.notificarFacturaGenerada(clienteUsuario, "numeroFactura",
                "total")).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testNotificarPagoConfirmado() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        // Run the test
        notificationServiceUnderTest.notificarPagoConfirmado(clienteUsuario, "numeroFactura", "total");

        // Verify the results
        verify(mockNotificacionRepo).save(any(Notificacion.class));
    }

    @Test
    void testNotificarPagoConfirmado_NotificacionRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockNotificacionRepo.save(any(Notificacion.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        notificationServiceUnderTest.notificarPagoConfirmado(clienteUsuario, "numeroFactura", "total");

        // Verify the results
    }

    @Test
    void testNotificarPagoConfirmado_UsuarioRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final Usuario clienteUsuario = new Usuario();
        clienteUsuario.setId(0L);
        clienteUsuario.setNombre("nombre");
        clienteUsuario.setApellidos("apellidos");
        clienteUsuario.setEmail("email");
        clienteUsuario.setFcmToken("fcmToken");

        when(mockUsuarioRepo.save(any(Usuario.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> notificationServiceUnderTest.notificarPagoConfirmado(clienteUsuario, "numeroFactura",
                "total")).isInstanceOf(OptimisticLockingFailureException.class);
    }
}
