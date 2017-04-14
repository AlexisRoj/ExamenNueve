package com.innovagenesis.aplicaciones.android.examennueve;

/**
 * Almacena varibles y elementos que van a ser usuados en toda la aplicacion
 * <p>
 * Created by alexi on 01/04/2017.
 */

public class DiccionarioDatos {

    public static final String PREFERENCE_LOGIN = "preferencia_de_login";

    public static final String nombreUsuario = "nombre de usuario";
    public static final String passUsuario = "Contraseña usuario";
    public static final int rolUsuario = 0;
    public static final boolean salvarUsuario = false;

    private static final String ip = "192.168.100.3";
    private static final String puerto = "8084";

    // Espacio reservado para las conexiones a base de datos

    public static final String URL_SERVICIO_USUARIO =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/usuarios/";

    public static final String URL_SERVICIO_TAREA =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/tareas/";

    public static final String URL_SERVICIO_ASIGNA =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/asignatura/";

    public static String nomTarea = "nomTarea";
    public static String nomEstuTarea = "nomEstuTarea";
    public static String nomAsignaTarea = "nomAsignaTarea";
    public static String notaTarea = "notaTarea";

}
