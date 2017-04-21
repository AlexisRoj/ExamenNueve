package com.innovagenesis.aplicaciones.android.examennueve.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.login.ListarUsuariosAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.dialogos.DialogoLogin;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.login.ConsultarLoginAsync;
import com.innovagenesis.aplicaciones.android.examennueve.provider.ProvedorContenidosUsuarios;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity implements DialogoLogin.DatosHacerLogin,
        ConsultarLoginAsync.ValidarLoginUsuario,ListarUsuariosAsyncTask.ConsultaRellenarProvider {

    private SharedPreferences preferences;
    public String usuario, contrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /** Incializa para llenar el content provider*/
        try {
            new ListarUsuariosAsyncTask(this).execute(new URL(DiccionarioDatos.URL_SERVICIO_USUARIO));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        preferences = getSharedPreferences(DiccionarioDatos.PREFERENCE_LOGIN, MODE_PRIVATE);
        usuario = preferences.getString(DiccionarioDatos.nombreUsuario, null);
        contrasena = preferences.getString(DiccionarioDatos.passUsuario, null);

        if (usuario != null && contrasena != null)
            mLoginUsuario(usuario, contrasena);

        DialogoLogin dialogoLogin = new DialogoLogin();
        dialogoLogin.setCancelable(false); // Evita que se cierre el dialogo
        dialogoLogin.show(getSupportFragmentManager(), DialogoLogin.TAG);

        this.setTitle("Ingreso al sistema");

        if (!mValidarConexion()) { // Valida si existe conexion
            Toast.makeText(this, "Error al establecef conexion", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void HacerLogin(String usuario, String contrasena, Boolean recordar) {
        // envia a consultar los datos atrapados del login y sus campos

        SharedPreferences.Editor editor = preferences.edit();

        if (recordar) {
            //Guarda las preferencias
            editor.putString(DiccionarioDatos.nombreUsuario, usuario);
            editor.putString(DiccionarioDatos.passUsuario, contrasena);
            editor.apply();
        } else {
            //Borra las preferencias
            editor.remove(DiccionarioDatos.nombreUsuario);
            editor.remove(DiccionarioDatos.passUsuario);
            editor.apply();
        }
        mLoginUsuario(usuario, contrasena);
    }

    /**
     * Inicia el proceso de login
     */
    private void mLoginUsuario(String usuario, String contrasena) {
        try {
            new ConsultarLoginAsync(this).execute(new URL(DiccionarioDatos.URL_SERVICIO_USUARIO +
                    usuario + "/" + contrasena));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface que permite el ingreso al sistema
     */
    @Override
    public void LoginUsuario(String userName, String pass, int rol) {
        // si el usuario existe permite la carga de la otra activity

        if (rol > 0)
            if (rol == 3 || rol == 1)
                //rol 3 activa al profesor
                mCargarActivity();
            else
                Toast.makeText
                        (this, getString(R.string.mensajePrivilegios), Toast.LENGTH_SHORT).show();
    }


    /**
     * Valida si existe conexion
     */
    private boolean mValidarConexion() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Metodo encargado de cargar la actividad
     */
    private void mCargarActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Realiza el llenado del provider, es el resultado de la interface
     * ListarUsuariosAsyncTask
     * */
    @Override
    public void RellenarProvider(String s) {

        try {
            JSONArray jsonArray = new JSONArray(s);

            for (int i = 0; i < jsonArray.length(); i++){

                ContentValues values = new ContentValues();

                values.put(ProvedorContenidosUsuarios.id_usuario,jsonArray.getJSONObject(i)
                        .getInt(ProvedorContenidosUsuarios.id_usuario));
                values.put(ProvedorContenidosUsuarios.nom_usuario,jsonArray.getJSONObject(i)
                        .getString(ProvedorContenidosUsuarios.nom_usuario));
                values.put(ProvedorContenidosUsuarios.pass_usuario,jsonArray.getJSONObject(i)
                        .getString(ProvedorContenidosUsuarios.pass_usuario));
                values.put(ProvedorContenidosUsuarios.rol_user,jsonArray.getJSONObject(i)
                        .getInt(ProvedorContenidosUsuarios.rol_user));
                getContentResolver().insert(ProvedorContenidosUsuarios.CONTENEDORURI,values);
            }
            Toast.makeText(getApplicationContext(),
                    "Nuevo registro ingresado " + ProvedorContenidosUsuarios.CONTENEDORURI,
                    Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
