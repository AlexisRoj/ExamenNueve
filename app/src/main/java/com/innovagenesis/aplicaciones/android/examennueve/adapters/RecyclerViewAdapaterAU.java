package com.innovagenesis.aplicaciones.android.examennueve.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.innovagenesis.aplicaciones.android.examennueve.R;
import com.innovagenesis.aplicaciones.android.examennueve.instancias.UsuariosAsigna;
import java.util.Collections;
import java.util.List;

/**
 * Adaptardor
 * Created by alexi on 04/04/2017.
 */

public class RecyclerViewAdapaterAU extends RecyclerView.Adapter<RecyclerViewHolderAU> {

    private Context context;
    private LayoutInflater inflater;
    private List<UsuariosAsigna> data = Collections.emptyList();

    public RecyclerViewAdapaterAU(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerViewHolderAU onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_template_item,parent,false);
        return new RecyclerViewHolderAU(view);
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolderAU holder, int position) {

        UsuariosAsigna actual = data.get(position);

        holder.descripcion.setText(actual.descripcion);
        holder.codigo.setText(String.valueOf(actual.codigo));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
