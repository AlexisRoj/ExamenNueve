package com.innovagenesis.aplicaciones.android.examennueve.dialogos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
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
    public static String jsonTare = "json_tarea";
    public String nombreTarea = "nombre_tarea";
    private static Bundle argumentos;

    private Boolean nuevoDonante = true;
    /**
     * Spinners y Json
     */
    private ArrayList<String> nombreAsigna;
    private ArrayList<String> codAsigna;
    private ArrayList<String> nombreEstu;
    private ArrayList<String> codEstu;

    private interface DatosGuardarTarea {
        void GuardarTarea(Tareas tareas);
    }

    /**
     * Recibe dos json los cuales despues los deserializa para llenar los spinner
     */
    public static DialogoAgregarTareas newInstance
    (String jsonEstudiante, String jsonAsignatura, String jsonTarea) {

        DialogoAgregarTareas fragment = new DialogoAgregarTareas();

        argumentos = new Bundle();
        argumentos.putString(jsonAsigna, jsonAsignatura);
        argumentos.putString(jsonEstu, jsonEstudiante);
        argumentos.putString(jsonTare, jsonTarea);
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

        @SuppressLint("InflateParams") final
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialogo_agregar_tarea, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        String deserializaAsinga = argumentos.getString(jsonAsigna);
        String deserializaEstud = argumentos.getString(jsonEstu);

        try {
            /*
             * Seccion encargada de deserializar el json de asignatura
             * se utilizan dos arrglos por uno es para mostrar y el
             * otro es para guardar los datos, es el codigo
             * */
            JSONArray jsonAsina = new JSONArray(deserializaAsinga);

            nombreAsigna = new ArrayList<>();
            codAsigna = new ArrayList<>();

            nombreAsigna.add(getString(R.string.spinerMateria));
            /**
             *
             * TODO recordar quitar uno a el contador de codigo, para que guarde el codigo correcto
             *
             * */

            for (int i = 0; i < jsonAsina.length(); i++) {

                nombreAsigna.add(jsonAsina.getJSONObject(i).getString("nom_asigna"));
                codAsigna.add(jsonAsina.getJSONObject(i).getString("id_asigna"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            JSONArray jsonEstu = new JSONArray(deserializaEstud);

            nombreEstu = new ArrayList<>();
            codEstu = new ArrayList<>();

            nombreEstu.add(getString(R.string.selectEstudiante));
            /**
             *
             * TODO recordar quitar uno a el contador de codigo, para que guarde el codigo correcto
             *
             * */

            for (int i = 0; i < jsonEstu.length(); i++) {

                nombreEstu.add(jsonEstu.getJSONObject(i).getString("nom_usuario"));
                codEstu.add(jsonEstu.getJSONObject(i).getString("ced_usuario"));

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


        /* Sesión de los spinner materia
        * Es llenado con el json y se le agrega un elemento arriba para que haga el hint
        * */

        final ArrayAdapter<String> stringArrayAdapterMateria = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, nombreAsigna) {

            @Override
            public boolean isEnabled(int position) {
                //Desabilita el primer elemento de la lista del spinner
                return position != 0;
            }

            @Override
            public View getDropDownView
                    (int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view1 = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view1;

                if (position == 0) {
                    /** Funciona para efecto visual de un elemento del spinner*/
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };


        final String[] codAsignatura = new String[1];

        spinnerAsignatura.setAdapter(stringArrayAdapterMateria);
        spinnerAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Se corrije solo dado que se le envia la misma posicion al arreglo,
                // como si tuviera otro elemento incluido
                codAsignatura[0] = codAsigna.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        /* Spinner Usuario o Estudiante*/

        ArrayAdapter<String> stringArrayAdapterEstu = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_item, nombreEstu) {

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view1 = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view1;

                if (position == 0) {
                    /** Funciona para efecto visual de un elemento del spinner*/
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view1;
            }
        };

        final String[] codEstudiante = new String[1];

        spinnerEstudiante.setAdapter(stringArrayAdapterEstu);
        spinnerEstudiante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                codEstudiante[0] = codEstu.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /* Seccion de los botones del dialgo */

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


        /** Trae los argumentos desde el adapter, pasa por el activity*/
        Bundle args = getArguments();
        if (args != null) {
            nuevoDonante = false;
            textInputEditTarea.setFocusable(false);
            btnAgregarTarea.setText(R.string.actualizar);
            /** Metodo encargado de instanciar y asignar los valores de la ediccion*/
            mRellenarEdit(spinnerAsignatura, spinnerEstudiante, args);
        }


        return builder.create();
    }

    private void mRellenarEdit(Spinner spinnerAsignatura, Spinner spinnerEstudiante, Bundle args) {

        textInputEditTarea.setText(args.getString(DiccionarioDatos.nomTarea));
        textInputEditNota.setText(String.valueOf(args.getInt(DiccionarioDatos.notaTarea)));

        spinnerAsignatura.setSelection(getIndex(spinnerAsignatura,
                args.getString(DiccionarioDatos.nomAsignaTarea)));

    }


    /** Recorre el spinner para realizar la seleccion*/
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i <= spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
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
