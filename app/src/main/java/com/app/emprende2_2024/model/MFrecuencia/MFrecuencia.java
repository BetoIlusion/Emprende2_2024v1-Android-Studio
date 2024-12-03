package com.app.emprende2_2024.model.MFrecuencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MTipoFrecuencia;

import java.util.ArrayList;

public class MFrecuencia {
    private int id;
    private MPersona persona;
    private int frecuencia;
    private MTipoFrecuencia tipoFrecuencia;
    Context context;
    DbHelper dbHelper;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public MTipoFrecuencia getTipoFrecuencia() {
        return tipoFrecuencia;
    }

    public void setTipoFrecuencia(MTipoFrecuencia tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }

    public MPersona getPersona() {
        return persona;
    }

    public void setPersona(MPersona persona) {
        this.persona = persona;
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
        this.frecuencia = -1;
        this.tipoFrecuencia = new MTipoFrecuencia(context);
        this.persona = new MPersona(context);
        this.dbHelper = new DbHelper(context);
    }
    public MFrecuencia(Context context, int id, int frecuencia) {
        this.id = id;
        this.persona = new MPersona(context);
        this.frecuencia = frecuencia;
        this.tipoFrecuencia = new MTipoFrecuencia(context);

        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    public long create(){
        return create(getFrecuencia(), getTipoFrecuencia(), getPersona());
    }
    public long create(int frecuencia, MTipoFrecuencia tipoFrecuencia, MPersona persona){
        long id = 0;
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("frecuencia", frecuencia);
            values.put("id_tipo_frecuencia", tipoFrecuencia.getId());
            values.put("id_persona", persona.getId());
            id = db.insert(dbHelper.TABLE_FRECUENCIA, null, values);
            setId((int)id);
        }catch (Exception ex){
            Log.e("TAG", "Error al insertar Categoria: " + ex.getMessage(), ex);
            ex.toString();
        }
        return id;
    }
    public ArrayList<MFrecuencia> read() {
        ArrayList<MFrecuencia> arrayList = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        MFrecuencia frecuencia = new MFrecuencia(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorFrecuencia = null;
            cursorFrecuencia = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_FRECUENCIA + " ORDER BY id DESC", null);
            if(cursorFrecuencia.moveToFirst()){
                do{
                    frecuencia = new MFrecuencia(context);
                    frecuencia.setId(cursorFrecuencia.getInt(0));
                    MTipoFrecuencia tipoFrecuencia = new MTipoFrecuencia(context);
                    frecuencia.setTipoFrecuencia(tipoFrecuencia.findById(cursorFrecuencia.getInt(1)));
                    MPersona persona = new MPersona(context);
                    frecuencia.setPersona(persona.findById(cursorFrecuencia.getInt(2)));
                    frecuencia.setFrecuencia(cursorFrecuencia.getInt(3));
                    arrayList.add(frecuencia);
                }while (cursorFrecuencia.moveToNext());
            }
            cursorFrecuencia.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }
    public MFrecuencia findById(int id) {
        MFrecuencia model = new MFrecuencia(context);
        try{
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorFrecuencia = null;
            cursorFrecuencia = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_FRECUENCIA
                    + " WHERE id = " + id + " LIMIT 1", null);
            if(cursorFrecuencia.moveToFirst()){
                model.setId(cursorFrecuencia.getInt(0));
                MTipoFrecuencia modelTipoFrecuencia = new MTipoFrecuencia(context);
                model.setTipoFrecuencia(modelTipoFrecuencia.findById(cursorFrecuencia.getInt(1)));
                MPersona modelPersona = new MPersona(context);
                model.setPersona(modelPersona.findById(cursorFrecuencia.getInt(2)));
                model.setFrecuencia(cursorFrecuencia.getInt(3));
            }
            cursorFrecuencia.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

    public boolean update(MFrecuencia frecuenciaPersona) {
        if(frecuenciaPersona.getId() > 0){
            this.id = frecuenciaPersona.getId();
            this.frecuencia = frecuenciaPersona.getFrecuencia();
            this.tipoFrecuencia = frecuenciaPersona.getTipoFrecuencia();
            return update(
                    getId(),
                    getFrecuencia(),
                    getTipoFrecuencia()
            );
        }
        return false;
    }
    public boolean update(int id, int frecuencia, MTipoFrecuencia tipoFrecuencia) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean b = false;
        try{
            String sql = "UPDATE " + dbHelper.TABLE_FRECUENCIA + " SET frecuencia = ?, id_tipo_frecuencia = ? WHERE id = ?";
            db.execSQL(sql,new Object[]{frecuencia,tipoFrecuencia.getId(),id});
            b = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }


    public ArrayList<MFrecuencia> read(int id_persona) {
        ArrayList<MFrecuencia> arrayFinal = new ArrayList<>();

        MFrecuencia model = new MFrecuencia(context);
        ArrayList<MFrecuencia> arrayList = new ArrayList<>();
        arrayList = model.read();
        for (MFrecuencia frec :
                arrayList) {
            if (frec.getPersona().getId() == id_persona) {
                arrayFinal.add(frec);
            }
        }
        return arrayFinal;
    }

    public boolean update() {
        return update(
                getId(),
                getFrecuencia(),
                getTipoFrecuencia()
        );
    }

    public MFrecuencia findByTiempo(String tiempo) {
        MFrecuencia model = new MFrecuencia(context);
        ArrayList<MFrecuencia> arrayList = new ArrayList<>();
        arrayList = model.read();
        for (MFrecuencia frec :
                arrayList) {
            if (frec.getTipoFrecuencia().getNombre().equals(tiempo)) {
                return frec;
            }
        }
        return null;
    }

    public MFrecuencia findBy(int id_persona, String tiempo) {
        MFrecuencia model = new MFrecuencia(context);
        ArrayList<MFrecuencia> arrayList = new ArrayList<>();
        arrayList = model.read();

        for (MFrecuencia frec :
                arrayList) {
            if (frec.getPersona().getId() == id_persona && frec.getTipoFrecuencia().getNombre().equals(tiempo)) {
                return frec;
            }
            }
        return model;
    }
}

