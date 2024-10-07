package com.app.emprende2_2024.controller.CDetalleNotaVenta;

import com.app.emprende2_2024.model.MDetalleFactura.DetalleFactura;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleFactura;
import com.app.emprende2_2024.model.MNotaVenta.NotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class CDetalleNotaVenta {
    VDetalleNotaVentaShow vVer;
    public CDetalleNotaVenta(VDetalleNotaVentaShow vDetalleFacturaVer) {
        this.vVer = vDetalleFacturaVer;
    }

    public void read(int id) {
        VDetalleNotaVentaShow view = vVer;
        try{

            MNotaVenta modelNotaVenta = new MNotaVenta(view);
            MDetalleFactura modelDetalle = new MDetalleFactura(view);
            MPersona modelPersona = new MPersona(view);
            MProducto modelProducto = new MProducto(view);

            NotaVenta notaVenta = modelNotaVenta.readUno(id);
            ArrayList<DetalleFactura> detalles = modelDetalle.read(id);
            ArrayList<Producto> productos = modelProducto.read();
            for (int i = 0; i < detalles.size(); i++) {
                for (int j = 0; j < productos.size(); j++) {
                    if (productos.get(j).getId() == detalles.get(i).getProducto().getId()){
                        detalles.get(i).setProducto(productos.get(i));
                    }
                }
            }
            Persona persona = modelPersona.readUno(notaVenta.getId_persona());

            view.llenarVista(notaVenta,detalles,persona);
        }catch (Exception e){
            view.mensaje("ERROR, INSTANCIA MODEL/CDETALLEFACTURA");
            e.printStackTrace();
        }
    }

    public void compartirPDF(int id) {

    }
}
