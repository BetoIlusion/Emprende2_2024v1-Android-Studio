package com.app.emprende2_2024.model.MNotaVenta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class MNotaVenta extends DbHelper {
    Context context;
    public MNotaVenta(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public int obtenerCodigo(int id) {
        int Codigo = -1;
       try {
           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();
           Cursor cursorFactura;
           cursorFactura = db.rawQuery("SELECT * FROM " + TABLE_FACTURA
                   + " WHERE id_persona = " + id + " LIMIT 1", null);

       }catch (Exception e){
           Toast.makeText(context, "ERROR MODEL FACTURA", Toast.LENGTH_SHORT).show();
       }
        return Codigo;
    }


    public long create( int id_persona, float efectivo, float cambio, float montoTotal) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_codigo", -1);
            values.put("id_persona", id_persona);
            values.put("efectivo", efectivo);
            values.put("cambio", cambio);
            values.put("monto_total", montoTotal);
            id = db.insert(TABLE_FACTURA, null, values);
            String sql = "UPDATE " + TABLE_FACTURA + " SET id_codigo = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{id, id});
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public NotaVenta readUno(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        NotaVenta factura = null;
        Cursor cursorFactura = null;
        cursorFactura = db.rawQuery("SELECT * FROM " + TABLE_FACTURA
                + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorFactura.moveToFirst()) {
            factura = new NotaVenta();
            factura.setId(cursorFactura.getInt(0));
            factura.setId_codigo(cursorFactura.getInt(1));
            factura.setId_persona(cursorFactura.getInt(2));
            factura.setMontoTotal(cursorFactura.getInt(3));
            factura.setEfectivo(cursorFactura.getFloat(4));
            factura.setCambio(cursorFactura.getFloat(5));
        }
        cursorFactura.close();
        return factura;
    }

    public ArrayList<NotaVenta> read() {
        ArrayList<NotaVenta> facturas = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            NotaVenta factura;
            Cursor cursorFactura;
            cursorFactura = db.rawQuery("SELECT * FROM " + TABLE_FACTURA
                    + " ORDER BY id DESC", null);
            if (cursorFactura.moveToFirst()){
                do{
                    factura = new NotaVenta();
                    factura.setId(cursorFactura.getInt(0));
                    factura.setId_codigo(cursorFactura.getInt(1));
                    factura.setId_persona(cursorFactura.getInt(2));
                    factura.setMontoTotal(cursorFactura.getInt(3));
                    factura.setEfectivo(cursorFactura.getFloat(4));
                    factura.setCambio(cursorFactura.getFloat(5));

                    facturas.add(factura);
                }while (cursorFactura.moveToNext());
            }else{
                Toast.makeText(context, "No se encontraron facturas.", Toast.LENGTH_SHORT).show();
            }
            cursorFactura.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR DBHELPER/MFACTURA", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return facturas;
    }
}
