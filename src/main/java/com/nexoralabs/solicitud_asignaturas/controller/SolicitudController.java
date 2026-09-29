package com.nexoralabs.solicitud_asignaturas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexoralabs.solicitud_asignaturas.dto.SolicitudConAdhesiones;
import com.nexoralabs.solicitud_asignaturas.model.Adhesion;
import com.nexoralabs.solicitud_asignaturas.model.Solicitud;
import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.repository.SolicitudRepository;
import com.nexoralabs.solicitud_asignaturas.repository.UsuarioRepository;
import com.nexoralabs.solicitud_asignaturas.service.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {
    private final SolicitudService solicitudService;
    private final UsuarioRepository usuarioRepository;
    private final SolicitudRepository solicitudRepository;

    public SolicitudController(SolicitudService solicitudService, UsuarioRepository usuarioRepository,
            SolicitudRepository solicitudRepository) {
        this.solicitudService = solicitudService;
        this.usuarioRepository = usuarioRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @PostMapping
    public Solicitud crearSolicitud(
            @RequestBody Solicitud solicitud

    ) {
        return solicitudService.crearSolicitud(solicitud);
    }

    @GetMapping("/listar")
    public List<Solicitud> listarSolicitudes() {
        return solicitudService.listarSolicitudes();
    }

    @PostMapping("/{solicitudId}/unirse/{usuarioId}")
    public Adhesion sumarseSolicitud(
            @PathVariable Long solicitudId,
            @PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        return solicitudService.sumarseASolicitud(usuario, solicitud);
    }

    @GetMapping("/director")
    public List<SolicitudConAdhesiones> listarAdhesiones() {
        return solicitudService.listarSolicitudesConConteo();
    }

    @PutMapping("/{id}")
    public Solicitud actualizarSolicitud(
            @PathVariable Long id,
            @RequestBody Solicitud solicitud) {
        return solicitudService.actualizarSolicitud(id, solicitud);
    }

    @DeleteMapping("/{id}")
    public void eliminarSolicitud(@PathVariable Long id) {
        solicitudService.eliminarSolicitud(id);
    }

    @DeleteMapping("/{solicitudId}/salir/{usuarioId}")
    public void darseDeBaja(
            @PathVariable Long solicitudId,
            @PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitudService.darseDeBaja(usuario, solicitud);
    }

    @GetMapping("/misAdhesiones/{usuarioId}")
    public List<Long> misAdhesiones(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return solicitudService.listarIdsSolicitudesDeUsuario(usuario);
    }
}
