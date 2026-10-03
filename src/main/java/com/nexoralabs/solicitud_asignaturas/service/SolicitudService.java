package com.nexoralabs.solicitud_asignaturas.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.nexoralabs.solicitud_asignaturas.dto.SolicitudConAdhesiones;
import com.nexoralabs.solicitud_asignaturas.model.Adhesion;
import com.nexoralabs.solicitud_asignaturas.model.Solicitud;
import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.repository.AdhesionRepository;
import com.nexoralabs.solicitud_asignaturas.repository.SolicitudRepository;

@Service 
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final AdhesionRepository adhesionRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, AdhesionRepository adhesionRepository){
        this.solicitudRepository = solicitudRepository;
        this.adhesionRepository = adhesionRepository;
    }

    public Solicitud crearSolicitud(Solicitud solicitud){
        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> listarSolicitudes(){
        return solicitudRepository.findAll();
    }

    public Adhesion sumarseASolicitud(Usuario usuario, Solicitud solicitud){

        if(adhesionRepository.existsByUsuarioAndSolicitud(usuario, solicitud)){
            throw new RuntimeException("Este usuario ya se sumo a esta solicitud");
        }

        Adhesion adhesion = new Adhesion();
        adhesion.setUsuario(usuario);
        adhesion.setSolicitud(solicitud);

        return adhesionRepository.save(adhesion);
    }

    public List<SolicitudConAdhesiones> listarSolicitudesConConteo(){
        List<SolicitudConAdhesiones> solicitudesUnidas = new ArrayList<>();

        for (Solicitud solicitud : solicitudRepository.findAll()) {
            Long conteo = adhesionRepository.countBySolicitud(solicitud);
            SolicitudConAdhesiones dto = new SolicitudConAdhesiones(
                solicitud.getId(),
                solicitud.getAsignatura(),
                solicitud.getHorario(),
                conteo, 
                solicitud.getCreador()
            );
            solicitudesUnidas.add(dto);
            
        }

        return  solicitudesUnidas;
    }

    public Solicitud actualizarSolicitud(Long id, Solicitud datosActualizados) {
    Solicitud existente = solicitudRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    existente.setAsignatura(datosActualizados.getAsignatura());
    existente.setHorario(datosActualizados.getHorario());

    return solicitudRepository.save(existente);
}

public void eliminarSolicitud(Long id) {
    Solicitud solicitud = solicitudRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
    
    adhesionRepository.deleteBySolicitud(solicitud);
    solicitudRepository.deleteById(id);
}

    public void darseDeBaja(Usuario usuario, Solicitud solicitud) {
    Adhesion adhesion = adhesionRepository.findByUsuarioAndSolicitud(usuario, solicitud)
        .orElseThrow(() -> new RuntimeException("No estás sumado a esta solicitud"));

    adhesionRepository.delete(adhesion);
}

public List<Long> listarIdsSolicitudesDeUsuario(Usuario usuario) {
    List<Long> ids = new ArrayList<>();

    for (Adhesion adhesion : adhesionRepository.findByUsuario(usuario)) {
        ids.add(adhesion.getSolicitud().getId());
    }

    return ids;
}

}
