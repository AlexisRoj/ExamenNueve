package com.innovagenesis.aplicaciones.android.examennueve.asynctask;

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


/**
 * Encargada de traer los estuadiante a listar
 * Created by alexi on 04/04/2017.
 */

public class AsignaturaAsyncTask extends AsyncTask<URL, Integer, String> {

    private Activity activity;
    private ProgressDialog progressDialog;
    private int evento;

    public interface mDesplegarEstudiantes {
        void DesplegarAsignaturaRecycler(ArrayList<UsuariosAsigna> listaAsignatura);
        void DesplegarAsignaturaDialogo(ArrayList<UsuariosAsigna> listaAsignatura);
    }

    private mDesplegarEstudiantes listener;



    public AsignaturaAsyncTask(Activity activity, int evento) {
        this.activity = activity;
        this.evento = evento;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (mDesplegarEstudiantes) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("La interface AsignaturaAsyncTask no ha sido implementada");
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

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (s != null) {
            //LLena la lista con el json
            ArrayList<UsuariosAsigna> lista = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++) {

                    UsuariosAsigna usuariosAsigna = new UsuariosAsigna();

                    usuariosAsigna.setDescripcion(jsonArray.getJSONObject(i).getString("nom_asigna"));
                    usuariosAsigna.setCodigo(jsonArray.getJSONObject(i).getInt("id_asigna"));

                    lista.add(usuariosAsigna);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.errorJSON, Toast.LENGTH_SHORT).show();
            }
            switch (evento){

                case 1:
                    //Llena el recycler
                    listener.DesplegarAsignaturaRecycler(lista);
                    break;
                case 2:
                    //Llena el spinner
                    listener.DesplegarAsignaturaDialogo(lista);
                    break;
            }

        }
    }
}
