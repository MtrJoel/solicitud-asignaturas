package com.nexoralabs.solicitud_asignaturas.service;
import java.util.List;
import org.springframework.stereotype.Service;
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

}
