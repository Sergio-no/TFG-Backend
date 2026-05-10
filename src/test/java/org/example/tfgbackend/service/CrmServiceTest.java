package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.CanjearRecompensaRequest;
import org.example.tfgbackend.dto.response.CanjearRecompensaResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Cliente;
import org.example.tfgbackend.model.PuntosHistorial;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.ClienteRepository;
import org.example.tfgbackend.repository.PuntosHistorialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrmServiceTest {

    @Mock
    private ClienteRepository mockClienteRepo;
    @Mock
    private PuntosHistorialRepository mockPuntosRepo;

    @InjectMocks
    private CrmService crmServiceUnderTest;

    @Test
    void testCanjear() {
        // Setup
        final CanjearRecompensaRequest req = new CanjearRecompensaRequest();
        req.setClienteId(0L);
        req.setPuntosRequeridos(0);
        req.setRecompensa("recompensa");

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        // Run the test
        final CanjearRecompensaResponse result = crmServiceUnderTest.canjear(req);

        // Verify the results
        verify(mockClienteRepo).save(any(Cliente.class));
        verify(mockPuntosRepo).save(any(PuntosHistorial.class));
    }

    @Test
    void testCanjear_ClienteRepositoryFindByIdReturnsAbsent() {
        // Setup
        final CanjearRecompensaRequest req = new CanjearRecompensaRequest();
        req.setClienteId(0L);
        req.setPuntosRequeridos(0);
        req.setRecompensa("recompensa");

        when(mockClienteRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> crmServiceUnderTest.canjear(req)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testCanjear_ClienteRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final CanjearRecompensaRequest req = new CanjearRecompensaRequest();
        req.setClienteId(0L);
        req.setPuntosRequeridos(0);
        req.setRecompensa("recompensa");

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockClienteRepo.save(any(Cliente.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> crmServiceUnderTest.canjear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testCanjear_PuntosHistorialRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final CanjearRecompensaRequest req = new CanjearRecompensaRequest();
        req.setClienteId(0L);
        req.setPuntosRequeridos(0);
        req.setRecompensa("recompensa");

        // Configure ClienteRepository.findById(...).
        final Cliente cliente1 = new Cliente();
        cliente1.setId(0L);
        final Usuario usuario = new Usuario();
        usuario.setId(0L);
        usuario.setNombre("nombre");
        cliente1.setUsuario(usuario);
        cliente1.setPuntosAcumulados(0);
        final Optional<Cliente> cliente = Optional.of(cliente1);
        when(mockClienteRepo.findById(0L)).thenReturn(cliente);

        when(mockPuntosRepo.save(any(PuntosHistorial.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> crmServiceUnderTest.canjear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
        verify(mockClienteRepo).save(any(Cliente.class));
    }
}
