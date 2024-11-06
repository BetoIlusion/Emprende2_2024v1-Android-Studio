package com.app.emprende2_2024.model.MDescuento;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.emprende2_2024.db.DbHelper;

public class MDescuento {
    Context context;
    DbHelper dbHelper;
    private int id;
    private double descuento_persona;
    private double descuento_producto;
    private double descuento_festejo;
    private String fechaFestivo;
    public MDescuento(Context context) {
        this.context = context;
        this.id = -1;
        this.descuento_persona = 0;
        this.descuento_producto = 0;
        this.descuento_festejo = 0;
        this.dbHelper = new DbHelper(context);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDescuento_persona() {
        return descuento_persona;
    }

    public void setDescuento_persona(double descuento_persona) {
        this.descuento_persona = descuento_persona;
    }

    public double getDescuento_producto() {
        return descuento_producto;
    }

    public void setDescuento_producto(double descuento_producto) {
        this.descuento_producto = descuento_producto;
    }

    public double getDescuento_festejo() {
        return descuento_festejo;
    }

    public void setDescuento_festejo(double descuento_festejo) {
        this.descuento_festejo = descuento_festejo;
    }

    public String getFechaFestivo() {
        return fechaFestivo;
    }

    public void setFechaFestivo(String fechaFestivo) {
        this.fechaFestivo = fechaFestivo;
    }

    public MDescuento get(){
        MDescuento model = new MDescuento(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorDescuento = null;
            cursorDescuento = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_DESCUENTO
                    + " WHERE id = " + 1 + " LIMIT 1", null);
            if(cursorDescuento.moveToFirst()){
                model.setId(cursorDescuento.getInt(0));
                model.setDescuento_persona(cursorDescuento.getDouble(1));
                model.setDescuento_producto(cursorDescuento.getDouble(2));
                model.setDescuento_festejo(cursorDescuento.getDouble(3));
                model.setFechaFestivo(cursorDescuento.getString(4));
            }
            cursorDescuento.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

    public boolean update(String descuentoPersona, String descuentoFestejo, String fechaFestejo) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_DESCUENTO + " SET descuento_persona = ?, descuento_festejo = ?, fecha_festivo = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{descuentoPersona,descuentoFestejo,fechaFestejo,1});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
