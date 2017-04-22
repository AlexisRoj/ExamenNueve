package com.innovagenesis.aplicaciones.android.examennueve.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase encargada de actualizar las tareas
 * Created by alexi on 15/04/2017.
 */

public class ActualizarTareaAsyncTask extends AsyncTask<URL,Integer,Boolean> {

    Activity activity;
    ProgressDialog progressDialog;
    private Tareas tareas;

    public ActualizarTareaAsyncTask(Activity activity, Tareas tareas) {
        this.activity = activity;
        this.tareas = tareas;

        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected Boolean doInBackground(URL... params) {

        HttpURLConnection connection = null;

        JSONObject datos = new JSONObject();

        try {
            datos.put(DiccionarioDatos.nomTarea,tareas.getNomTarea())
                    .put(DiccionarioDatos.idAsignaTarea,tareas.getIdAsignaTarea())
                    .put(DiccionarioDatos.idEstuTarea,tareas.getIdEstuTarea())
                    .put(DiccionarioDatos.notaTarea,tareas.getNotaTarea());

            connection = (HttpURLConnection)params[0].openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(datos.toString().getBytes().length);
            OutputStream output = connection.getOutputStream();
            output.write(datos.toString().getBytes());
            output.flush();
            output.close();
            return true;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
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
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (aBoolean) {

        } else
            Toast.makeText(activity, "Error en la Inserccion", Toast.LENGTH_SHORT).show();
    }
}
