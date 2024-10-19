package com.app.emprende2_2024.model.MProveedor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class MProveedor extends DbHelper {
    Context context;
    public MProveedor(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ArrayList<Proveedor> read() {
        ArrayList<Proveedor> listaProveedor = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Proveedor proveedor;
            Cursor cursorProveedor;

            cursorProveedor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR, null);
            if (cursorProveedor.moveToFirst()) {
                do {

                    proveedor = new Proveedor();
                    proveedor.setId(cursorProveedor.getInt(0));
                    proveedor.setNit(cursorProveedor.getString(1));
                    proveedor.setId_persona(cursorProveedor.getInt(2));
                    listaProveedor.add(proveedor);
                } while (cursorProveedor.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron registros de personas.", Toast.LENGTH_SHORT).show();
            }
            cursorProveedor.close();
            return listaProveedor;
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL MOSTRAR PROVEEDOR", Toast.LENGTH_SHORT).show();
        }
        return listaProveedor;
    }

    public long create(String nit, int id_persona) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nit", nit);
            values.put("id_persona", id_persona);
            id = db.insert(TABLE_PROVEEDOR, null, values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR REGISTRO PROVEEDOR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public Proveedor readUno(int id_persona) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Proveedor proveedor = null;
        Cursor cursorProveedor = null;
        cursorProveedor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR
                + " WHERE id_persona = " + id_persona + " LIMIT 1", null);
        if (cursorProveedor.moveToFirst()) {
            proveedor = new Proveedor();
            proveedor.setId(cursorProveedor.getInt(0));
            proveedor.setNit(cursorProveedor.getString(1));
            proveedor.setId_persona(cursorProveedor.getInt(2));

        }
        cursorProveedor.close();
        return proveedor;

    }

    public boolean delete(int id_persona) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_PROVEEDOR + " WHERE id_persona = '" + id_persona  +"'");
            correcto = true; // Se establece a true si la operación de eliminación fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return correcto;
    }

    public boolean update(String nit, int id_persona) {
        boolean b = false;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PROVEEDOR + " SET nit = ? WHERE id_persona = ?";
            db.execSQL(sql, new Object[]{nit, id_persona});
            b = true; // Se establece a true si la operación de actualización fue exitosa
            db.close();
        }catch (Exception e){
            b = false;
            e.printStackTrace();
        }
        return b;
    }
}
