package com.app.emprende2_2024.model.MNotaVenta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MDetalleFactura.modelDetalleNotaVenta;
import com.app.emprende2_2024.model.MPersona.modelPersona;

import java.util.ArrayList;

public class modelNotaVenta extends DbHelper {

    private int id;
    private int id_codigo;
    private float monto_total;
    private float efectivo;
    private float cambio;
    private modelPersona persona;
    private Context context;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_codigo() {
        return id_codigo;
    }

    public void setId_codigo(int id_codigo) {
        this.id_codigo = id_codigo;
    }

    public float getMonto_total() {
        return monto_total;
    }

    public void setMonto_total(float monto_total) {
        this.monto_total = monto_total;
    }

    public float getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }

    public float getCambio() {
        return cambio;
    }

    public void setCambio(float cambio) {
        this.cambio = cambio;
    }

    public modelPersona getPersona() {
        return persona;
    }

    public void setPersona(modelPersona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return "modelNotaVenta{" +
                "persona=" + persona +
                '}';
    }

    public modelNotaVenta(@Nullable Context context) {
        super(context);
        this.id = -1;
        this.id_codigo = -1;
        this.monto_total = 0f;
        this.efectivo = 0f;
        this.cambio = 0f;
        this.persona = new modelPersona(context);
        this.context = context;
    }

    public long create(float montoTotal, float efectivo, float cambio, modelPersona cliente, ArrayList<modelDetalleNotaVenta> detalles) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("monto_total", montoTotal);
            values.put("efectivo", efectivo);
            values.put("cambio", cambio);
            values.put("id_persona", cliente.getId());
            id = db.insert(TABLE_NOTA_VENTA, null, values);
            for (int i = 0; i < detalles.size(); i++) {
                modelDetalleNotaVenta detalle = new modelDetalleNotaVenta(context);
                if(detalle.create(
                        detalles.get(i).getCantidad(),
                        detalles.get(i).getSubtotal(),
                        id,
                        detalles.get(i).getProducto()
                ) >0){
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public modelNotaVenta findById(int id) {
        modelNotaVenta notaVenta = null;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursoNotaVenta = null;
            cursoNotaVenta = db.rawQuery("SELECT * FROM " + TABLE_NOTA_VENTA
                    + " WHERE id = " + id + " LIMIT 1", null);
            if (cursoNotaVenta.moveToFirst()) {
                notaVenta = new modelNotaVenta(context);
                notaVenta.setId(cursoNotaVenta.getInt(0));
                notaVenta.setMonto_total(cursoNotaVenta.getInt(2));
                notaVenta.setEfectivo(cursoNotaVenta.getFloat(3));
                notaVenta.setCambio(cursoNotaVenta.getFloat(4));
                modelPersona persona1 = new modelPersona(context);
                persona1 = persona1.findById(cursoNotaVenta.getInt(6));
                notaVenta.setPersona(persona1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return notaVenta;
    }

    public ArrayList<modelNotaVenta> read() {
        ArrayList<modelNotaVenta> notaVentas = new ArrayList<>();

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            modelNotaVenta notaVenta;
            Cursor cursorNotaVenta;
            cursorNotaVenta = db.rawQuery("SELECT * FROM " + TABLE_NOTA_VENTA
                    + " ORDER BY id DESC", null);
            if (cursorNotaVenta.moveToFirst()) {
                do {
                    notaVenta = new modelNotaVenta(context);
                    notaVenta.setId(cursorNotaVenta.getInt(0));
                    notaVenta.setId_codigo(cursorNotaVenta.getInt(1));
                    notaVenta.setMonto_total(cursorNotaVenta.getInt(2));
                    notaVenta.setEfectivo(cursorNotaVenta.getFloat(3));
                    notaVenta.setCambio(cursorNotaVenta.getFloat(4));
                    modelPersona persona1 = new modelPersona(context);
                    persona1 = persona1.findById(cursorNotaVenta.getInt(6));
                    notaVenta.setPersona(persona1);
                    notaVentas.add(notaVenta);
                } while (cursorNotaVenta.moveToNext());
            }
            cursorNotaVenta.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return notaVentas;
    }
}
