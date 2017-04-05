package com.innovagenesis.aplicaciones.android.examennueve.asynctask.estudiantes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Encargada de traer los estuadiante a listar
 * Created by alexi on 04/04/2017.
 */

public class EstudiantesAsyncTask extends AsyncTask<URL, Integer, String> {

    private Activity activity;
    private ProgressDialog progressDialog;

    public interface mDesplegarEstudiantes {
        void DesplegarEstudiantes(List<UsuariosAsigna> listaUsuarios);
    }

    private mDesplegarEstudiantes listener;


    public EstudiantesAsyncTask(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (mDesplegarEstudiantes) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("La interface EstudiantesAsyncTask no ha sido implementada");
        }
    }

    @Override
    protected String doInBackground(URL... params) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) params[0].openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

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

        if (s != null) {
            //LLena la lista con el json
            List<UsuariosAsigna> lista = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {

                    UsuariosAsigna usuariosAsigna = new UsuariosAsigna();

                    usuariosAsigna.setDescripcion(jsonArray.getJSONObject(i).getString("nom_asignatura"));
                    usuariosAsigna.setCodigo(jsonArray.getJSONObject(i).getInt("id_asignatura"));

                    lista.add(usuariosAsigna);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.errorJSON, Toast.LENGTH_SHORT).show();
            }
            listener.DesplegarEstudiantes(lista);
        }
    }
}
