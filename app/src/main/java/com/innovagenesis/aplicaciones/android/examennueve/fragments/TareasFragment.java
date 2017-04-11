package com.innovagenesis.aplicaciones.android.examennueve.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.adapters.RecyclerViewAdapterTarea;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TareasFragment extends Fragment {

    private static Bundle bundle;
    private static ArrayList<Tareas> miLita;


    public static TareasFragment newInstance (ArrayList<Tareas> listaTareas){

        TareasFragment fragment = new TareasFragment();

        bundle = new Bundle();
        bundle.putSerializable("lista",listaTareas);
        fragment.setArguments(bundle);

        return fragment;
    }


    public TareasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tareas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        miLita = (ArrayList<Tareas>) bundle.getSerializable("lista");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTareas);
        RecyclerViewAdapterTarea adapterTarea = new RecyclerViewAdapterTarea(miLita, getActivity());
        recyclerView.setAdapter(adapterTarea);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}
