package com.app.emprende2_2024.controller.CDetalleNotaVenta;

import com.app.emprende2_2024.model.MDetalleFactura.modelDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.modelNotaVenta;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class CDetalleNotaVenta {
    VDetalleNotaVentaShow vVer;
    public CDetalleNotaVenta(VDetalleNotaVentaShow vDetalleFacturaVer) {
        this.vVer = vDetalleFacturaVer;
    }

    public void read(int id) {
        VDetalleNotaVentaShow view = vVer;
        modelDetalleNotaVenta detalles = new modelDetalleNotaVenta(view);
        modelNotaVenta notaVenta = new modelNotaVenta(view);
        ArrayList<modelDetalleNotaVenta> listaDetalles = detalles.finByIdFull(id);
        notaVenta = notaVenta.findById(id);
        view.llenarVista(notaVenta,listaDetalles);
    }

    public void compartirPDF(int id) {

    }
}
