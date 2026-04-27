package org.example.tfgbackend.controller;

import org.example.tfgbackend.model.Notificacion;
import org.example.tfgbackend.model.Usuario;
import org.example.tfgbackend.repository.NotificacionRepository;
import org.example.tfgbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired private NotificacionRepository notificacionRepo;
    @Autowired private UsuarioRepository usuarioRepo;

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * GET /api/notificaciones?uid=firebaseUid
     * Devuelve todas las notificaciones del usuario, más recientes primero.
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getNotificaciones(
            @RequestHeader("X-Firebase-UID") String firebaseUid) {

        Usuario usuario = usuarioRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Map<String, Object>> resultado = notificacionRepo
                .findByUsuarioIdOrderByFechaDesc(usuario.getId())
                .stream()
                .map(n -> Map.<String, Object>of(
                        "id", n.getId(),
                        "titulo", n.getTitulo(),
                        "cuerpo", n.getCuerpo(),
                        "pantalla", n.getPantalla() != null ? n.getPantalla() : "",
                        "leida", n.isLeida(),
                        "fecha", n.getFecha().format(FMT)
                ))
                .toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/no-leidas")
    public ResponseEntity<Map<String, Long>> getNoLeidas(
            @RequestHeader("X-Firebase-UID") String firebaseUid) {

        Usuario usuario = usuarioRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        long count = notificacionRepo.countByUsuarioIdAndLeidaFalse(usuario.getId());

        return ResponseEntity.ok(Map.of("count", count));
    }


    @PutMapping("/leer-todas")
    @Transactional
    public ResponseEntity<Map<String, String>> leerTodas(
            @RequestHeader("X-Firebase-UID") String firebaseUid) {

        Usuario usuario = usuarioRepo.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        notificacionRepo.marcarTodasLeidas(usuario.getId());

        return ResponseEntity.ok(Map.of("status", "ok"));
    }


    @PutMapping("/{id}/leer")
    public ResponseEntity<Map<String, String>> leer(@PathVariable Long id) {
        Notificacion n = notificacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        n.setLeida(true);
        notificacionRepo.save(n);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}