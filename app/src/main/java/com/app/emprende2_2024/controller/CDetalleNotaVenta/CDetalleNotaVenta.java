package com.app.emprende2_2024.controller.CDetalleNotaVenta;

import android.view.View;

import com.app.emprende2_2024.PatronAdapter.CaptureInterface;
import com.app.emprende2_2024.PatronAdapter.PNGCaptureAdapter;
import com.app.emprende2_2024.model.MDescuento.MDescuento;
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
        MDescuento descuento = new MDescuento(view);
        descuento = descuento.findBy(notaVenta.getId());
        view.llenarVista(notaVenta,listaDetalles, descuento);
    }

    public void compartirPNG(View viewById) {
        MDetalleNotaVenta detalles = new MDetalleNotaVenta(vVer);
        detalles.compartirPNG(viewById);


    }
}
