package com.app.emprende2_2024.model.MPersona;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

import java.util.ArrayList;

public class modelPersona extends DbHelper {
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String correo;
    private String tipo_cliente;
    private String ubicacion;
    private int estado;
    private Context context;

    public modelPersona(@Nullable Context context) {
        super(context);
        this.id = -1;
        this.nombre = "";
        this.telefono = "";
        this.direccion = "";
        this.correo = "";
        this.tipo_cliente = "";
        this.ubicacion = "";
        this.estado = -1;
        this.context = context;
    }

    public modelPersona(@Nullable Context context, int id, String nombre, String telefono, String direccion, String correo, String tipo_cliente, String ubicacion, int estado) {
        super(context);
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.tipo_cliente = tipo_cliente;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.context = context;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean create(String nombre, String telefono, String direccion, String correo,
                       String tipo_cliente, String ubicacion, int estado) {
        boolean b = false;
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("direccion", direccion);
            values.put("correo", correo);
            values.put("tipo_cliente", tipo_cliente);
            values.put("link_ubicacion", ubicacion);
            values.put("estado", estado);
            id = db.insert(TABLE_PERSONA, null, values);

            setId((int)id);
            setNombre(nombre);
            setTelefono(telefono);
            setDireccion(direccion);
            setCorreo(correo);
            setTipo_cliente(tipo_cliente);
            setUbicacion(ubicacion);
            setEstado(estado);
            b = true;
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return b;
    }

    public ArrayList<modelPersona> read() {
        ArrayList<modelPersona> listaPersona = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            modelPersona persona;
            Cursor cursorPersona;

            cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                    + " ORDER BY nombre ASC", null);
            if (cursorPersona.moveToFirst()) {
                do {
                    if(cursorPersona.getInt(7)==1){
                        persona = new modelPersona(context);
                        persona.setId(cursorPersona.getInt(0));
                        persona.setNombre(cursorPersona.getString(1));
                        persona.setTelefono(cursorPersona.getString(2));
                        persona.setDireccion(cursorPersona.getString(3));
                        persona.setCorreo(cursorPersona.getString(4));
                        persona.setTipo_cliente(cursorPersona.getString(5));
                        persona.setUbicacion(cursorPersona.getString(6));
                        persona.setEstado(cursorPersona.getInt(7));
                        listaPersona.add(persona);
                    }
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

    public modelPersona readUno(int id) {
        modelPersona persona = null;
       try{
           DbHelper dbHelper = new DbHelper(context);
           SQLiteDatabase db = dbHelper.getWritableDatabase();
           Cursor cursorPersona = null;
           cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                   + " WHERE id = " + id + " LIMIT 1", null);
           if (cursorPersona.moveToFirst()) {
               persona = new modelPersona(context);
               persona.setId(cursorPersona.getInt(0));
               persona.setNombre(cursorPersona.getString(1));
               persona.setTelefono(cursorPersona.getString(2));
               persona.setDireccion(cursorPersona.getString(3));
               persona.setCorreo(cursorPersona.getString(4));
               persona.setTipo_cliente(cursorPersona.getString(5));
               persona.setUbicacion(cursorPersona.getString(6));
               persona.setEstado(cursorPersona.getInt(7));
           }
           cursorPersona.close();
       }catch (Exception e){
           e.printStackTrace();
       }
       return  persona;
    }

    public ArrayList<modelPersona> readFiltro(String filtroSeleccionado) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<modelPersona> listaPersona = new ArrayList<>();
        modelPersona persona = null;
        Cursor cursorPersona;

        cursorPersona = db.rawQuery("SELECT * FROM " + TABLE_PERSONA
                + " WHERE tipo_cliente = '" + filtroSeleccionado +"'"
                + " ORDER BY nombre ASC", null);
        if (cursorPersona.moveToFirst()) {
            do {
                if(cursorPersona.getInt(7) == 1){
                    persona = new modelPersona(context);
                    persona.setId(cursorPersona.getInt(0));
                    persona.setNombre(cursorPersona.getString(1));
                    persona.setTelefono(cursorPersona.getString(2));
                    persona.setDireccion(cursorPersona.getString(3));
                    persona.setCorreo(cursorPersona.getString(4));
                    persona.setTipo_cliente(cursorPersona.getString(5));
                    persona.setUbicacion(cursorPersona.getString(6));
                    persona.setEstado(cursorPersona.getInt(7));
                    listaPersona.add(persona);
                }
            } while (cursorPersona.moveToNext());
        }else {
            Toast.makeText(context, "No se encontraron registros de personas.", Toast.LENGTH_SHORT).show();
        }
        cursorPersona.close();
        return listaPersona;

    }

    public boolean delete(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + TABLE_PERSONA + " SET estado = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{0,id});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public boolean update(int id, String nombre, String telefono, String direccion, String correo, String ubicacion) {
        boolean correcto = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PERSONA + " SET nombre = ?, telefono = ?, direccion = ?, correo = ?, link_ubicacion = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, telefono, direccion, correo, ubicacion, id});
            correcto = true; // Se establece a true si la operación de actualización fue exitosa
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }
        return correcto;
    }
}
