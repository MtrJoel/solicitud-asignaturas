package com.nexoralabs.solicitud_asignaturas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nexoralabs.solicitud_asignaturas.model.Solicitud;

public interface SolicitudRepository  extends JpaRepository<Solicitud, Long>{
    
}
