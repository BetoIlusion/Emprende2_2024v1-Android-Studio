package com.app.emprende2_2024.model.MPersona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class MPersona extends DbHelper {
    Context context;
    public MPersona(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public ArrayList<Persona> read() {
        ArrayList<Persona> listaPersona = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            Persona persona;
            Cursor cursorPersona;

            cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                    + " ORDER BY nombre ASC", null);
            if (cursorPersona.moveToFirst()) {
                do {
                    persona = new Persona();
                    persona.setId(cursorPersona.getInt(0));
                    persona.setNombre(cursorPersona.getString(1));
                    persona.setTelefono(cursorPersona.getString(2));
                    persona.setDireccion(cursorPersona.getString(3));
                    persona.setCorreo(cursorPersona.getString(4));
                    persona.setTipo_cliente(cursorPersona.getString(5));
                    persona.setEstado(cursorPersona.getString(6));
                    persona.setUbicacion(cursorPersona.getString(7));
                    listaPersona.add(persona);
                } while (cursorPersona.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron registros de personas.", Toast.LENGTH_SHORT).show();
            }
            cursorPersona.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR EL DbHelper", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaPersona;
    }
    public Persona readUno(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Persona persona = null;
        Cursor cursorPersona = null;
        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorPersona.moveToFirst()) {
            persona = new Persona();
            persona.setId(cursorPersona.getInt(0));
            persona.setNombre(cursorPersona.getString(1));
            persona.setTelefono(cursorPersona.getString(2));
            persona.setDireccion(cursorPersona.getString(3));
            persona.setCorreo(cursorPersona.getString(4));
            persona.setTipo_cliente(cursorPersona.getString(5));
            persona.setEstado(cursorPersona.getString(6));
            persona.setUbicacion(cursorPersona.getString(7));
        }
        cursorPersona.close();
        return persona;
    }
    public Persona readUnoXproveedor(int idProveedor) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Persona persona = null;
        Cursor cursorPersona = null;
        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                + " WHERE id_proveedor = " + idProveedor + " LIMIT 1", null);
        if (cursorPersona.moveToFirst()) {
            persona = new Persona();
            persona.setId(cursorPersona.getInt(0));
            persona.setNombre(cursorPersona.getString(1));
            persona.setTelefono(cursorPersona.getString(2));
            persona.setDireccion(cursorPersona.getString(3));
            persona.setCorreo(cursorPersona.getString(4));
            persona.setTipo_cliente(cursorPersona.getString(5));
            persona.setEstado(cursorPersona.getString(6));
        }
        cursorPersona.close();
        return persona;
    }
    public long create(String nombre, String telefono, String direccion, String correo, String tipoCliente, String estado, String ubicacion) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("direccion", direccion);
            values.put("correo", correo);
            values.put("tipo_cliente", tipoCliente);
            values.put("estado", estado);
            values.put("link_ubicacion", ubicacion);
            id = db.insert(TABLE_PERSONA, null, values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public boolean update(int id,
                          String nombre,
                          String telefono,
                          String direccion,
                          String correo,
                          String estado, String ubicacion) {
        boolean correcto = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PERSONA + " SET nombre = ?, telefono = ?, direccion = ?, correo = ?, estado = ?, link_ubicacion = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, telefono, direccion, correo, estado, ubicacion, id});
            correcto = true; // Se establece a true si la operación de actualización fue exitosa
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }
        return correcto;
    }


    public boolean delete(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_PERSONA + " WHERE id = '" + id  +"'");
            correcto = true; // Se establece a true si la operación de eliminación fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return correcto;
    }

    public ArrayList<Persona> mostrarFiltro(String filtroSeleccionado) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Persona> listaPersona = new ArrayList<>();
        Persona persona;
        Cursor cursorPersona;

        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                + " WHERE tipo_cliente = '" + filtroSeleccionado +"'"
                + " ORDER BY nombre ASC", null);
        if (cursorPersona.moveToFirst()) {
            do {
                persona = new Persona();
                persona.setId(cursorPersona.getInt(0));
                persona.setNombre(cursorPersona.getString(1));
                persona.setTelefono(cursorPersona.getString(2));
                persona.setDireccion(cursorPersona.getString(3));
                persona.setCorreo(cursorPersona.getString(4));
                persona.setTipo_cliente(cursorPersona.getString(5));
                persona.setEstado(cursorPersona.getString(6));
                listaPersona.add(persona);
            } while (cursorPersona.moveToNext());
        }else {
            Toast.makeText(context, "No se encontraron registros de personas.", Toast.LENGTH_SHORT).show();
        }
        cursorPersona.close();
        return listaPersona;
    }


}
