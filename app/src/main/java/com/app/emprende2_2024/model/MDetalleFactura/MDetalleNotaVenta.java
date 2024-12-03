package com.app.emprende2_2024.model.MDetalleFactura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.PatronAdapter.CaptureInterface;
import com.app.emprende2_2024.PatronAdapter.PNGCaptureAdapter;
import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MProducto.MProducto;

import java.util.ArrayList;

public class MDetalleNotaVenta extends DbHelper {
    private  int id;
    private int cantidad;
    private double subtotal;
    private MNotaVenta notaVenta;
    private MProducto producto;
    private Context context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MNotaVenta getNotaVenta() {
        return notaVenta;
    }

    public void setNotaVenta(MNotaVenta notaVenta) {
        this.notaVenta = notaVenta;
    }

    public MProducto getProducto() {
        return producto;
    }

    public void setProducto(MProducto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return producto.getNombre();
    }

    public MDetalleNotaVenta(@Nullable Context context) {
        super(context);
        this.id = -1;
        this.notaVenta = new MNotaVenta(context);
        this.producto = new MProducto(context);
        this.cantidad = 0;
        this.subtotal = 0f;
        this.context = context;
    }

    public long create(int cantidad, double subtotal, long id_nota_venta, MProducto producto) {
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("cantidad", cantidad);
            values.put("subtotal", subtotal);
            values.put("id_nota_venta", id_nota_venta);
            values.put("id_producto", producto.getId());
            producto.getStock().actualizarCantidad(producto.getStock().getCantidad()-cantidad);
            return db.insert(TABLE_DETALLE_NOTA_VENTA,null,values);
        }catch (Exception e){
            return 0;
        }
    }

    public ArrayList<MDetalleNotaVenta> finByIdFull(int id) {
        ArrayList<MDetalleNotaVenta> detalles = new ArrayList<>();
        MDetalleNotaVenta detalle = new MDetalleNotaVenta(context);
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorDetalle;
            cursorDetalle = db.rawQuery("SELECT * FROM " + TABLE_DETALLE_NOTA_VENTA
                    + " WHERE id_nota_venta = ? ORDER BY id DESC",
                    new String[]{String.valueOf(id)});
            if (cursorDetalle.moveToFirst()){
                do {
                    detalle = new MDetalleNotaVenta(context);
                    detalle.setId(cursorDetalle.getInt(0));
                    detalle.setCantidad(cursorDetalle.getInt(1));
                    detalle.setSubtotal(cursorDetalle.getDouble(2));
                    MNotaVenta notaVenta = new MNotaVenta(context);
                    notaVenta = notaVenta.findById(cursorDetalle.getInt(3));
                    detalle.setNotaVenta(notaVenta);
                    MProducto producto = new MProducto(context);
                    producto = producto.findById(cursorDetalle.getInt(4));
                    detalle.setProducto(producto);
                    detalles.add(detalle);
                }while (cursorDetalle.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return detalles;
    }

    public void compartirPNG(View viewById) {
        CaptureInterface captureInterface = new PNGCaptureAdapter(
                context,
                viewById
        );
        captureInterface.compartirWhatsapp();
    }
}
