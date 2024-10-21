package com.app.emprende2_2024.model.MProducto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
import com.app.emprende2_2024.model.MStock.modelStock;

import java.util.ArrayList;

public class modelProducto extends DbHelper {
    private int id;
    private String nombre;
    private String sku;
    private float precio;
    private modelCategoria categoria;
    private modelProveedor proveedor;
    private modelStock stock;
    private int estado;
    private Context context;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public modelCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(modelCategoria categoria) {
        this.categoria = categoria;
    }

    public modelProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(modelProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public modelStock getStock() {
        return stock;
    }

    public void setStock(modelStock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public modelProducto(@Nullable Context context) {
        super(context);
        this.id = -1;
        this.nombre = "";
        this.sku = "";
        this.precio = 0;
        this.categoria = new modelCategoria(context);
        this.proveedor = new modelProveedor(context);
        this.estado = 0;
        this.context = context;
    }

    public long create(String nombre, String precio, String sku, modelCategoria categoria, modelProveedor proveedor, long id_stock) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("SKU", sku);
            values.put("precio",precio);
            values.put("estado",1);
            values.put("id_categoria",categoria.getId());
            values.put("id_proveedor",proveedor.getId());
            values.put("id_stock",id_stock);
            id = db.insert(TABLE_PRODUCTO,null,values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public modelProducto findById(int id) {
        modelProducto producto = null;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorProducto = null;
            cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO
                    + " WHERE id = " + id + " LIMIT 1", null);
            if (cursorProducto.moveToFirst()) {
                producto = new modelProducto(context);
                producto.setId(cursorProducto.getInt(0));
                producto.setNombre(cursorProducto.getString(1));
                producto.setSku(cursorProducto.getString(2));
                producto.setPrecio(cursorProducto.getFloat(3));
                //-> ENTRA AQUI IMAGEN
                modelCategoria categoria1 = new modelCategoria(context);
                categoria1 = categoria1.findById(cursorProducto.getInt(5));
                modelProveedor proveedor1 = new modelProveedor(context);
                proveedor1 = proveedor1.findById(cursorProducto.getInt(6));
                modelStock stock1 = new modelStock(context);
                stock1 = stock1.findById(cursorProducto.getInt(7));

                producto.setCategoria(categoria1);
                producto.setProveedor(proveedor1);
                producto.setStock(stock1);
            }
            cursorProducto.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return producto;
    }

    public ArrayList<modelProducto> read() {
        ArrayList<modelProducto> listaProducto = new ArrayList<>();
        modelProducto producto;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorProducto;
            cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO
                    + " ORDER BY id DESC", null);
            if (cursorProducto.moveToFirst()) {
                do {
                    if(cursorProducto.getInt(5) == 1){
                        producto = new modelProducto(context);
                        producto.setId(cursorProducto.getInt(0));
                        producto.setNombre(cursorProducto.getString(1));
                        producto.setSku(cursorProducto.getString(2));
                        producto.setPrecio(cursorProducto.getFloat(3));

                        producto.setEstado(cursorProducto.getInt(5));
                        modelCategoria categoria1 = new modelCategoria(context);
                        categoria1 = categoria1.findById(cursorProducto.getInt(6));
                        producto.setCategoria(categoria1);

                        modelProveedor proveedor1 = new modelProveedor(context);
                        proveedor1 = proveedor1.findById(cursorProducto.getInt(7));
                        producto.setProveedor(proveedor1);

                        modelStock stock1 = new modelStock(context);
                        stock1 = stock1.findById(cursorProducto.getInt(8));
                        producto.setStock(stock1);

                        listaProducto.add(producto);
                    }
                } while (cursorProducto.moveToNext());
            }
            cursorProducto.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR EL DbHelper", Toast.LENGTH_SHORT).show();
            listaProducto = null;
            e.printStackTrace();
        }
        return listaProducto;
    }

    public boolean update(int id, String nombre, String precio, String sku, String cantidad, String minimo, modelCategoria categoria, modelProveedor proveedor) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PRODUCTO + " SET nombre = ?, precio = ?, sku = ?, id_categoria = ?, id_proveedor = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, precio, sku, categoria.getId(), proveedor.getId(), id});


            modelProducto producto = new modelProducto(context);
            producto = producto.findById(id);
            modelStock stock = new modelStock(context);
            stock = stock.findById(producto.getStock().getId());
            if(stock.update(
                    stock.getId(),
                    cantidad,
                    minimo
            )){
                b = true;
            }else {
                Toast.makeText(context, "ERROR EN mProducto/update", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } catch (Exception ex) {
            Toast.makeText(context, "ERROR EN MODELO", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            b = false;
        }
        return b;
    }

    public boolean delete(int id) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PRODUCTO + " SET estado = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{0, id});
            db.close();
        } catch (Exception ex) {
            Toast.makeText(context, "ERROR EN MODELO", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            b = false;
        }
        return b;
    }
}
