package com.innovagenesis.aplicaciones.android.examennueve.asynctask.login;

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
 * Lista los usuarios que estan en el service
 * los cuales van a ser almacenados en el provider
 * Created by alexi on 18/04/2017.
 */

public class ListarUsuariosAsyncTask extends AsyncTask<URL,Integer,String> {

    private Activity activity;
    private ProgressDialog progressDialog;

    public interface ConsultaRellenarProvider{
        void RellenarProvider(String s);
    }

    ConsultaRellenarProvider listener;

    public ListarUsuariosAsyncTask(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);

        try {
            listener = (ConsultaRellenarProvider)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException ("la interface no ha sido implementada en el activity");
        }

    }

    @Override
    protected String doInBackground(URL... params) {

        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection)params[0].openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            return reader.readLine();

        } catch (IOException e) {
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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (progressDialog.isShowing())
            progressDialog.dismiss();

        if (s != null){
            listener.RellenarProvider(s);

        }

    }
}
