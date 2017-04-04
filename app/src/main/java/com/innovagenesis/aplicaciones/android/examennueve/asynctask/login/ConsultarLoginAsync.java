package com.innovagenesis.aplicaciones.android.examennueve.asynctask.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Clase encargada de realizar la consulta a la base de datos
 * Created by alexi on 02/04/2017.
 */

public class ConsultarLoginAsync extends AsyncTask<URL, Integer, String> {

    private String username = "";
    private String pass = "";
    private int rol = 0;

    private Activity activity;
    private ProgressDialog progressDialog;

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

    public interface ValidarLoginUsuario {
        void LoginUsuario(String userName, String pass, int rol);
    }


    private ValidarLoginUsuario listener;

    public ConsultarLoginAsync(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (ValidarLoginUsuario) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("La activity no implementa la interfaz consultar usuario");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
        progressDialog.setMessage(activity.getString(R.string.procesando));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (s != null) {
            try {
                /**
                 * Es necesario recorrer toda el objecto para extraer la informacion necesaria
                 * */
                JSONObject jsonObject = new JSONObject(s);
                for (int i = 0; i < jsonObject.length(); i++) {
                    username = jsonObject.getString("nom_usuario");
                    pass = jsonObject.getString("pass_usuario");
                    rol = jsonObject.getInt("rol_user");
                }
                listener.LoginUsuario(username, pass, rol);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(activity, R.string.contrasenaNoValida,Toast.LENGTH_SHORT).show();
    }
}
