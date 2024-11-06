package com.app.emprende2_2024.view.VProducto;

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
import com.app.emprende2_2024.controller.CProducto.CProducto;
import com.app.emprende2_2024.model.MProducto.MProducto;

import java.util.ArrayList;

public class ListaProductoAdapter extends RecyclerView.Adapter<ListaProductoAdapter.ProductoViewHolder> {
    ArrayList<MProducto> listaProducto;

    public ListaProductoAdapter(ArrayList<MProducto> productos) {
        this.listaProducto = productos;

    }
    public class ProductoViewHolder extends RecyclerView.ViewHolder{
        TextView viewNombre, viewSKU, viewPrecio, viewCantidad, viewCategoria, viewProveedor;
        ImageView ivEditar,ivEliminar;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewSKU = itemView.findViewById(R.id.viewSKU);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewCategoria = itemView.findViewById(R.id.viewNombreCategoria);
            viewProveedor = itemView.findViewById(R.id.viewNombreProveedor);
            ivEditar = itemView.findViewById(R.id.imageView1);
            ivEliminar = itemView.findViewById(R.id.imageView2);

        }
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vproducto_item, null, false);
        return new ListaProductoAdapter.ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.viewNombre.setText("Producto: " + listaProducto.get(position).getNombre());
        holder.viewSKU.setText("SKU: "  + listaProducto.get(position).getSku());
        holder.viewPrecio.setText(String.valueOf(listaProducto.get(position).getPrecio()));
        holder.viewCantidad.setText(String.valueOf(listaProducto.get(position).getStock().getCantidad()));
        holder.viewCategoria.setText("Categoria: " + listaProducto.get(position).getCategoria().getNombre());
        holder.viewProveedor.setText("Proveedor: " + listaProducto.get(position).getProveedor().getPersona().getNombre());
        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Context context = v.getContext();
                    int a = listaProducto.get(adapterPosition).getId();
                    Intent intent = new Intent(context,VProductoEditar.class);
                    intent.putExtra("ID",listaProducto.get(adapterPosition).getId());
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
                                final CProducto controller = new CProducto(v.getContext());
                                if (controller.delete(listaProducto.get(adapterPosition).getId())){
                                       Context context = v.getContext();
                                    Intent intent = new Intent(context, VProductoMain.class);
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

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }


}
