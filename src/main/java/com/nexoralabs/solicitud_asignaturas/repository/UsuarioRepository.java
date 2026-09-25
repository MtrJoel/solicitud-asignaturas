package com.nexoralabs.solicitud_asignaturas.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nexoralabs.solicitud_asignaturas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
