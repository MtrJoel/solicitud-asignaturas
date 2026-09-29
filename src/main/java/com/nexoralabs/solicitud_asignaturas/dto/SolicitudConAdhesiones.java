package com.nexoralabs.solicitud_asignaturas.dto;

import com.nexoralabs.solicitud_asignaturas.model.Usuario;

public class SolicitudConAdhesiones {
    private Long id;
    private String asignatura;
    private String horario;
    private Long totalAdhesiones;
    private Usuario creador;

    // ! Constructores
    public SolicitudConAdhesiones(){

    }

    public SolicitudConAdhesiones(Long id,String asignatura, String horario, Long totalAdhesiones, Usuario creador){
        this.id = id;
        this.asignatura = asignatura;
        this.horario = horario;
        this.totalAdhesiones = totalAdhesiones;
        this.creador = creador;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getAsignatura(){
        return asignatura;
    }

    public String getHorario(){
        return horario;
    }

    public Long getTotalAdhesiones(){
        return totalAdhesiones;
    }

    public void setAsignatura(String asignatura){
        this.asignatura = asignatura;
    }

    public void setHorario(String horario){
        this.horario = horario;
    }

    public void setTotalAdhesiones(Long totalAdhesiones){
        this.totalAdhesiones = totalAdhesiones;
    }

    public void setCreador(Usuario creador){
        this.creador = creador;
    }

    public Usuario getCreador(){
        return creador;
    }
}
