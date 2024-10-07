package com.app.emprende2_2024.model.MDetalleFactura;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MProducto.Producto;

import java.util.ArrayList;

public class MDetalleFactura extends DbHelper {
    Context context;
    public MDetalleFactura(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public long create(ArrayList<DetalleFactura> detalles, long id_factura) {
        long id2 = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            int id_factura1 = (int) id_factura;
            for (int i = 0; i < detalles.size(); i++) {
                try{
                    int id_producto = detalles.get(i).getProducto().getId();
                    int cantidad = detalles.get(i).getCantidad();
                    float subtotal = detalles.get(i).getSubtotal();
                    values.put("id_factura", id_factura1);
                    values.put("id_producto", id_producto);
                    values.put("cantidad", cantidad);
                    values.put("subtotal", subtotal);
                    id2 = db.insert(TABLE_DETALLE_NOTA_VENTA,null,values);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return id2;
    }


    public ArrayList<DetalleFactura> read(int id) {
        ArrayList<DetalleFactura> detalles = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            DetalleFactura detalle;
            Cursor cursorDetalle;
            // Suponiendo que tienes una variable con el valor de id
            int idVariable = 123; // Reemplaza 123 con el valor de la variable que quieras usar
// Consulta SQL con WHERE para filtrar por el valor de la variable 'id'
            cursorDetalle = db.rawQuery("SELECT * FROM " + TABLE_DETALLE_NOTA_VENTA
                    + " WHERE id_factura = ? ORDER BY id DESC", new String[]{String.valueOf(id)});
            if (cursorDetalle.moveToFirst()){
                do{
                    detalle = new DetalleFactura();
                    detalle.setId(cursorDetalle.getInt(0));
                    detalle.setId_factura(cursorDetalle.getInt(1));
                    int idProducto = cursorDetalle.getInt(2);
                    Producto prod = new Producto();
                    prod.setId(idProducto);
                    detalle.setProducto(prod);
                    detalle.setCantidad(cursorDetalle.getInt(3));
                    detalle.setSubtotal(cursorDetalle.getFloat(4));
                    detalles.add(detalle);
                }while (cursorDetalle.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron registros de detalles.", Toast.LENGTH_SHORT).show();

            }
            cursorDetalle.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return detalles;
    }
}
