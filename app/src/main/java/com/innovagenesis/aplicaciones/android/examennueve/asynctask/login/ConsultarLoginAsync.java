package com.innovagenesis.aplicaciones.android.examennueve.asynctask.login;

import android.net.Uri;
import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Clase encargada de realizar la consulta a la base de datos
 * Created by alexi on 02/04/2017.
 */

public class ConsultarLoginAsync extends AsyncTask <Uri, Integer, String> {


    HttpURLConnection connection = null;

    @Override
    protected String doInBackground(Uri... params) {
        return null;


    }



}
