package com.innovagenesis.aplicaciones.android.examennueve.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsignaturaFragment extends Fragment {


    public static final String KEY = "key";

    public static AsignaturaFragment newInstances (ArrayList<UsuariosAsigna> list){



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asignatura, container, false);
    }

}
