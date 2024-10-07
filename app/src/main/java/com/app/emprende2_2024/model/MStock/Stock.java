package com.app.emprende2_2024.model.MStock;

public class Stock {
    private int id;
    private int cantidad;
    private int id_producto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "cantidad=" + cantidad +
                '}';
    }
}
