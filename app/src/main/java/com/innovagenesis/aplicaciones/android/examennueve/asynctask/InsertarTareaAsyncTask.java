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
 * Clase encargada de insertar las tareas
 * Created by alexi on 15/04/2017.
 */

public class InsertarTareaAsyncTask extends AsyncTask<URL, Integer, Boolean> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private Tareas tareas;

    public InsertarTareaAsyncTask(Activity activity, Tareas tareas) {
        this.activity = activity;
        this.tareas = tareas;
        progressDialog = new ProgressDialog(activity);
    }

    @Override
    protected Boolean doInBackground(URL... params) {

        HttpURLConnection connection = null;

        try {
            JSONObject datos = new JSONObject();

            /* Crea el JSONObject y le asigna las tareas*/
            datos.put(DiccionarioDatos.nomTarea,tareas.getNomTarea())
                    .put(DiccionarioDatos.idEstuTarea,tareas.getIdEstuTarea())
                    .put(DiccionarioDatos.idAsignaTarea,tareas.getIdAsignaTarea())
                    .put(DiccionarioDatos.notaTarea,tareas.getNotaTarea());

            connection = (HttpURLConnection)params[0].openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode(datos.toString().getBytes().length);
            OutputStream output = connection.getOutputStream();
            output.write(datos.toString().getBytes());
            output.flush();
            output.close();
            return true;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
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
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (aBoolean)
            Toast.makeText(activity,"Inserccion correcta",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity,"Error en la Inserccion",Toast.LENGTH_SHORT).show();
    }
}
