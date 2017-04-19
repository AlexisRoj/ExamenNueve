package com.innovagenesis.aplicaciones.android.examennueve.dialogos;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

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

    public static final String TAG_DIALOGO = "dialogo_agregar_tarea";
    public static String jsonAsigna = "json_asigna";
    public static String jsonEstu = "json_estudiante";
    private static Bundle argumentos;

    private int idUsuario;
    Boolean activarInterface = true;
    private Boolean nuevaTarea = true;
    /**
     * Spinners y Json
     */
    private ArrayList<String> nombreAsigna;
    private ArrayList<String> codAsigna;
    private ArrayList<String> nombreEstu;
    private ArrayList<String> codEstu;

    public interface DatosGuardarTarea {
        void GuardarTarea(Tareas tareas, Boolean nuevaTarea);
    }

    public static DatosGuardarTarea listener;

    /**
     * Recibe dos json los cuales despues los deserializa para llenar los spinner
     */

    public static DialogoAgregarTareas newInstance
    (String jsonEstudiante, String jsonAsignatura, Activity activity) {

        DialogoAgregarTareas fragment = new DialogoAgregarTareas();
        argumentos = new Bundle();
        argumentos.putString(jsonAsigna, jsonAsignatura);
        argumentos.putString(jsonEstu, jsonEstudiante);
        fragment.setArguments(argumentos);

        try {
            listener = (DatosGuardarTarea) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException
                    ("La interface ListarUsuarioAsyncTask no ha sido implementada");
        }

        return fragment;
    }
    /**
     * Spinner & TextInput
     */
    public Spinner spinnerAsignatura;
    public Spinner spinnerEstudiante;

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
        /*
        Cuando inicia el dialogo arranca la variable nueva tarea en verdadero
        si vienen args cuando instancia el dialogo, cambia a false, la funcionalidad
        es cambiar el texto del boton
        */

        nuevaTarea = true;

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
            codAsigna.add("-0");

            /** Deserializa el json*/
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
            codEstu.add(getString(R.string.selectEstudiante));

            /** Deserializa el json*/
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

        final Button btnAgregarTarea = (Button) view.findViewById(R.id.btn_ingresar_tarea);
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
                /* Devuelve el arreglo de codigo estudiante*/
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
                /*Valida que los campos no esten vacios*/
                String errorTarea = "";
                String nombreTarea = "";
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

                /* Validación de spinner que esten selecionados*/
                if (codAsignatura[0].equals("-0")) {
                    activarInterface = false;
                    Toast.makeText(getContext(), R.string.asignaNull, Toast.LENGTH_SHORT).show();
                }
                if (codAsignatura[0].equals("-0")) {
                    activarInterface = false;
                    Toast.makeText(getContext(), R.string.estuNull, Toast.LENGTH_SHORT).show();
                }

                /*Cambia el texto del bonton de guardar a actualizar*/
                if (nuevaTarea) {
                    btnAgregarTarea.setText(getContext().getResources().getString(R.string.actualizar));
                }

                /*Construcción de empaquetado que se envia a guardar, valida que no
                * existan campos vacios*/
                if (activarInterface) {
                    Tareas tareas = new Tareas();
                    if (!nuevaTarea){
                        tareas.setIdTarea(idUsuario);
                    }
                    tareas.setNomTarea(nombreTarea);
                    tareas.setIdAsignaTarea(Integer.valueOf(codAsignatura[0]));
                    tareas.setIdEstuTarea(Integer.valueOf(codEstudiante[0]));
                    tareas.setNotaTarea(Integer.valueOf(notaTarea));
                    /* Cuando trae argumentos lo pasa a true y es actualizar*/
                    listener.GuardarTarea(tareas,nuevaTarea);
                    dismiss();
                }
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
        if ((args != null) && !args.isEmpty()) {
            nuevaTarea = false;
            //textInputEditTarea.setFocusable(false); //Desabilita nombre de la tarea
            btnAgregarTarea.setText(R.string.actualizar);
            idUsuario = args.getInt(DiccionarioDatos.idTarea);
            /** Metodo encargado de instanciar y asignar los valores de la ediccion*/
            mRellenarEdit(spinnerAsignatura, spinnerEstudiante, args);
        }
        return builder.create();
    }

    /** Metodo encargado de llenar los editText cuando viene con un argumento*/
    private void mRellenarEdit(Spinner spinnerAsignatura, Spinner spinnerEstudiante, Bundle args) {
        textInputEditTarea.setText(args.getString(DiccionarioDatos.nomTarea));
        textInputEditNota.setText(String.valueOf(args.getInt(DiccionarioDatos.notaTarea)));

        spinnerAsignatura.setSelection(getIndex(spinnerAsignatura,
                args.getString(DiccionarioDatos.nomAsignaTarea)));

        spinnerEstudiante.setSelection(getIndex(spinnerEstudiante,
                args.getString(DiccionarioDatos.nomEstuTarea)));
    }
    /**
     * Recorre el spinner para realizar la seleccion, cuando se encuentra en modo
     * editar
     */
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        if (myString!= null){
            int contar = spinner.getCount();
            for (int i = 0; i <= contar; i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                    index = i;
                    break;
                }
            }
            return index;
        }
        return 0;
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

    /**
     * Encargado de limpiar los argumuentos del activity
     * */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.getArguments()!= null)
            this.getArguments().clear();
    }
}
