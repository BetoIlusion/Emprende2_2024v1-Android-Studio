package com.app.emprende2_2024.PatronDecorador;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.view.VNotaVenta.VNotaVentaInsertar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaPromo extends PromocionDecorator {
    MDescuento descuento;
    private double descuentoFinal;
    public FechaPromo(PromocionI IPromocion, VNotaVentaInsertar vInsertar) {
        super(IPromocion);
        this.descuento = new MDescuento(vInsertar);
        this.descuento = this.descuento.get();
        // Obtener la fecha actual en el mismo formato que `fechaFestivo`
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");
        String fechaActual = dateFormat.format(new Date());

        if (this.descuento.getFechaFestivo().equals(fechaActual)) {
            this.descuentoFinal = this.descuento.getDescuento_festejo();
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
