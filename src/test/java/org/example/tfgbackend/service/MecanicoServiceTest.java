package org.example.tfgbackend.service;

import org.example.tfgbackend.dto.request.MecanicoRequest;
import org.example.tfgbackend.dto.response.MecanicoResponse;
import org.example.tfgbackend.exception.ResourceNotFoundException;
import org.example.tfgbackend.model.Mecanico;
import org.example.tfgbackend.repository.MecanicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MecanicoServiceTest {

    @Mock
    private MecanicoRepository mockMecanicoRepo;

    @InjectMocks
    private MecanicoService mecanicoServiceUnderTest;

    @Test
    void testGetAll() {
        // Setup
        // Configure MecanicoRepository.findAll(...).
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("n");
        mecanico.setApellidos("a");
        mecanico.setEspecialidad("e");
        mecanico.setTelefono("t");
        mecanico.setActivoTaller(false);
        final List<Mecanico> mecanicos = List.of(mecanico);
        when(mockMecanicoRepo.findAll()).thenReturn(mecanicos);

        // Run the test
        final List<MecanicoResponse> result = mecanicoServiceUnderTest.getAll();

        // Verify the results
    }

    @Test
    void testGetAll_MecanicoRepositoryReturnsNoItems() {
        // Setup
        when(mockMecanicoRepo.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<MecanicoResponse> result = mecanicoServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetActivos() {
        // Setup
        // Configure MecanicoRepository.findByActivoTallerTrue(...).
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("n");
        mecanico.setApellidos("a");
        mecanico.setEspecialidad("e");
        mecanico.setTelefono("t");
        mecanico.setActivoTaller(false);
        final List<Mecanico> mecanicos = List.of(mecanico);
        when(mockMecanicoRepo.findByActivoTallerTrue()).thenReturn(mecanicos);

        // Run the test
        final List<MecanicoResponse> result = mecanicoServiceUnderTest.getActivos();

        // Verify the results
    }

    @Test
    void testGetActivos_MecanicoRepositoryReturnsNoItems() {
        // Setup
        when(mockMecanicoRepo.findByActivoTallerTrue()).thenReturn(Collections.emptyList());

        // Run the test
        final List<MecanicoResponse> result = mecanicoServiceUnderTest.getActivos();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCrear() {
        // Setup
        final MecanicoRequest req = new MecanicoRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEspecialidad("e");
        req.setTelefono("t");

        // Configure MecanicoRepository.save(...).
        final Mecanico mecanico = new Mecanico();
        mecanico.setId(0L);
        mecanico.setNombre("n");
        mecanico.setApellidos("a");
        mecanico.setEspecialidad("e");
        mecanico.setTelefono("t");
        mecanico.setActivoTaller(false);
        when(mockMecanicoRepo.save(any(Mecanico.class))).thenReturn(mecanico);

        // Run the test
        final MecanicoResponse result = mecanicoServiceUnderTest.crear(req);

        // Verify the results
    }

    @Test
    void testCrear_MecanicoRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final MecanicoRequest req = new MecanicoRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEspecialidad("e");
        req.setTelefono("t");

        when(mockMecanicoRepo.save(any(Mecanico.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> mecanicoServiceUnderTest.crear(req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testToggleActivo() {
        // Setup
        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("n");
        mecanico1.setApellidos("a");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("t");
        mecanico1.setActivoTaller(false);
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        // Configure MecanicoRepository.save(...).
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("n");
        mecanico2.setApellidos("a");
        mecanico2.setEspecialidad("e");
        mecanico2.setTelefono("t");
        mecanico2.setActivoTaller(false);
        when(mockMecanicoRepo.save(any(Mecanico.class))).thenReturn(mecanico2);

        // Run the test
        final MecanicoResponse result = mecanicoServiceUnderTest.toggleActivo(0L);

        // Verify the results
    }

    @Test
    void testToggleActivo_MecanicoRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockMecanicoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> mecanicoServiceUnderTest.toggleActivo(0L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testToggleActivo_MecanicoRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("n");
        mecanico1.setApellidos("a");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("t");
        mecanico1.setActivoTaller(false);
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        when(mockMecanicoRepo.save(any(Mecanico.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> mecanicoServiceUnderTest.toggleActivo(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testActualizar() {
        // Setup
        final MecanicoRequest req = new MecanicoRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEspecialidad("e");
        req.setTelefono("t");

        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("n");
        mecanico1.setApellidos("a");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("t");
        mecanico1.setActivoTaller(false);
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        // Configure MecanicoRepository.save(...).
        final Mecanico mecanico2 = new Mecanico();
        mecanico2.setId(0L);
        mecanico2.setNombre("n");
        mecanico2.setApellidos("a");
        mecanico2.setEspecialidad("e");
        mecanico2.setTelefono("t");
        mecanico2.setActivoTaller(false);
        when(mockMecanicoRepo.save(any(Mecanico.class))).thenReturn(mecanico2);

        // Run the test
        final MecanicoResponse result = mecanicoServiceUnderTest.actualizar(0L, req);

        // Verify the results
    }

    @Test
    void testActualizar_MecanicoRepositoryFindByIdReturnsAbsent() {
        // Setup
        final MecanicoRequest req = new MecanicoRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEspecialidad("e");
        req.setTelefono("t");

        when(mockMecanicoRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> mecanicoServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testActualizar_MecanicoRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final MecanicoRequest req = new MecanicoRequest();
        req.setNombre("n");
        req.setApellidos("a");
        req.setEspecialidad("e");
        req.setTelefono("t");

        // Configure MecanicoRepository.findById(...).
        final Mecanico mecanico1 = new Mecanico();
        mecanico1.setId(0L);
        mecanico1.setNombre("n");
        mecanico1.setApellidos("a");
        mecanico1.setEspecialidad("e");
        mecanico1.setTelefono("t");
        mecanico1.setActivoTaller(false);
        final Optional<Mecanico> mecanico = Optional.of(mecanico1);
        when(mockMecanicoRepo.findById(0L)).thenReturn(mecanico);

        when(mockMecanicoRepo.save(any(Mecanico.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> mecanicoServiceUnderTest.actualizar(0L, req))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }
}
