package com.nexoralabs.solicitud_asignaturas.service;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.repository.UsuarioRepository;

@Service 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrar(Usuario usuario){
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
           throw new RuntimeException("El email ya esta registrado");
        }

        // ? encriptar contraseña
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario login(String email, String password){
        Usuario usuario = buscarPorEmail(email).orElseThrow(() -> new RuntimeException("Crendenciales invalidas"));

        if(!bCryptPasswordEncoder.matches(password, usuario.getPassword())){
            throw new RuntimeException("Credenciales invalidas");
        }

        return usuario;
    }
}
