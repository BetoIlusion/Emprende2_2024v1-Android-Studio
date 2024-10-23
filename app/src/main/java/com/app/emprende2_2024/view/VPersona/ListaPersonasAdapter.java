package com.app.emprende2_2024.view.VPersona;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CPersona.CPersona;
import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
import com.app.emprende2_2024.view.VProveedor.VProveedorEditar;

import java.util.ArrayList;

public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.PersonaViewHolder> {
    ArrayList<modelPersona> listaPersonas;
    ArrayList<modelProveedor> listaProveedor;

    public ListaPersonasAdapter(ArrayList<modelPersona> listaPersonas, ArrayList<modelProveedor> listaProveedor) {
        this.listaPersonas = listaPersonas;
        this.listaProveedor = listaProveedor;
    }
    public class PersonaViewHolder extends RecyclerView.ViewHolder{
        TextView viewNombre, viewTelefono, viewDireccion, viewCorreo, viewTipoCliente;
        TextView viewNIT;
        ImageView ivEditar, ivEliminar;
        public PersonaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewNIT = itemView.findViewById(R.id.viewNIT);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewDireccion = itemView.findViewById(R.id.viewDireccion);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);
            viewTipoCliente = itemView.findViewById(R.id.viewTipoCliente);
            ivEditar = itemView.findViewById(R.id.imageView1);
            ivEliminar = itemView.findViewById(R.id.imageView2);
        }
    }

    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vpersona_item, null, false);
        return new PersonaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPersonasAdapter.PersonaViewHolder holder, int position) {
        holder.viewNombre.setText("Nombre: " + listaPersonas.get(position).getNombre());
        holder.viewTelefono.setText(String.valueOf(listaPersonas.get(position).getTelefono()));
        holder.viewDireccion.setText(listaPersonas.get(position).getDireccion());
        holder.viewCorreo.setText(listaPersonas.get(position).getCorreo());
        holder.viewTipoCliente.setText(listaPersonas.get(position).getTipo_cliente());
        if (!listaPersonas.get(position).getTipo_cliente().equals("Proveedor")){
            holder.viewNIT.setVisibility(View.GONE);
        }else{
            for (int i = 0; i < listaProveedor.size(); i++) {
                if (listaProveedor.get(i).getPersona().getId() == listaPersonas.get(position).getId()){
                    holder.viewNIT.setText("NIT: " + listaProveedor.get(i).getNit());
                }
            }
        }
        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Context context = v.getContext();
                    if (listaPersonas.get(adapterPosition).getTipo_cliente().toString().equals("Proveedor")){
                        Intent intent = new Intent(context, VProveedorEditar.class);
                        intent.putExtra("ID", listaPersonas.get(adapterPosition).getId());
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, VPersonaEditar.class);
                        intent.putExtra("ID", listaPersonas.get(adapterPosition).getId());
                        context.startActivity(intent);
                    }

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
                                final CPersona controller = new CPersona(v.getContext());
                                if (controller.delete(listaPersonas.get(adapterPosition).getId())){
                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, VPersonaMain.class);
                                    context.startActivity(intent);
                                }else
                                    Toast.makeText(v.getContext(), "ERROR AL ELIMINAR", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void  onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }
    // Método para actualizar la lista
    public void updateList(ArrayList<modelPersona> nuevaLista) {
        listaPersonas = nuevaLista;
        notifyDataSetChanged(); // Actualiza la vista del RecyclerView
    }
    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }
}
