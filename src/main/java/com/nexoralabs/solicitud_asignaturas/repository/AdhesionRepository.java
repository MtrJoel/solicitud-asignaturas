package com.nexoralabs.solicitud_asignaturas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nexoralabs.solicitud_asignaturas.model.Adhesion;
import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.model.Solicitud;

public interface AdhesionRepository extends JpaRepository<Adhesion, Long>{
    boolean existsByUsuarioAndSolicitud(Usuario usuario, Solicitud solicitud);
    Long countBySolicitud(Solicitud solicitud);
}
