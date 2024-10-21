package com.app.emprende2_2024.model.MStock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;

public class modelStock extends DbHelper {
    private int id;
    private int cantidad;
    private int minimo;
    private Context context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return "modelStock{" +
                "cantidad=" + cantidad +
                '}';
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public modelStock(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long create(String cantidad, String minimo) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cantidad", Integer.parseInt(cantidad));
            values.put("minimo", Integer.parseInt(minimo));
            id = db.insert(TABLE_STOCK,null,values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR STOCK", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }
    public modelStock findById(int id){
        modelStock stock = null;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorStock = null;
            cursorStock = db.rawQuery("SELECT * FROM " + TABLE_STOCK
                    + " WHERE id = " + id + " LIMIT 1", null);
            if (cursorStock.moveToFirst()){
               stock = new modelStock(context);
               stock.setId(cursorStock.getInt(0));
               stock.setCantidad(cursorStock.getInt(1));
               stock.setMinimo(cursorStock.getInt(2));
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "ERROR AL BUSCAR STOCK", Toast.LENGTH_SHORT).show();
        }
        return stock;
    }

    public boolean update(int id, String cantidad, String minimo) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_STOCK + " SET cantidad = ?, minimo = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{cantidad, minimo, id});
            b = true;
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }
//    public boolean min(int min){
//
//    }
}
