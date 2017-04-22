package com.innovagenesis.aplicaciones.android.examennueve.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Clase encargada de realizar el listado de las tareas
 * almacenadas en la base de datos, tambien cumple
 * la funcion de verificar que un nombre de tarea sea unico
 * Created by alexi on 10/04/2017.
 */

public class ListarTareasAsyncTask extends AsyncTask<URL, Integer, String> {


    private ProgressDialog progressDialog;
    private Activity activity;
    private int evento;

    public ListarTareasAsyncTask(Activity activity, int evento) {
        this.activity = activity;
        this.evento = evento;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (mDesplegarTareas) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("La interface ListarUsuarioAsyncTask no ha sido implementada");
        }

    }


    public interface mDesplegarTareas {

        void DesplegarTareaRecycler(ArrayList<Tareas> listarTareasAsyncTasks);

        void LlenarProviderTareas(String jsonTarea);
    }

    private mDesplegarTareas listener;

    @Override
    protected String doInBackground(URL... params) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) params[0].openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        ArrayList<Tareas> lista = new ArrayList<>();

        switch (evento) {

            case 1: {
                //Carga el fragmento

                try {

                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Tareas tareas = new Tareas();

                        tareas.setIdTarea(jsonArray.getJSONObject(i).
                                getInt(DiccionarioDatos.idTarea));
                        tareas.setNomTarea(jsonArray.getJSONObject(i).
                                getString(DiccionarioDatos.nomTarea));
                        tareas.setNomAsignaTarea(jsonArray.getJSONObject(i).
                                getString(DiccionarioDatos.nomAsignaTarea));
                        tareas.setNomEstuTarea(jsonArray.getJSONObject(i).
                                getString(DiccionarioDatos.nomUsuarioTarea));
                        tareas.setNotaTarea(Integer.valueOf(jsonArray.getJSONObject(i).
                                getString(DiccionarioDatos.notaTarea)));

                        lista.add(tareas);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, R.string.errorJSON,
                            Toast.LENGTH_SHORT).show();
                }
                listener.DesplegarTareaRecycler(lista);
                break;
            }

            case 2:
                //Valida si ya existe la tarea
                listener.LlenarProviderTareas(s);
                break;
        }
    }
}
