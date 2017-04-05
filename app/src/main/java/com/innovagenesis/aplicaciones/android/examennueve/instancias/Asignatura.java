package com.innovagenesis.aplicaciones.android.examennueve.instancias;

/**
 * Clase encargada de desplegar las asignaturas
 * Created by alexi on 04/04/2017.
 */

public class Asignatura {

    private String materia;
    private String cod_materia;

    public Asignatura() {
    }

    public Asignatura(String materia, String cod_materia) {
        this.materia = materia;
        this.cod_materia = cod_materia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getCod_materia() {
        return cod_materia;
    }

    public void setCod_materia(String cod_materia) {
        this.cod_materia = cod_materia;
    }
}
