package com.innovagenesis.aplicaciones.android.examennueve.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.LoginDialogo;
import com.innovagenesis.aplicaciones.android.examennueve.R;

public class Login extends AppCompatActivity implements LoginDialogo.DatosHacerLogin {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(DiccionarioDatos.PREFERENCE_LOGIN,MODE_PRIVATE);


        LoginDialogo loginDialogo = new LoginDialogo();
        loginDialogo.setCancelable(false);
        loginDialogo.show(getSupportFragmentManager(),LoginDialogo.TAG);

        this.setTitle("Ingreso al sistema");
    }


    @Override
    public void hacerLogin(String usuario, String contrasena, Boolean recordar) {


    }
}
