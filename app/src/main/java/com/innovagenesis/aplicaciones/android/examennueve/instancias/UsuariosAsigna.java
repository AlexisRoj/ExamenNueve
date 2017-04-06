package com.innovagenesis.aplicaciones.android.examennueve.instancias;

import java.io.Serializable;

/**
 * Clase encargada de desplegar los usuarios
 * Created by alexi on 04/04/2017.
 */

public class UsuariosAsigna{

    public String descripcion;
    public int codigo;


    public UsuariosAsigna() {
        //constructor vacio
    }

    public UsuariosAsigna(String descripcion, int codigo) {
        this.descripcion = descripcion;
        this.codigo = codigo;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
