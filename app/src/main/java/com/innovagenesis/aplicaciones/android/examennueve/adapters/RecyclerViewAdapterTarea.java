package com.innovagenesis.aplicaciones.android.examennueve.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.examennueve.DiccionarioDatos;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.asynctask.BorrarTareaAsyncTask;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.Tareas;

import java.net.MalformedURLException;
import java.net.URL;
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

    private String nomTarea, nomEstuTarea, nomAsignaTarea;
    private int notaTarea,idTarea;


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
    public void onBindViewHolder(final RecyclerViewHolderTarea holder, int position) {

        final Tareas current = lista.get(position);
        final int tareaBorrar = current.idTarea;

        holder.nom_tarea_item.setText(current.nomTarea);
        holder.nom_estudiante_item.setText(current.nomEstuTarea);
        holder.nom_asigna_item.setText(current.nomAsignaTarea);
        holder.nota_tarea_item.setText(String.valueOf(current.notaTarea));



        holder.imgEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idTarea = current.idTarea;
                nomTarea = current.nomTarea;
                nomEstuTarea = current.nomEstuTarea;
                nomAsignaTarea = current.nomAsignaTarea;
                notaTarea = current.notaTarea;

                Bundle bundle = new Bundle();

                bundle.putInt(DiccionarioDatos.idTarea,idTarea);
                bundle.putString(DiccionarioDatos.nomTarea,nomTarea);
                bundle.putString(DiccionarioDatos.nomAsignaTarea,nomAsignaTarea);
                bundle.putString(DiccionarioDatos.nomEstuTarea,nomEstuTarea);
                bundle.putInt(DiccionarioDatos.notaTarea,notaTarea);
                listener.EditarElementoRecycler(bundle);

            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //idTarea = current.idTarea;
                mElimarTarea(holder,tareaBorrar).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public AlertDialog mElimarTarea(final RecyclerViewHolderTarea holder, final int idTareaBorrar) {

        /** Dialogo que borra la cuenta de usuario en la que se encuentra*/
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(R.string.eliminarTarea)
                .setMessage(R.string.borrarTarea)
                .setPositiveButton(R.string.aceptar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int item = holder.getAdapterPosition();
                                try {
                                    /** Remueve el donante de la base de datos*/
                                    new BorrarTareaAsyncTask(activity).execute(
                                            new URL(DiccionarioDatos.URL_SERVICIO_TAREA
                                                    + idTareaBorrar));
                                    /** Remuelve el elemento del recyclerView*/
                                    mRemoverTareaRecycler(item);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton(activity.getString(R.string.cancelar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }

    private void mRemoverTareaRecycler(int item) {
        //Elimina los items del recyclerView
        lista.remove(item);
        notifyItemRemoved(item);
        notifyItemRangeChanged(item, lista.size());
    }
}
