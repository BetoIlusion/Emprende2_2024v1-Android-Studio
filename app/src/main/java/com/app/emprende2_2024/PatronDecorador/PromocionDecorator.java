package com.app.emprende2_2024.PatronDecorador;

public class PromocionDecorator implements PromocionI {
    protected PromocionI promocion;

    public PromocionDecorator(PromocionI promocion) {
        this.promocion = promocion;
    }

    @Override
    public String getDescripcion() {
        return promocion.getDescripcion();
    }

    @Override
    public double calcularDescuento() {
        return promocion.calcularDescuento();
    }
}
