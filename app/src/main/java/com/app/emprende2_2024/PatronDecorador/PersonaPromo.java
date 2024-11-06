package com.app.emprende2_2024.PatronDecorador;

import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.view.VNotaVenta.VNotaVentaInsertar;
import java.util.Calendar;

public class PersonaPromo extends PromocionDecorator {
    private MPersona persona;
    private MFrecuencia frecuencia;
    private MDescuento descuento;
    private double descuentoFinal;
    public PersonaPromo(PromocionI promocion,
                        VNotaVentaInsertar vInsertar,
                        MNotaVenta notaVenta) {
        super(promocion);
        this.frecuencia = new MFrecuencia(vInsertar);
        this.frecuencia = frecuencia.get();
        this.persona = new MPersona(vInsertar);
        this.descuento = new MDescuento(vInsertar);
        this.descuento = this.descuento.get();
        this.persona = this.persona.findById(notaVenta.getPersona().getId());

        if(persona.getFrecuencia() >=  frecuencia.getFrecuencia_mes()){
            persona.setFrecuencia(0);
            persona.update(persona);
            this.descuentoFinal = descuento.getDescuento_persona();
        }else{
            this.persona.setFrecuencia(persona.getFrecuencia()+1);
            this.persona.update(persona);
            this.descuentoFinal = 0;
        }
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
