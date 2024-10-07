package com.app.emprende2_2024.model.MStock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MDetalleFactura.DetalleFactura;

import java.util.ArrayList;

public class MStock extends DbHelper {
    Context context;
    public MStock(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long create(String cantidad, long idProducto) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("cantidad", Integer.parseInt(cantidad));
            values.put("id_producto", idProducto);
            id = db.insert(TABLE_STOCK,null,values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR STOCK", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Stock> read() {
        ArrayList<Stock> listaStock = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Stock stock;
            Cursor cursorStock;
            cursorStock = db.rawQuery("SELECT * FROM " + TABLE_STOCK
                    + " ORDER BY id ASC", null);
            if (cursorStock.moveToFirst()) {
                do {
                    stock = new Stock();
                    stock.setId(cursorStock.getInt(0));
                    stock.setCantidad(cursorStock.getInt(1));
                    stock.setId_producto(cursorStock.getInt(2));
                    listaStock.add(stock);
                } while (cursorStock.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron stocks", Toast.LENGTH_SHORT).show();
            }
            cursorStock.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR DEL DbHelper", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaStock;
    }

    public boolean updateUno(String cantidad, int id_producto) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_STOCK + " SET cantidad = ? WHERE id_producto = ?";
            db.execSQL(sql, new Object[]{cantidad, id_producto});
            b = true; // Se establece a true si la operación de actualización fue exitosa
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    public boolean delete(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_STOCK + " WHERE id = '" + id  +"'");
            correcto = true; // Se establece a true si la operación de eliminación fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return correcto;
    }

    public Stock readUno(int id_producto) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Stock stock = null;
        Cursor cursorStock = null;
        cursorStock = db.rawQuery("SELECT * FROM " + TABLE_STOCK
                + " WHERE id_producto = " + id_producto + " LIMIT 1", null);
        if (cursorStock.moveToFirst()) {
            stock = new Stock();
            stock.setId(cursorStock.getInt(0));
            stock.setCantidad(cursorStock.getInt(1));
            stock.setId_producto(cursorStock.getInt(2));
        }
        cursorStock.close();
        return stock;
    }

    public void update(ArrayList<DetalleFactura> detalles, ArrayList<Stock> stocks) {
        boolean b = false;
        try {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (int i = 0; i < detalles.size(); i++) {
                for (int j = 0; j < stocks.size(); j++) {
                    if (detalles.get(i).getProducto().getId() == stocks.get(j).getId_producto()){
                        String sql = "UPDATE " + TABLE_STOCK + " SET cantidad = ? WHERE id_producto = ?";
                        db.execSQL(sql, new Object[]{stocks.get(j).getCantidad()
                                - detalles.get(i).getCantidad(), detalles.get(i).getProducto().getId()});
                        b = true;
                        db.close();
                    }
                }
            }
    } catch (Exception ex) {
        Toast.makeText(context, "ERROR EN MODELO", Toast.LENGTH_SHORT).show();
        ex.printStackTrace();
        b = false;
    }
    }
}
