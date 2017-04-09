package com.innovagenesis.aplicaciones.android.examennueve.dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;

/**
 * Dialogo encargado de realizar el ingreso de tareas
 * Created by alexi on 07/04/2017.
 */

public class DialogoAgregarTareas extends DialogFragment {

    public static final String TAG = "dialogo_agregar_tarea";
    public static String jsonAsigna = "json_asigna";
    public static String jsonEstu = "json_estudiante";
    public String nombreTarea = "nombre_tarea";
    private String deserializaAsinga, deserializaEstud;
    private static Bundle argumentos;


    private interface DatosGuardarTarea {
        void GuardarTarea(Tareas tareas);
    }


    public static DialogoAgregarTareas newInstance(String jsonEstudiante, String jsonAsignatura) {

        DialogoAgregarTareas fragment = new DialogoAgregarTareas();

        argumentos = new Bundle();
        argumentos.putString(jsonAsigna, jsonAsignatura);
        argumentos.putString(jsonEstu,jsonEstudiante);
        fragment.setArguments(argumentos);

        return fragment;
    }


    public DatosGuardarTarea listener;

    /**
     * Spinner & TextInput
     */
    private Spinner spinnerAsignatura;
    private Spinner spinnerEstudiante;

    private TextInputEditText textInputEditTarea;
    private TextInputLayout textInputLayoutTarea;
    private TextInputEditText textInputEditNota;
    private TextInputLayout textInputLayoutNota;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialogo_agregar_tarea, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        deserializaAsinga = argumentos.getString(jsonAsigna);
        deserializaEstud = argumentos.getString(jsonEstu);

        try {
            /*
             * Seccion encargada de deserializar el json de asignatura
             * se utilizan dos arrglos por uno es para mostrar y el
             * otro es para guardar los datos, es el codigo
             * */
            JSONArray jsonAsina = new JSONArray(deserializaAsinga);
            ArrayList<String> nombreAsigna = new ArrayList<>();
            ArrayList<Integer> codAsigna = new ArrayList<>();

            for (int i = 0; i < jsonAsina.length(); i++) {

                nombreAsigna.add(jsonAsina.getJSONObject(i).getString("nom_asigna"));
                codAsigna.add(Integer.valueOf(jsonAsina.getJSONObject(i).getString("id_asigna")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jsonEstu = new JSONArray(deserializaEstud);
            ArrayList<String> nombreEstu = new ArrayList<>();
            ArrayList<Integer> codEstu = new ArrayList<>();

            for (int i = 0; i<jsonEstu.length(); i++){

                nombreEstu.add(jsonEstu.getJSONObject(i).getString("nom_usuario"));
                codEstu.add(Integer.valueOf(jsonEstu.getJSONObject(i).getString("ced_usuario")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        spinnerAsignatura = (Spinner) view.findViewById(R.id.spinner_selecionar_asignatura);
        spinnerEstudiante = (Spinner) view.findViewById(R.id.spinner_selecionar_estudiante);

        textInputLayoutTarea = (TextInputLayout) view.findViewById(R.id.txt_inputlayout_nomtarea);
        textInputEditTarea = (TextInputEditText) view.findViewById(R.id.txt_inputEdit_nomtarea);

        textInputLayoutNota = (TextInputLayout) view.findViewById(R.id.txt_inputlayout_nota_tarea);
        textInputEditNota = (TextInputEditText) view.findViewById(R.id.txt_inputEdit_nota_tarea);

        Button btnAgregarTarea = (Button) view.findViewById(R.id.btn_ingresar_tarea);
        Button btnCancelarTarea = (Button) view.findViewById(R.id.btn_cancelar_tarea);

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorTarea = "";
                String nombreTarea = "";
                Boolean activarInterface = true;
                if (TextUtils.isEmpty(textInputEditTarea.getText())) {
                    errorTarea = getString(R.string.campoVacio);
                    activarInterface = false;
                } else
                    nombreTarea = textInputEditTarea.getText().toString();
                mValidarTextInput(textInputLayoutTarea, errorTarea);


                errorTarea = "";
                String notaTarea = "";
                if (TextUtils.isEmpty(textInputEditNota.getText())) {
                    errorTarea = getString(R.string.campoVacio);
                    activarInterface = false;
                } else
                    notaTarea = textInputEditNota.getText().toString();
                mValidarTextInput(textInputLayoutNota, errorTarea);

            }

        });

        btnCancelarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        return builder.create();
    }

    /**
     * Valida que los datos del textInput no esten vacios
     */
    private void mValidarTextInput(TextInputLayout textInput, String mensajeError) {
        textInput.setError(mensajeError);
        if (mensajeError == null) {
            textInput.setErrorEnabled(false);
        } else
            textInput.setErrorEnabled(true);
    }

}
