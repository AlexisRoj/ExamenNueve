package com.innovagenesis.aplicaciones.android.examennueve.instancias;

/**
 * Clase encargada de desplegar los usuarios
 * Created by alexi on 04/04/2017.
 */

public class Usuarios {

    private String estudiante;
    private String id_estudiante;

    public Usuarios() {
    }

    public Usuarios(String estudiante, String id_estudiante) {
        this.estudiante = estudiante;
        this.id_estudiante = id_estudiante;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(String id_estudiante) {
        this.id_estudiante = id_estudiante;
    }
}
