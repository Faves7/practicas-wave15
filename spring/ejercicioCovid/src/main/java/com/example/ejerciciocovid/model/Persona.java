package com.example.ejerciciocovid.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Persona {

    private int id;
    private String nombre;
    private String apellido;
    private int edad;

    public Persona(int id, String nombre, String apellido, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
}
