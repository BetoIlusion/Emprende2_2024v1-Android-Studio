package com.app.emprende2_2024.model.MProducto;

public class ProductoFull {
    private int id;
    private String nombre;
    private String sku;
    private float precio;
    private int cantidad;
    private int id_categoria;
    private String nombreCategoria;
    private int id_proveedor;
    private String nombreProveedor;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    //    public ProductoFull(int id, String nombre, String sku, float precio, int id_categoria, String nombreCategoria, int id_proveedor, String nombreProveedor) {
//
//        this.id = id;
//        this.nombre = nombre;
//        this.sku = sku;
//        this.precio = precio;
//        this.id_categoria = id_categoria;
//        this.nombreCategoria = nombreCategoria;
//        this.id_proveedor = id_proveedor;
//        this.nombreProveedor = nombreProveedor;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }
}
