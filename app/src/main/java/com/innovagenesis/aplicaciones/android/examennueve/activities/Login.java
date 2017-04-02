package com.innovagenesis.aplicaciones.android.examennueve.activities;

import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.innovagenesis.aplicaciones.android.examennueve.AlmacenDeVariables;
import com.innovagenesis.aplicaciones.android.examennueve.LoginDialogo;
import com.innovagenesis.aplicaciones.android.examennueve.R;

public class Login extends AppCompatActivity implements LoginDialogo.DatosHacerLogin {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(AlmacenDeVariables.PREFERENCE_LOGIN,MODE_PRIVATE);


        LoginDialogo loginDialogo = new LoginDialogo();
        loginDialogo.setCancelable(false);
        loginDialogo.show(getSupportFragmentManager(),LoginDialogo.TAG);

        this.setTitle("Ingreso al sistema");
    }


    @Override
    public void hacerLogin(String usuario, String contrasena, Boolean recordar) {


    }
}
