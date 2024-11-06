package com.app.emprende2_2024.controller.CDetalleNotaVenta;

import com.app.emprende2_2024.model.MDetalleFactura.MDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class CDetalleNotaVenta {
    VDetalleNotaVentaShow vVer;
    public CDetalleNotaVenta(VDetalleNotaVentaShow vDetalleFacturaVer) {
        this.vVer = vDetalleFacturaVer;
    }

    public void read(int id) {
        VDetalleNotaVentaShow view = vVer;
        MDetalleNotaVenta detalles = new MDetalleNotaVenta(view);
        MNotaVenta notaVenta = new MNotaVenta(view);
        ArrayList<MDetalleNotaVenta> listaDetalles = detalles.finByIdFull(id);
        notaVenta = notaVenta.findById(id);
        view.llenarVista(notaVenta,listaDetalles);
    }

}
