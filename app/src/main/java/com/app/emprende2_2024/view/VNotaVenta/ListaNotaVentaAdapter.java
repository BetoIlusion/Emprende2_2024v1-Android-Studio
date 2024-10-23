package com.app.emprende2_2024.view.VNotaVenta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.model.MNotaVenta.modelNotaVenta;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class ListaNotaVentaAdapter extends RecyclerView.Adapter<ListaNotaVentaAdapter.NotaVentaViewHolder>{
    ArrayList<modelNotaVenta> notaVentas;


    public ListaNotaVentaAdapter(ArrayList<modelNotaVenta> notaVentas) {
        this.notaVentas = notaVentas;

    }

    @NonNull
    @Override
    public NotaVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vfactura_item, null, false);
        return new NotaVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaVentaViewHolder holder, int position) {
        holder.tvFactura.setText("Nro: " + notaVentas.get(position).getId());
        holder.tvNombre.setText("Nombre: " + notaVentas.get(position).getPersona().getNombre());
        holder.tvCodigo.setText("Cod: " + notaVentas.get(position).getId_codigo());
    }

    @Override
    public int getItemCount() {
        return notaVentas.size();
    }

    public class NotaVentaViewHolder extends RecyclerView.ViewHolder{
        TextView tvFactura, tvNombre, tvCodigo, tvFecha;

        public NotaVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFactura = itemView.findViewById(R.id.viewIdFactura);
            tvNombre = itemView.findViewById(R.id.viewNombrePersona);
            tvCodigo = itemView.findViewById(R.id.viewCodigoX);
            tvFecha = itemView.findViewById(R.id.viewFecha);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VDetalleNotaVentaShow.class);
                    intent.putExtra("ID", notaVentas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
