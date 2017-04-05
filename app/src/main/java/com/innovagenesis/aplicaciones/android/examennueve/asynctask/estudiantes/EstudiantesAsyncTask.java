package com.innovagenesis.aplicaciones.android.examennueve.asynctask.estudiantes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.innovagenesis.aplicaciones.android.examennueve.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Encargada de traer los estuadiante a listar
 * Created by alexi on 04/04/2017.
 */

public class EstudiantesAsyncTask extends AsyncTask<URL, Integer, String> {

    private Activity activity;
    private ProgressDialog progressDialog;

    public interface DesplegarJSONEstudiante{
        void JSONEstudiante(String json);
    }

    private DesplegarJSONEstudiante listener;

    public EstudiantesAsyncTask(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected String doInBackground(URL... params) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection)params[0].openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert connection != null;
            connection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setMessage(activity.getString(R.string.procesando));
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s!= null){

            listener.JSONEstudiante(s);
        }
    }
}
