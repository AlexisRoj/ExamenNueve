package com.innovagenesis.aplicaciones.android.examennueve.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovagenesis.aplicaciones.android.examennueve.R;

/**
 * Clase encargada de usar el template para ingresar
 * elementos en el recyclerView
 * Created by alexi on 11/04/2017.
 */

public class RecyclerViewHolderTarea extends RecyclerView.ViewHolder {

    public ImageView imgEditar, imgDelete;
    public TextView nom_tarea_item, nom_asigna_item, nom_estudiante_item, nota_tarea_item;

    public RecyclerViewHolderTarea(View itemView) {
        super(itemView);

        imgEditar = (ImageView)itemView.findViewById(R.id.img_edit_tarea);
        imgDelete = (ImageView) itemView.findViewById(R.id.img_delete_tarea);

        nom_tarea_item = (TextView) itemView.findViewById(R.id.nom_tarea_item);
        nom_asigna_item = (TextView) itemView.findViewById(R.id.nom_asigna_item);
        nom_estudiante_item = (TextView) itemView.findViewById(R.id.nom_estudiante_item);
        nota_tarea_item = (TextView) itemView.findViewById(R.id.nota_tarea_item);
    }
}
