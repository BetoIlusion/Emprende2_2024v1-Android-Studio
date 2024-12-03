package com.app.emprende2_2024.PatronDecorador;

public class PromocionBase implements PromocionI {

    private String descripcion;

    public PromocionBase( String descripcion) {
        this.descripcion = descripcion;
    }
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public double calcularDescuento() {
        return 0;
    }
}
