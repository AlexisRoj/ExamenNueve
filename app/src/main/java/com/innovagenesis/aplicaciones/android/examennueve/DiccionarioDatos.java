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

    private static final String ip = "192.168.100.2";
    private static final String puerto = "8084";

    // Espacio reservado para las conexiones a base de datos

    public static final String URL_SERVICIO_USUARIO =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/usuarios/";

    public static final String URL_SERVICIO_TAREA =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/tareas/";

    public static final String URL_SERVICIO_ASIGNA =
            "http://" + ip + ":" + puerto + "/WebServiceExamenNueve/webapi/asignatura/";

    /* Llaves tareas, recordar que son los nombres de las varias del constructor
    * definido en el webservice*/
    public static String idTarea = "id_tarea";
    public static String nomTarea = "nom_tarea";
    public static String idEstuTarea = "id_estudiante_tarea";
    public static String idAsignaTarea = "id_asigna_tarea";
    public static String nomEstuTarea = "nom_estudiante_tarea";
    public static String nomUsuarioTarea = "nom_usuario_tarea";
    public static String nomAsignaTarea = "nom_asigna_tarea";
    public static String notaTarea = "nota_tarea";

}
