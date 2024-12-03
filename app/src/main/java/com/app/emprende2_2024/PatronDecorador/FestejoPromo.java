package com.app.emprende2_2024.PatronDecorador;

import android.content.Context;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.view.VNotaVenta.VNotaVentaInsertar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FestejoPromo extends PromocionDecorator {
    MDescuento descuento;
    private double descuentoFinal;
    public FestejoPromo(PromocionI IPromocion, Context vInsertar) {
        super(IPromocion);
        this.descuento = new MDescuento(vInsertar);
        this.descuento = this.descuento.findByNombre("Festejo");
        // Obtener la fecha actual en el mismo formato que `fechaFestivo`
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());

        if (this.descuento.getFecha_inicio().equals(fechaActual)) {
            this.descuentoFinal = this.descuento.getPorcentaje();
        }else
            this.descuentoFinal = 0;
    }
    @Override
    public String getDescripcion(){
        return super.getDescripcion() + " (Fecha Promocion: " + this.descuentoFinal + " %)";
    }
    @Override
    public double calcularDescuento(){
        return super.calcularDescuento() + this.descuentoFinal;
    }
}
