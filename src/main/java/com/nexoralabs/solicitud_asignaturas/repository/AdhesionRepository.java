package com.nexoralabs.solicitud_asignaturas.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.nexoralabs.solicitud_asignaturas.model.Adhesion;
import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.model.Solicitud;

public interface AdhesionRepository extends JpaRepository<Adhesion, Long>{
    boolean existsByUsuarioAndSolicitud(Usuario usuario, Solicitud solicitud);
    Long countBySolicitud(Solicitud solicitud);
    Optional<Adhesion> findByUsuarioAndSolicitud(Usuario usuario, Solicitud solicitud);
    List<Adhesion> findByUsuario(Usuario usuario);
    @Transactional 
    void deleteBySolicitud(Solicitud solicitud);
}
