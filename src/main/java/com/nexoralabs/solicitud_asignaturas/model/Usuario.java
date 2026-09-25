package com.nexoralabs.solicitud_asignaturas.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;
    private String password;

    public enum Rol {
        ESTUDIANTE, DIRECTOR
    }

    @Enumerated(EnumType.STRING)
    private Rol rol;

    // ! Constructor 
    public Usuario(){}
    public Usuario(Long id, String nombre, String email, String password, Rol rol){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    // ! getters and setters

    public Long getId(){
        return id;
    }

    public String getNombre(){
        return  nombre;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public Rol getRol(){
        return rol;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRol(Rol rol){
        this.rol = rol;
    }

}
