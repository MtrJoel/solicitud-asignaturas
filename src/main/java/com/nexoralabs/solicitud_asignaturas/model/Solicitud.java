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
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String asignatura;
    private String horario;
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario creador;

    // ! Constructor
    public Solicitud(){
        this.fechaCreacion = LocalDateTime.now();
    }
    public Solicitud(Long id, String asignatura, String horario, LocalDateTime fechaCreacion, Usuario creador){
        this.id = id;
        this.asignatura = asignatura;
        this.horario = horario;
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    //! Getters and Setters

    public Long getId(){
        return id;
    }

    public String getAsignatura(){
        return asignatura;
    }

    public String getHorario(){
        return horario;
    }

    public LocalDateTime getFechaCreacion(){
        return fechaCreacion;
    }

    public Usuario getCreador(){
        return creador;
    }

    public void setAsignatura(String asignatura){
        this.asignatura = asignatura;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public void setCreador(Usuario creador){
        this.creador = creador;
    }

    public void setHorario(String horario){
        this.horario = horario;
    }
    

}
