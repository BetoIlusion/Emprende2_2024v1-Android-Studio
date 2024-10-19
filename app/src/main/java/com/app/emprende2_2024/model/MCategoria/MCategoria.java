package com.app.emprende2_2024.model.MCategoria;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class MCategoria extends DbHelper {

    Context context;

    public MCategoria(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public boolean update(int id, String nombre, String descripcion) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + TABLE_CATEGORIA + " SET nombre = ?, descripcion = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{nombre,descripcion,id});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public long create(String nombre, String descripcion){
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("descripcion", descripcion);
            id = db.insert(TABLE_CATEGORIA, null, values);
        }catch (Exception ex){
            Log.e("TAG", "Error al insertar Categoria: " + ex.getMessage(), ex);
            ex.toString();
        }
        return id;
    }
    public ArrayList<Categoria> read() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Categoria> arrayCategoria = new ArrayList<>();
        Categoria categorias;
        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIA + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()){
            do{
                categorias = new Categoria();
                categorias.setId(cursor.getInt(0));
                categorias.setNombre(cursor.getString(1));
                categorias.setDescripcion(cursor.getString(2));
                arrayCategoria.add(categorias);
            }while (cursor.moveToNext());
            cursor.close();
        }else {
            Toast.makeText(context, "No se encontraron las Categorias, inserte una.",
                    Toast.LENGTH_SHORT).show();
        }
        return arrayCategoria;
    }

    public Categoria readUno(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Categoria categoria = null;
        Cursor cursorCategoria = null;
        cursorCategoria = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIA
                + " WHERE id = " + id + " LIMIT 1", null);
        if(cursorCategoria.moveToFirst()){
            categoria = new Categoria();
            categoria.setId(cursorCategoria.getInt(0));
            categoria.setNombre(cursorCategoria.getString(1));
            categoria.setDescripcion(cursorCategoria.getString(2));
        }
        cursorCategoria.close();

        return categoria;
    }

    public boolean destroy(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_CATEGORIA + " WHERE id = '" + id  +"'");
            correcto = true; // Se establece a true si la operación de eliminación fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return correcto;
    }



//    public boolean delete(int id) {
//        boolean b = false;
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        try{
//            db.execSQL("DELETE FROM " + TABLE_CATEGORIA + " WHERE id = '" + id + "'");
//            b = true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return b;
//    }
}