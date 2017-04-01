package com.innovagenesis.aplicaciones.android.examennueve;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginDialogo loginDialogo = new LoginDialogo();
        loginDialogo.setCancelable(false);
        loginDialogo.show(getSupportFragmentManager(),LoginDialogo.TAG);

        this.setTitle("Ingreso al sistema");
    }


}
