package com.app.emprende2_2024.PatronDecorador;

public class PromocionBase implements PromocionI {
    private int id;
    private String nombre;
    private String descripcion;

    public PromocionBase(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
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
