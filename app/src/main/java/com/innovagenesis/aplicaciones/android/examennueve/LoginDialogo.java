package com.innovagenesis.aplicaciones.android.examennueve;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Dialogo para inicio de seccion en el sistema
 * Created by alexi on 01/04/2017.
 */

public class LoginDialogo extends DialogFragment {

    public static final String TAG = "dialogo_inicio_sesión";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.login_fragment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        TextInputLayout textInputUser = (TextInputLayout)view.findViewById(R.id.textInputLayoutUser);
        TextInputLayout textInputPass = (TextInputLayout)view.findViewById(R.id.textInputLayoutPass);

        Button btnLogin = (Button)view.findViewById(R.id.btn_login);
        Button btnSalirogin = (Button)view.findViewById(R.id.btn_salir_login);




        return builder.create();
    }

}
