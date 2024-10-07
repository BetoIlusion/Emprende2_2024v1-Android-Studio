package com.app.emprende2_2024.controller.CNotaVenta;

import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MDetalleFactura.DetalleFactura;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleFactura;
import com.app.emprende2_2024.model.MNotaVenta.NotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.model.MStock.MStock;
import com.app.emprende2_2024.model.MStock.Stock;
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
        try{
            MPersona modelPersona = new MPersona(view);
            MCategoria modelCategoria = new MCategoria(view);
            MProducto modelProducto = new MProducto(view);

            ArrayList<Persona> personas = modelPersona.read();
            ArrayList<Categoria> categorias = modelCategoria.read();

            view.llenarSpinners(personas,categorias,null);
        }catch (Exception e){
            view.mensaje("ERROR EN CONTROLLER");
            e.printStackTrace();
        }
    }

    public void filtrarProductos(Categoria categoria) {
        VNotaVentaInsertar view = vInsertar;
        MProducto modelProducto = new MProducto(view);
        ArrayList<Producto> productos = modelProducto.read();
        ArrayList<Producto> productoFiltro = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            if (categoria.getId() == productos.get(i).getId_categoria()){
                productoFiltro.add(productos.get(i));
            }
        }
        view.filtrarProductos(productoFiltro);
    }

    public void añadirDetalle(int cantidad,
                              Persona persona,
                              Categoria categoria,
                              Producto producto) {
        VNotaVentaInsertar view = vInsertar;
        try{

            DetalleFactura detalle = new DetalleFactura();
            float subtotal = (float) cantidad * producto.getPrecio();
            MStock modelStock = new MStock(view);
            Stock stock = modelStock.readUno(producto.getId());
            if(cantidad <= stock.getCantidad()){
                boolean existe = existeDetalle(producto, cantidad, subtotal);
                if (!existe){
                    detalle.setCantidad(cantidad);
                    detalle.setSubtotal(subtotal);
                    detalle.setProducto(producto);
                    view.getDetalles().add(detalle);
                }

                actualizarMontoTotal();
                view.actualizar();
            }else{
                view.mensaje("ERROR, cantidad por encima del stock(" + stock.getCantidad() + ")");
            }


        }catch (Exception e){
            view.mensaje("ERROR EN CONTROLLER");
            e.printStackTrace();
        }

    }

    private void actualizarMontoTotal() {
        VNotaVentaInsertar view = vInsertar;
        float montoTotal = 0.0f;
        for (int i = 0; i < view.getDetalles().size(); i++) {
            montoTotal += view.getDetalles().get(i).getSubtotal();
        }
        view.getFactura().setMontoTotal(montoTotal);
    }

    private boolean existeDetalle(Producto producto, int cantidad, float subtotal) {
        VNotaVentaInsertar view = vInsertar;
        for (int i = 0; i < view.getDetalles().size(); i++) {
            if(view.getDetalles().get(i).getProducto().getId() == producto.getId()){
                view.getDetalles().get(i).setCantidad(cantidad);
                view.getDetalles().get(i).setSubtotal(subtotal);
                return true;
            }
        }
        return false;
    }

    public void calcularCambio(int efectivo) {
        VNotaVentaInsertar view = vInsertar;
        float cambio = efectivo - view.getFactura().getMontoTotal();
        view.getFactura().setCambio(cambio);
        view.getFactura().setEfectivo(efectivo);
        view.actualizarFactura();

    }

    public void craete(Persona cliente, NotaVenta factura, ArrayList<DetalleFactura> detalles) {
        VNotaVentaInsertar view = vInsertar;
        boolean b = false;
        try{
            MNotaVenta modelNotaVenta = new MNotaVenta(view);
            MDetalleFactura modelDetalle = new MDetalleFactura(view);
            MStock modelStock = new MStock(view);
            long id = modelNotaVenta.create(
                    cliente.getId(),
                    factura.getEfectivo(),
                    factura.getCambio(),
                    factura.getMontoTotal()
            );
            if (id > 0){
                ArrayList<Stock> stocks = modelStock.read();
                long id2 = modelDetalle.create(detalles, id);
                modelStock.update(detalles,stocks);
                if (id2 > 0){
                    view.mensaje("FACTURA GURDADA");
                    view.vShow(id);
                }else
                    view.mensaje("ERROR EN DETALLE");
            }else{
                view.mensaje("ERROR AL CREAR/CFACTURA");
            }

        }catch (Exception e){
            view.mensaje("ERROR EN CONTROLLER FACTURA");
            e.printStackTrace();
        }
    }

    public void read() {
        MainActivity view = vMain;
        MNotaVenta modelFactura = new MNotaVenta(view);
        MPersona modelPersona = new MPersona(view);

        ArrayList<NotaVenta> facturas = modelFactura.read();
        ArrayList<Persona> personas = modelPersona.read();

        view.read(facturas,personas);
    }
}
