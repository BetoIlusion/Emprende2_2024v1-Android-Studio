package com.app.emprende2_2024.model.MFrecuencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.emprende2_2024.db.DbHelper;

public class MFrecuencia {
    Context context;
    private int id;
    private int frecuencia_dias;
    private int frecuencia_mes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrecuencia_dias() {
        return frecuencia_dias;
    }

    public void setFrecuencia_dias(int frecuencia_dias) {
        this.frecuencia_dias = frecuencia_dias;
    }

    public int getFrecuencia_mes() {
        return frecuencia_mes;
    }

    public void setFrecuencia_mes(int frecuencia_mes) {
        this.frecuencia_mes = frecuencia_mes;
    }


    @Override
    public String toString() {
        return "MFrecuencia{" +
                "id=" + id +
                '}';
    }

    public MFrecuencia(Context context) {
        this.context = context;
        this.id = -1;
        this.frecuencia_dias = 0;
        this.frecuencia_mes = 0;
    }

    public MFrecuencia get() {
        MFrecuencia frecuencia = new MFrecuencia(context);
        DbHelper dbHelper = new DbHelper(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorFrecuencia = null;
            cursorFrecuencia = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_FRECUENCIA
                    + " WHERE id = " + 1 + " LIMIT 1", null);
            if(cursorFrecuencia.moveToFirst()){
                frecuencia.setId(cursorFrecuencia.getInt(0));
                frecuencia.setFrecuencia_dias(cursorFrecuencia.getInt(1));
                frecuencia.setFrecuencia_mes(cursorFrecuencia.getInt(2));
            }
            cursorFrecuencia.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return frecuencia;
    }

    public boolean update(MFrecuencia frecuenciaPersona) {
        String dias = frecuenciaPersona.getFrecuencia_dias() + "";
        String mes = frecuenciaPersona.getFrecuencia_mes() + "";
        return update(
                dias,
                mes
        );
    }
    public boolean update(String dias, String mes) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_FRECUENCIA + " SET frecuencias_dias = ?, frecuencia_mes = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{dias,mes,1});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
