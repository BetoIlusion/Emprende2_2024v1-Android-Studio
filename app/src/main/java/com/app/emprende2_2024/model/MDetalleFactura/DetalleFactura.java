package com.app.emprende2_2024.model.MDetalleFactura;

import com.app.emprende2_2024.model.MNotaVenta.NotaVenta;
import com.app.emprende2_2024.model.MProducto.Producto;

public class DetalleFactura {
    private int id;
    private int id_factura;
    private int cantidad;
    private float subtotal;
    private NotaVenta factura;
    private Producto producto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public NotaVenta getFactura() {
        return factura;
    }

    public void setFactura(NotaVenta factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", factura=" + factura +
                ", producto=" + producto +
                '}';
    }
}
