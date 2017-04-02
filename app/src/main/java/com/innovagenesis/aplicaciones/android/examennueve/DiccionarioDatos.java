package com.innovagenesis.aplicaciones.android.examennueve;

/**
 *
 * Almacena varibles y elementos que van a ser usuados en toda la aplicacion
 *
 * Created by alexi on 01/04/2017.
 */

public class DiccionarioDatos {

    public static final String PREFERENCE_LOGIN = "preferencia_de_login";

    public static final String nombreUsuario = "nombre de usuario";
    public static final String passUsuario = "Contraseña usuario";
    public static final int rolUsuario = 0;
    public static final boolean salvarUsuario = false;

    private static final String ip = "192.168.100.4";
    private static final String puerto = "8080";

    // Espacio reservado para las conexiones a base de datos

    public static final String URL_SERVICIO_TAREA =
    "http://" + ip + ":" + puerto + "/WebServiceExamenSiete/webapi/Users/";

    public static final String URL_SERVICIO_USUARIOS =
    "http://" + ip + ":" + puerto + "/WebServiceExamenSiete/webapi/usuarios/";

}
