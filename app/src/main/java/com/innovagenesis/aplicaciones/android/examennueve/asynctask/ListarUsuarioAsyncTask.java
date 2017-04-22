package com.innovagenesis.aplicaciones.android.examennueve.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

/**Clase encargada de realizar el proceso de extraccion de usuarios
 * Created by alexi on 06/04/2017.
 */

public class ListarUsuarioAsyncTask extends AsyncTask <URL, Integer, String>{

    private ProgressDialog progressDialog;
    private Activity activity;
    private int evento;



    public interface mDesplegarUsuario {
        void DesplegarUsuarioRecycler(ArrayList<UsuariosAsigna> listaUsuarios);
        void DesplegarUsuarioDialogo (String jsonEstudiante);
    }

    private mDesplegarUsuario listener;


    public ListarUsuarioAsyncTask(Activity activity, int evento) {
        this.activity = activity;
        this.evento = evento;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (mDesplegarUsuario)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("La interface ListarUsuarioAsyncTask no ha sido implementada");
        }

    }

    @Override
    protected String doInBackground(URL... params) {

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection)params[0].openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
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

        ArrayList<UsuariosAsigna> lista = new ArrayList<>();

        switch (evento){
            // dependiendo el evento solicitado deserializa o envia el
            // json completo
            case 1:

                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i =0; i< jsonArray.length(); i++){

                        UsuariosAsigna usuariosAsigna = new UsuariosAsigna();

                        usuariosAsigna.descripcion =jsonArray.getJSONObject(i).getString("nom_usuario");
                        usuariosAsigna.codigo = jsonArray.getJSONObject(i).getInt("ced_usuario");

                        lista.add(usuariosAsigna);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.DesplegarUsuarioRecycler(lista);
                break;
            case 2:
                listener.DesplegarUsuarioDialogo(s);
                break;
        }


    }
}
