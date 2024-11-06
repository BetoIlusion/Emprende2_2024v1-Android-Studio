package com.app.emprende2_2024.view.VCategoria;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;

import java.util.ArrayList;

public class ListaCategoriasAdapter extends RecyclerView.Adapter<ListaCategoriasAdapter.CategoriaViewHolder> {
    ArrayList<MCategoria> listaCategoria;
    public ListaCategoriasAdapter(ArrayList<MCategoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }
    public class CategoriaViewHolder extends RecyclerView.ViewHolder{
        TextView viewNombre, viewDescricion;
        ImageView ivEditar, ivEliminar;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombreCategoria);
            viewDescricion = itemView.findViewById(R.id.viewDescripcionCategoria);
            ivEditar = itemView.findViewById(R.id.imageView1);
            ivEliminar = itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VCategoriaProductoShowPDF.class);
                    intent.putExtra("ID", listaCategoria.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public ListaCategoriasAdapter.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vcategoria_item, null, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaCategoriasAdapter.CategoriaViewHolder holder, int position) {
        holder.viewNombre.setText("Nombre: " + listaCategoria.get(position).getNombre());
        holder.viewDescricion.setText(listaCategoria.get(position).getDescripcion());
        //holder.viewTelefono.setText(String.valueOf(listaPersonas.get(position).getTelefono()));
        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VCategoriaEditar.class);
                    intent.putExtra("ID", listaCategoria.get(adapterPosition).getId());
                    context.startActivity(intent);
                }
            }
        });
        holder.ivEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("¿Desea eliminar este contacto?").
                        setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int adapterPosition = holder.getAdapterPosition();
                                //final MPersona model = new MPersona(v.getContext());
                                final CCategoria controller = new CCategoria(v.getContext());
                                if (controller.delete(listaCategoria.get(adapterPosition).getId())){
                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, VCategoriaMain.class);
                                    context.startActivity(intent);
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }
    // Método para actualizar la lista
//    public void updateList(ArrayList<Categoria> nuevaLista) {
//        listaCategoria = nuevaLista;
//        notifyDataSetChanged(); // Actualiza la vista del RecyclerView
//    }
    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }
}
