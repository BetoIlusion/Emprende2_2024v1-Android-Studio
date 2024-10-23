package com.app.emprende2_2024.controller.CNotaVenta;

import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.model.MDetalleFactura.modelDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.modelNotaVenta;
import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProducto.modelProducto;
import com.app.emprende2_2024.view.VNotaVenta.MainActivity;
import com.app.emprende2_2024.view.VNotaVenta.VNotaVentaInsertar;

import java.util.ArrayList;

public class CNotaVenta {
    private MainActivity vMain;
    private VNotaVentaInsertar vInsertar;

    public CNotaVenta(VNotaVentaInsertar vFacturaInsertar) {
        this.vInsertar = vFacturaInsertar;
    }

    public CNotaVenta(MainActivity mainActivity) {
        this.vMain = mainActivity;
    }

    public void llenarSpinners() {
        VNotaVentaInsertar view = vInsertar;
        modelPersona persona = new modelPersona(view);
        modelCategoria categoria = new modelCategoria(view);

        ArrayList<modelPersona> personas = persona.read();
        ArrayList<modelCategoria> categorias = categoria.read();
        view.llenarSpinners(personas,categorias);
    }

    public void filtrarProductos(modelCategoria categoria) {
        VNotaVentaInsertar view = vInsertar;
        modelProducto producto = new modelProducto(view);

        ArrayList<modelProducto> productos = producto.fingByCategoria(categoria.getId());
        view.filtrarProductos(productos);
    }

    public void añadirDetalle(int cantidad,
                              modelProducto producto) {
        VNotaVentaInsertar view = vInsertar;
        modelDetalleNotaVenta detalle = new modelDetalleNotaVenta(view);
        if(cantidad < producto.getStock().getCantidad()){
            if(!existeDetalle(producto,cantidad)){
                    detalle.setCantidad(cantidad);
                    detalle.setSubtotal(producto.getPrecio() * cantidad);
                    detalle.setProducto(producto);
                    view.getDetalles().add(detalle);
            }
            actualizarMontoTotal();
            view.actualizar();
        }else{
            view.showSuccessMessage("Cantidad por encima del stock");
        }
//        try{
//
//            DetalleFactura detalle = new DetalleFactura();
//            float subtotal = (float) cantidad * producto.getPrecio();
//            MStock modelStock = new MStock(view);
//            Stock stock = modelStock.readUno(producto.getId());
//            if(cantidad <= stock.getCantidad()){
//                boolean existe = existeDetalle(producto, cantidad, subtotal);
//                if (!existe){
//                    detalle.setCantidad(cantidad);
//                    detalle.setSubtotal(subtotal);
//                    detalle.setProducto(producto);
//                    view.getDetalles().add(detalle);
//                }
//
//                actualizarMontoTotal();
//                view.actualizar();
//            }else{
//                view.mensaje("ERROR, cantidad por encima del stock(" + stock.getCantidad() + ")");
//            }
//
//
//        }catch (Exception e){
//            view.mensaje("ERROR EN CONTROLLER");
//            e.printStackTrace();
//        }

    }

    private void actualizarMontoTotal() {
        VNotaVentaInsertar view = vInsertar;
        float montoTotal = 0.0f;
        for (int i = 0; i < view.getDetalles().size(); i++) {
            montoTotal += view.getDetalles().get(i).getSubtotal();
        }
        view.getNotaVenta().setMonto_total(montoTotal);
    }

    private boolean existeDetalle(modelProducto producto, int cantidad) {
        VNotaVentaInsertar view = vInsertar;
        for (int i = 0; i < view.getDetalles().size(); i++) {
            if(view.getDetalles().get(i).getProducto().getId() == producto.getId()){
                view.getDetalles().get(i).setCantidad(cantidad);
                view.getDetalles().get(i).setSubtotal(producto.getPrecio() * cantidad);
                return true;
            }
        }
        return false;
    }

    public void calcularCambio(float efectivo) {
        VNotaVentaInsertar view = vInsertar;
        if (efectivo < view.getNotaVenta().getMonto_total()){
            view.mensaje("Efectivo insuficiente");
        }else{
            float cambio = efectivo - view.getNotaVenta().getMonto_total();
            view.getNotaVenta().setCambio(cambio);
            view.getNotaVenta().setEfectivo(efectivo);
            view.actualizarNotaVenta();
        }

    }

    public void craete(modelPersona cliente, modelNotaVenta notaVenta, ArrayList<modelDetalleNotaVenta> detalles) {
        VNotaVentaInsertar view = vInsertar;
        modelNotaVenta mNotaVenta = new modelNotaVenta(view);
        long id = mNotaVenta.create(
                notaVenta.getMonto_total(),
                notaVenta.getEfectivo(),
                notaVenta.getCambio(),
                cliente,
                detalles
                );
        if(id > 0){
            view.mensaje("COMPROBANTE CREADA");
            view.vShow(id);
        }else
            view.mensaje("ERROR AL CREAR COMPROBANTE");
    }

    public void read() {
        MainActivity view = vMain;
        modelNotaVenta mNotaVenta = new modelNotaVenta(view);

        ArrayList<modelNotaVenta> notaVentas = mNotaVenta.read();

        view.read(notaVentas);
//        MainActivity view = vMain;
//        MNotaVenta modelFactura = new MNotaVenta(view);
//        MPersona modelPersona = new MPersona(view);
//
//        ArrayList<NotaVenta> facturas = modelFactura.read();
//        ArrayList<Persona> personas = modelPersona.read();
//
//        view.read(facturas,personas);
    }
}
