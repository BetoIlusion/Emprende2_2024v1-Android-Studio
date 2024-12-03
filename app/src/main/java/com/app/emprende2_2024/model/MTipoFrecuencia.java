package com.app.emprende2_2024.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MCategoria.MCategoria;

import java.util.ArrayList;

public class MTipoFrecuencia {
    private int id;
    private String nombre;
    DbHelper dbHelper;
    MTipoFrecuencia mTipoFrecuencia;
    Context context;

    public MTipoFrecuencia(Context context){
        this.id = 0;
        this.nombre = "";
        this.dbHelper = new DbHelper(context);
        this.context = context;
    }

    public MTipoFrecuencia(Context context,int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.dbHelper = new DbHelper(context);
        this.context = context;
    }
    public void set(MTipoFrecuencia tipoFrecuencia){
        this.id = tipoFrecuencia.getId();
        this.nombre = tipoFrecuencia.getNombre();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "MTipoFrecuencia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
    //CRUD
    public long create(){
        long id = -1;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", getNombre());
            id = db.insert(dbHelper.TABLE_TIPO_FRECUENCIA, null, values);
        }catch (Exception ex){
            Log.e("TAG", "Error al insertar Categoria: " + ex.getMessage(), ex);
            ex.toString();
        }
        return id;
    }
    public ArrayList<MTipoFrecuencia> read(){
        ArrayList<MTipoFrecuencia> arrayTF = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor;
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_TIPO_FRECUENCIA + " ORDER BY id DESC", null);
            if (cursor.moveToFirst()){
                do{
                    if(cursor.getInt(3) == 1){ //si el estado es true
                        MTipoFrecuencia tipoFrecuencia = new MTipoFrecuencia(context);
                        tipoFrecuencia.setId(cursor.getInt(0));
                        tipoFrecuencia.setNombre(cursor.getString(1));
                        arrayTF.add(tipoFrecuencia);
                    }
                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception e){
            arrayTF = null;
        }
        return arrayTF;
    }
    public MTipoFrecuencia findById(int id){
        MTipoFrecuencia model = new MTipoFrecuencia(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorCategoria = null;
            cursorCategoria = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_TIPO_FRECUENCIA
                    + " WHERE id = " + id + " LIMIT 1", null);
            if(cursorCategoria.moveToFirst()){
                model.setId(cursorCategoria.getInt(0));
                model.setNombre(cursorCategoria.getString(1));
            }
            cursorCategoria.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

    public boolean update() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try {
            String sql = "UPDATE " + dbHelper.TABLE_TIPO_FRECUENCIA + " SET nombre = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, id});
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    public boolean delete() {
        return false;
    }

}
