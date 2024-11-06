package com.app.emprende2_2024.model.MCategoria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.PatronComposite.ReporteI;
import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class MCategoria implements ReporteI {
    private int id;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private ArrayList<ReporteI> componentes = new ArrayList<>();

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    Context context;
DbHelper dbHelper;

    public MCategoria(@Nullable Context context, int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }
    public MCategoria(@Nullable Context context) {
        this.context = context;
        this.id = -1;
        this.nombre = "";
        this.descripcion = "";
        this.dbHelper = new DbHelper(context);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre;
    }
    public boolean create(){
       boolean b = false;
       long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", getNombre());
            values.put("descripcion", getDescripcion());
            values.put("estado", 1);
            id = db.insert(dbHelper.TABLE_CATEGORIA, null, values);
            b = true;
            setId((int)id);
        }catch (Exception ex){
            Log.e("TAG", "Error al insertar Categoria: " + ex.getMessage(), ex);
            ex.toString();
        }
        return b;
    }

    public ArrayList<MCategoria> read() {
        ArrayList<MCategoria> arrayCategoria = new ArrayList<>();
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor;
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_CATEGORIA + " ORDER BY id DESC", null);
            if (cursor.moveToFirst()){
                do{
                    if(cursor.getInt(3) == 1){ //si el estado es true
                        MCategoria categoria = new MCategoria(context);
                        categoria.setId(cursor.getInt(0));
                        categoria.setNombre(cursor.getString(1));
                        categoria.setDescripcion(cursor.getString(2));
                        categoria.setEstado(cursor.getInt(3));
                        arrayCategoria.add(categoria);
                    }
                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception e){
            arrayCategoria = null;
        }

        return arrayCategoria;
    }

    public MCategoria findById(int id) {
        MCategoria model = new MCategoria(context);
        try{

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorCategoria = null;
            cursorCategoria = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_CATEGORIA
                    + " WHERE id = " + id + " LIMIT 1", null);
            if(cursorCategoria.moveToFirst()){
                model.setId(cursorCategoria.getInt(0));
                model.setNombre(cursorCategoria.getString(1));
                model.setDescripcion(cursorCategoria.getString(2));
            }
            cursorCategoria.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }
    public boolean update(int id, String nombre, String descripcion) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_CATEGORIA + " SET nombre = ?, descripcion = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{nombre,descripcion,id});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public boolean delete(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_CATEGORIA + " SET estado = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{0,id});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public String getNombreReporte() {
        return getNombre();
    }

    @Override
    public int getStockReporte() {
        int stock = 0;
        for (ReporteI componente: componentes){
            stock += componente.getStockReporte();
        }
        return stock;
    }

    @Override
    public double getTotalReporte() {
        double total = 0;
        for (ReporteI componente: componentes){
            total += componente.getTotalReporte();
        }
        return total;
    }

    @Override
    public String generarReporte() {
        String s = "Categoria: " + getNombre() + ", totales: $" + getTotalReporte() +
                ", Stock total: " + getStockReporte() + "\n";
        for (ReporteI componente : componentes) {
           s += componente.generarReporte();
        }
        return s;
    }
    public void addComponente(ReporteI componente) {
        componentes.add(componente);
    }
    public void removeComponente(ReporteI componente) {
        componentes.remove(componente);
    }
}
