package com.app.emprende2_2024.controller.CNotaVenta;

import com.app.emprende2_2024.PatronDecorador.FechaPromo;
import com.app.emprende2_2024.PatronDecorador.PersonaPromo;
import com.app.emprende2_2024.PatronDecorador.PromocionBase;
import com.app.emprende2_2024.PatronDecorador.PromocionI;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MProducto.MProducto;
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
        MPersona persona = new MPersona(view);
        MCategoria categoria = new MCategoria(view);

        ArrayList<MPersona> personas = persona.read();
        ArrayList<MCategoria> categorias = categoria.read();
        view.llenarSpinners(personas,categorias);
    }

    public void filtrarProductos(MCategoria categoria) {
        VNotaVentaInsertar view = vInsertar;
        MProducto producto = new MProducto(view);

        ArrayList<MProducto> productos = producto.fingByCategoria(categoria.getId());
        view.filtrarProductos(productos);
    }

    public void añadirDetalle(int cantidad,
                              MProducto producto) {
        VNotaVentaInsertar view = vInsertar;
        MDetalleNotaVenta detalle = new MDetalleNotaVenta(view);
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
            view.mensaje("Cantidad por encima del stock");
        }
    }

    private void actualizarMontoTotal() {
        VNotaVentaInsertar view = vInsertar;
        float montoTotal = 0.0f;
        for (int i = 0; i < view.getDetalles().size(); i++) {
            montoTotal += view.getDetalles().get(i).getSubtotal();
        }
        view.getNotaVenta().setMonto_total(montoTotal);
    }

    private boolean existeDetalle(MProducto producto, int cantidad) {
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

    public void create(MPersona cliente, MNotaVenta notaVenta, ArrayList<MDetalleNotaVenta> detalles) {
        VNotaVentaInsertar view = vInsertar;
        notaVenta.setPersona(cliente);
        notaVenta = actualizarDescuentos(notaVenta);
        long id = notaVenta.create(
                notaVenta.getMonto_total(),
                notaVenta.getEfectivo(),
                notaVenta.getCambio(),
                cliente,
                detalles,
                notaVenta.getDescuentoPorcentual(),
                notaVenta.getDescuentoString()
                );
        if(id > 0){
            view.mensaje("COMPROBANTE CREADA");
            view.vShow(id);
        }else
            view.mensaje("ERROR AL CREAR COMPROBANTE");
    }


    private MNotaVenta actualizarDescuentos(MNotaVenta notaVenta) {
        
        PromocionI promocionI = new PromocionBase(0,"","");
        promocionI = new PersonaPromo(promocionI,vInsertar,notaVenta);
        promocionI = new FechaPromo(promocionI,vInsertar);
        double descuento = promocionI.calcularDescuento();
        notaVenta.setDescuentoPorcentual(descuento);
        String descuentoTotal = String.valueOf(notaVenta.getMonto_total() - notaVenta.getMonto_total()*(descuento/100));
        notaVenta.setDescuentoString(promocionI.getDescripcion() + " Monto Total: " + descuentoTotal);
        return notaVenta;
    }

    public void read() {
        MainActivity view = vMain;
        MNotaVenta mNotaVenta = new MNotaVenta(view);
        ArrayList<MNotaVenta> notaVentas = mNotaVenta.read();
        view.read(notaVentas);
    }


}
