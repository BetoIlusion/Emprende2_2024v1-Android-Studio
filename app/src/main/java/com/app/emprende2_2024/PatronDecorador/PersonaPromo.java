package com.app.emprende2_2024.PatronDecorador;

import android.content.Context;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.view.VNotaVenta.VNotaVentaInsertar;
import java.util.Calendar;

public class PersonaPromo extends PromocionDecorator {
    private MPersona persona;
    private MDescuento descuento;
    private double descuentoFinal;

    public PersonaPromo(PromocionI promocion,
                        Context vInsertar,
                        MNotaVenta notaVenta) {
        super(promocion);
        this.descuento = new MDescuento(vInsertar);
        //obteninendo el valor del descuento de Persona Mes
        descuento = descuento.findById(2);
        this.persona = new MPersona(vInsertar);
        //obteniendo persona, de nuevo.
        persona = persona.findById(notaVenta.getPersona().getId());
        //frecuencia(valor) de la persona
        int frecPersona = persona.getFrecMM(persona.getId()) + 1;
        MFrecuencia mFrecuencia = new MFrecuencia(vInsertar);
        mFrecuencia = mFrecuencia.findBy(persona.getId(),"mm");
        int desc = descuento.getFrecuencia().getFrecuencia();
        if(frecPersona >= descuento.getFrecuencia().getFrecuencia()) {
            this.descuentoFinal = descuento.getPorcentaje();
            mFrecuencia.setFrecuencia(0);
        }else{
            mFrecuencia.setFrecuencia(frecPersona);
            this.descuentoFinal = 0;
        }
        mFrecuencia.update();

    }
    @Override
    public String getDescripcion(){
        return super.promocion.getDescripcion() + "(descuento persona: " + this.descuentoFinal + " %) ";
    }
    @Override
    public double calcularDescuento(){
      return super.promocion.calcularDescuento() + this.descuentoFinal;
    };

    private int obtenerUltimoDiaDelMes() {
        Calendar calendario = Calendar.getInstance();
        int ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
        return ultimoDia;
    }

    public int obtenerDiaActual() {
        Calendar calendario = Calendar.getInstance();
        int diaActual = calendario.get(Calendar.DAY_OF_MONTH);
        return diaActual;
    }


}
