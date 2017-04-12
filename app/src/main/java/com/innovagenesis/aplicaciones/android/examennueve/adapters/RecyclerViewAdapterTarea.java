package com.innovagenesis.aplicaciones.android.examennueve.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
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

    public interface mEditarElementoRecycler{
        void EditarElementoRecycler(Bundle bundle);
    }

    public mEditarElementoRecycler listener;

    public RecyclerViewAdapterTarea(List<Tareas> lista, Activity activity) {
        this.lista = lista;
        this.activity = activity;
        inflater =  LayoutInflater.from(activity);

        try {
            listener = (mEditarElementoRecycler)activity;
        } catch (Exception e) {
            e.printStackTrace();
        }

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


        holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nomTarea, nomEstuTarea, nomAsignaTarea;
                int notaTarea;

                nomTarea = current.nomTarea;
                nomEstuTarea = current.nomEstuTarea;
                nomAsignaTarea = current.nomAsignaTarea;
                notaTarea = current.notaTarea;

                Bundle bundle = new Bundle();

                bundle.putString(DiccionarioDatos.nomTarea,nomTarea);
                bundle.getString(DiccionarioDatos.nomEstuTarea,nomEstuTarea);
                bundle.putString(DiccionarioDatos.nomAsignaTarea,nomAsignaTarea);
                bundle.putInt(DiccionarioDatos.notaTarea,notaTarea);

                listener.EditarElementoRecycler(bundle);

            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
