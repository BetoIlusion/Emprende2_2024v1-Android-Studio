package com.app.emprende2_2024.model.MProveedor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MPersona.modelPersona;

import java.util.ArrayList;

public class modelProveedor extends DbHelper {
    private int id;
    private String nit;
    private modelPersona persona;
    private Context context;

    public modelProveedor(@Nullable Context context) {
        super(context);
        this.context = context;
        this.id = -1;
        this.nit = "";
        this.persona = new modelPersona(context);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public modelPersona getPersona() {
        return persona;
    }

    public void setPersona(modelPersona persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return getNit() + " : " + getPersona().getNombre();
    }

    public ArrayList<modelProveedor> read() {
        ArrayList<modelProveedor> listaProveedor = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            modelProveedor proveedor;
            Cursor cursorProveedor;

            cursorProveedor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR, null);
            if (cursorProveedor.moveToFirst()) {
                do {
                    modelPersona persona1 = new modelPersona(context);
                    persona1 = persona1.findById(cursorProveedor.getInt(2));
                    if(persona1.getEstado() == 1){
                        proveedor = new modelProveedor(context);
                        proveedor.setId(cursorProveedor.getInt(0));
                        proveedor.setNit(cursorProveedor.getString(1));
                        proveedor.setPersona(persona1);
                        listaProveedor.add(proveedor);
                    }
                } while (cursorProveedor.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron registros de personas/prov.", Toast.LENGTH_SHORT).show();
            }
            cursorProveedor.close();
            return listaProveedor;
        }catch (Exception e){
            listaProveedor = null;
        }
        return listaProveedor;
    }

    public modelProveedor findByIdPersona(int id_persona) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        modelProveedor proveedor = null;
        Cursor cursorProveedor = null;
        cursorProveedor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR
                + " WHERE id_persona = " + id_persona + " LIMIT 1", null);
        if (cursorProveedor.moveToFirst()) {
            proveedor = new modelProveedor(context);
            proveedor.setId(cursorProveedor.getInt(0));
            proveedor.setNit(cursorProveedor.getString(1));
            modelPersona mPersona = new modelPersona(context);
            proveedor.setPersona(mPersona.findById(cursorProveedor.getInt(2)));
        }
        cursorProveedor.close();
        return proveedor;
    }
    public modelProveedor findById(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        modelProveedor proveedor = null;
        Cursor cursorProveedor = null;
        cursorProveedor = db.rawQuery("SELECT * FROM " + TABLE_PROVEEDOR
                + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorProveedor.moveToFirst()) {
            proveedor = new modelProveedor(context);
            proveedor.setId(cursorProveedor.getInt(0));
            proveedor.setNit(cursorProveedor.getString(1));
            modelPersona mPersona = new modelPersona(context);
            proveedor.setPersona(mPersona.findById(cursorProveedor.getInt(2)));
        }
        cursorProveedor.close();
        return proveedor;
    }

    public boolean create(String nit, int id_persona) {
        boolean b = false;
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nit", nit);
            values.put("id_persona", id_persona);
            id = db.insert(TABLE_PROVEEDOR, null, values);
            setId((int) id);
            setNit(nit);
            modelPersona mPersona = new modelPersona(context);
            mPersona = mPersona.findById(id_persona);
            setPersona(mPersona);
            b = true;
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR REGISTRO PROVEEDOR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return b;
    }

    public boolean update(String nit,int id_persona) {
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
