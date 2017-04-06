package com.innovagenesis.aplicaciones.android.examennueve.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.activities.MainActivity;
import com.innovagenesis.aplicaciones.android.examennueve.adapters.RecyclerViewAdapaterAU;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsignaturaFragment extends Fragment {

    public static ArrayList<UsuariosAsigna> miLista;


    /** Instancia el fragmen*/
    public static AsignaturaFragment newInstances (ArrayList<UsuariosAsigna> list){

        AsignaturaFragment fragment = new AsignaturaFragment();
        miLista = list;
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asignatura, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerViewAdapaterAU adapter = new RecyclerViewAdapaterAU(getContext(),miLista);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



    }

    public void mListarDonantes(List<UsuariosAsigna> usuariosAsigna) {
        /** Llena el recyclerView en activity*/

    }
}
