package com.nexoralabs.solicitud_asignaturas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "adhesion")
public class Adhesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne 
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    private LocalDateTime fecha;

    // ! Constructor
    public Adhesion(){
        this.fecha = LocalDateTime.now();
    }

    public Adhesion(Long id, Usuario usuario, Solicitud solicitud, LocalDateTime fecha){
        this.id = id;
        this.usuario = usuario;
        this.solicitud = solicitud;
        this.fecha = fecha;
    }

    // ! Getters and Setters

    public Long getId(){
        return id;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public Solicitud getSolicitud(){
        return solicitud;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setSolicitud(Solicitud solicitud){
        this.solicitud = solicitud;
    }

    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }
}
