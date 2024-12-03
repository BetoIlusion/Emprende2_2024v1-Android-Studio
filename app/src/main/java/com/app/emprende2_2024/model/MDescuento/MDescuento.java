package com.app.emprende2_2024.model.MDescuento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;

import java.util.ArrayList;

public class MDescuento {
    Context context;
    DbHelper dbHelper;
    private int id;
    private MFrecuencia frecuencia;
    private MNotaVenta notaVenta;
    private String nombre;
    private double porcentaje;
    private String descripcion;
    private String fecha_inicio;
    private int estado;

    public MDescuento(Context context) {
        this.id = -1;
        this.nombre = "";
        this.porcentaje = 0;
        this.descripcion = "";
        this.fecha_inicio = "";
        this.estado = 0;
        this.dbHelper = new DbHelper(context);
        this.context = context;
        this.notaVenta = new MNotaVenta(context);
        this.frecuencia = new MFrecuencia(context);
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

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public MFrecuencia getFrecuencia() {
        return frecuencia;
    }

    public MFrecuencia setFrecuencia(){
        return this.frecuencia;
    }
    public void setFrecuencia(MFrecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public MNotaVenta getNotaVenta() {
        return notaVenta;
    }

    public void setNotaVenta(MNotaVenta notaVenta) {
        this.notaVenta = notaVenta;
    }
    public long create() {
        return create(
                getNombre(),
                getPorcentaje(),
                getDescripcion(),
                getFecha_inicio(),
                getNotaVenta().getId(),
                getEstado());
    }

    private long create(String nombre, double porcentaje, String descripcion, String fechaInicio, int id1, int estado) {
        long id = -1;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id_frecuencia",-1);
            values.put("nombre", nombre);
            values.put("porcentaje", porcentaje);
            values.put("descripcion", descripcion);
            values.put("fecha_inicio", fechaInicio);
            values.put("id_nota", id1);
            values.put("estado", 1);
            id = db.insert(dbHelper.TABLE_DESCUENTO, null, values);
            setId((int)id);
        }catch (Exception ex){
            Log.e("TAG", "Error al insertar Categoria: " + ex.getMessage(), ex);
            ex.toString();
        }

        return id;
    }

    public ArrayList<MDescuento> read(){
        ArrayList<MDescuento> array = new ArrayList<>();
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor;
            cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_DESCUENTO + " ORDER BY id DESC", null);

            if (cursor.moveToFirst()){
                do{
                    if(cursor.getInt(7) == 1){ //si el estado es true
                        MDescuento descuento = new MDescuento(context);
                        descuento.setId(cursor.getInt(0));
                        MFrecuencia frecuencia = new MFrecuencia(context);
                        frecuencia = frecuencia.findById(cursor.getInt(1));
                        descuento.setFrecuencia(frecuencia);
                        descuento.setNombre(cursor.getString(2));
                        descuento.setPorcentaje(cursor.getDouble(3));
                        descuento.setDescripcion(cursor.getString(4));
                        descuento.setFecha_inicio(cursor.getString(5));
                        MNotaVenta notaVenta = new MNotaVenta(context);
                        descuento.setNotaVenta(notaVenta.findById(cursor.getInt(6)));
                        descuento.setEstado(cursor.getInt(7));
                        array.add(descuento);
                    }
                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch (Exception e){

        }

        return array;

    }
    public MDescuento findById(int id){
        MDescuento model = new MDescuento(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorDescuento = null;
            cursorDescuento = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_DESCUENTO
                    + " WHERE id = " + id + " LIMIT 1", null);
            if(cursorDescuento.moveToFirst()){
                model.setId(cursorDescuento.getInt(0));
                MFrecuencia mFrecuencia = new MFrecuencia(context);
                model.setFrecuencia(mFrecuencia.findById(cursorDescuento.getInt(1)));
                model.setNombre(cursorDescuento.getString(2));
                model.setPorcentaje(cursorDescuento.getDouble(3));
                model.setDescripcion(cursorDescuento.getString(4));
                model.setFecha_inicio(cursorDescuento.getString(5));
                MNotaVenta mNotaVenta = new MNotaVenta(context);
                model.setNotaVenta(mNotaVenta.findById(cursorDescuento.getInt(6)));
                model.setEstado(cursorDescuento.getInt(7));
            }
            cursorDescuento.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

    public boolean update(int id, MFrecuencia frecuencia, String nombre, double porcentaje, String descripcion, String fecha_inicio, int estado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_DESCUENTO +
                    " SET nombre = ?, porcentaje = ?, descripcion = ?, fecha_inicio = ?, estado = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{nombre,porcentaje,descripcion,fecha_inicio, estado,id});
            MFrecuencia mFrecuencia = new MFrecuencia(context);
            MDescuento descuento = new MDescuento(context);
            descuento = descuento.findById(id);
            mFrecuencia = mFrecuencia.findById(descuento.getFrecuencia().getId());
            mFrecuencia.setFrecuencia(frecuencia.getFrecuencia());
            mFrecuencia.update();
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public int id_persona(){
        return 1;
    }

    public MDescuento findByNombre(String nombre) {
        MDescuento model = new MDescuento(context);
        ArrayList<MDescuento> array = new ArrayList<>();
        array = model.read();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getNombre().equals(nombre)) {
                return array.get(i);
            }
        }
        return null;
    }

    public boolean update() {
        return update(
                getId(),
                getFrecuencia(),
                getNombre(),
                getPorcentaje(),
                getDescripcion(),
                getFecha_inicio(),
                getEstado()
        );
    }


    public MDescuento findBy(int id_nota_venta) {
        MDescuento model = new MDescuento(context);
        ArrayList<MDescuento> array = new ArrayList<>();
        array = model.read();
        for (MDescuento desc :
                array) {
            if (desc.getNotaVenta().getId() == id_nota_venta)
                return desc;
        }
        return model;
    }
}
