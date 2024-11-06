package com.app.emprende2_2024.view.VNotaVenta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleNotaVenta;

import java.util.ArrayList;

public class ListaDetalleNotaVenta extends RecyclerView.Adapter<ListaDetalleNotaVenta.DetalleNotaVentaViewHolder> {
    ArrayList<MDetalleNotaVenta> detalles = new ArrayList<>();

    public ListaDetalleNotaVenta(ArrayList<MDetalleNotaVenta> detalles) {
        this.detalles = detalles;
    }
    public class DetalleNotaVentaViewHolder extends RecyclerView.ViewHolder{
        TextView tvProducto, tvCantidad, tvPrecio, tvSubtotal;
        ImageView ivEliminar;

        public DetalleNotaVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProductoDetalle);
            tvCantidad = itemView.findViewById(R.id.tvCantidadDetalle);
            tvPrecio = itemView.findViewById(R.id.tvPrecioDetalle);
            tvSubtotal = itemView.findViewById(R.id.tvSubDetalle);
            ivEliminar = itemView.findViewById(R.id.ivEliminarDetalle);
        }
    }
    @NonNull
    @Override
    public DetalleNotaVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_detalle_nota_venta_item,
                null, false);
        return new DetalleNotaVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetalleNotaVentaViewHolder holder, int position) {
        holder.tvProducto.setText(detalles.get(position).getProducto().getNombre());
        int cantidad = detalles.get(position).getCantidad();
        holder.tvCantidad.setText(String.valueOf(cantidad));
        float precio = detalles.get(position).getProducto().getPrecio();
        holder.tvPrecio.setText(String.valueOf(precio));
        double sub = detalles.get(position).getSubtotal();
        holder.tvSubtotal.setText(String.valueOf(sub));
        holder.ivEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalles.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), detalles.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return detalles.size();
    }



}
