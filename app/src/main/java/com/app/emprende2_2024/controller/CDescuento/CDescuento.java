package com.app.emprende2_2024.controller.CDescuento;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;
import com.app.emprende2_2024.view.VDescuento.VDescuento;

public class CDescuento {
    VDescuento vDescuento;
    public CDescuento(VDescuento vDescuento) {
        this.vDescuento = vDescuento;
    }

    public void llenarVista() {
        MDescuento mDescuento = new MDescuento(vDescuento);
        mDescuento = mDescuento.get();
        MFrecuencia mFrecuencia = new MFrecuencia(vDescuento);
        mFrecuencia = mFrecuencia.get();
        vDescuento.llenarVista(mDescuento,mFrecuencia);
    }


    public void update(String descuentoPersona, String descuentoFestejo, String frecuenciaPersona, String fechaFestejo) {
        MDescuento mDescuento = new MDescuento(vDescuento);
        mDescuento.update(descuentoPersona, descuentoFestejo, fechaFestejo);
        MFrecuencia mFrecuencia = new MFrecuencia(vDescuento);
        mFrecuencia = mFrecuencia.get();
        mFrecuencia.setFrecuencia_mes(Integer.parseInt(frecuenciaPersona));
        mFrecuencia.update(mFrecuencia);
        vDescuento.mensaje("Descuento actualizado");
    }
}
