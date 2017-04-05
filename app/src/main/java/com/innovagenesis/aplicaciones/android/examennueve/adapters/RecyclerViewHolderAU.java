package com.innovagenesis.aplicaciones.android.examennueve.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.innovagenesis.aplicaciones.android.examennueve.R;

/**
 * Clase encargada de rellenar el recicler view
 * Created by alexi on 04/04/2017.
 */

public class RecyclerViewHolderAU extends RecyclerView.ViewHolder {

    private TextView codigo,descripcion;

    public RecyclerViewHolderAU(View itemView) {
        super(itemView);

        codigo = (TextView)itemView.findViewById(R.id.txtCodigo);
        descripcion = (TextView)itemView.findViewById(R.id.txtDescripcion);
    }
}
