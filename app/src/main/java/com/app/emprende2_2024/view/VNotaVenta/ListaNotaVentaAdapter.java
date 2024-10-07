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
import com.app.emprende2_2024.model.MNotaVenta.NotaVenta;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class ListaNotaVentaAdapter extends RecyclerView.Adapter<ListaNotaVentaAdapter.FacturaViewHolder>{
    ArrayList<NotaVenta> facturas;
    ArrayList<Persona> personas;

    public ListaNotaVentaAdapter(ArrayList<NotaVenta> facturas, ArrayList<Persona> personas) {
        this.facturas = facturas;
        this.personas = personas;
    }

    @NonNull
    @Override
    public FacturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vfactura_item, null, false);
        return new ListaNotaVentaAdapter.FacturaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaViewHolder holder, int position) {
        holder.tvFactura.setText("Nro: " + facturas.get(position).getId());
        for (int i = 0; i < personas.size(); i++) {
            if (facturas.get(position).getId_persona() == personas.get(i).getId()){
                holder.tvNombre.setText("Nombre: " + personas.get(i).getNombre());
                break;
            }
        }
        holder.tvCodigo.setText("Cod: " + facturas.get(position).getId_codigo());

    }

    @Override
    public int getItemCount() {
        return facturas.size();
    }

    public class FacturaViewHolder extends RecyclerView.ViewHolder{
        TextView tvFactura, tvNombre, tvCodigo, tvFecha;

        public FacturaViewHolder(@NonNull View itemView) {
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
                    intent.putExtra("ID", facturas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
