package com.app.emprende2_2024.controller.CDescuento;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;
import com.app.emprende2_2024.view.VDescuento.VDescuento;

import java.util.ArrayList;

public class CDescuento {
    VDescuento vDescuento;
    public CDescuento(VDescuento vDescuento) {
        this.vDescuento = vDescuento;
    }

    public void llenarVista() {
        MDescuento mDescuento = new MDescuento(vDescuento);
        ArrayList<MDescuento> arrayList = mDescuento.read();

        vDescuento.llenarVista(arrayList);
    }


    public void update(String descuentoPersona, String descuentoFestejo, String frecuenciaPersona, String fechaFestejo) {
        MDescuento mDescuentoPersona = new MDescuento(vDescuento);
        mDescuentoPersona = mDescuentoPersona.findByNombre("Persona");
        mDescuentoPersona.setPorcentaje(Double.valueOf(descuentoPersona));
        mDescuentoPersona.setFrecuencia().setFrecuencia(Integer.valueOf(frecuenciaPersona));
        mDescuentoPersona.update();

        MDescuento mDescuentoFestejo = new MDescuento(vDescuento);
        mDescuentoFestejo = mDescuentoFestejo.findByNombre("Festejo");
        mDescuentoFestejo.setPorcentaje(Double.valueOf(descuentoFestejo));
        mDescuentoFestejo.setFecha_inicio(fechaFestejo);
        mDescuentoFestejo.update();

        vDescuento.mensaje("Descuento actualizado");
    }
}
