package com.nexoralabs.solicitud_asignaturas.dto;

public class SolicitudConAdhesiones {
    private String asignatura;
    private String horario;
    private Long totalAdhesiones;

    // ! Constructores
    public SolicitudConAdhesiones(){

    }

    public SolicitudConAdhesiones(String asignatura, String horario, Long totalAdhesiones){
        this.asignatura = asignatura;
        this.horario = horario;
        this.totalAdhesiones = totalAdhesiones;
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
}
