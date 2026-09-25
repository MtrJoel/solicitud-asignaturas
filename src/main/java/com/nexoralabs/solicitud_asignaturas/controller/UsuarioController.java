package com.nexoralabs.solicitud_asignaturas.controller;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexoralabs.solicitud_asignaturas.model.Usuario;
import com.nexoralabs.solicitud_asignaturas.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    // * Inyectar dependencia del servicio para usuario, por constructor
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario registrarUsuario(
        @RequestBody Usuario usuario
    ){
        return usuarioService.registrar(usuario);
    }


    @GetMapping("/{email}")
    public Optional<Usuario> buscarPorEmail(
        @PathVariable String email
    ){
        return usuarioService.buscarPorEmail(email);
    }

    @PostMapping("/login")
    public Usuario login(
        @RequestBody Usuario usuario
    ){
        return usuarioService.login(usuario.getEmail(), usuario.getPassword());
    }
}
