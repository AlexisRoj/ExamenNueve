package com.innovagenesis.aplicaciones.android.examennueve.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import java.util.Collections;
import java.util.List;

/**
 * Clase encargada de llenar el recyclerView
 * Recycler
 * Created by alexi on 11/04/2017.
 */



public class RecyclerViewAdapterTarea extends RecyclerView.Adapter<RecyclerViewHolderTarea> {

    private List<Tareas> lista = Collections.emptyList();
    private LayoutInflater inflater;
    private Activity activity;

    public RecyclerViewAdapterTarea(List<Tareas> lista, Activity activity) {
        this.lista = lista;
        this.activity = activity;
        inflater =  LayoutInflater.from(activity);
    }

    @Override
    public RecyclerViewHolderTarea onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_template_tarea,parent,false);

        return new RecyclerViewHolderTarea(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderTarea holder, int position) {

        final Tareas current = lista.get(position);
        final int tareaBorrar = current.idTarea;

        holder.nom_tarea_item.setText(current.nomTarea);
        holder.nom_estudiante_item.setText(current.nomEstuTarea);
        holder.nom_asigna_item.setText(current.nomAsignaTarea);
        holder.nota_tarea_item.setText(String.valueOf(current.notaTarea));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
