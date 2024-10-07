package com.app.emprende2_2024.model.MProducto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.model.MStock.Stock;

import java.util.ArrayList;

public class MProducto extends DbHelper {

    Context context;
    public MProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long create(String nombre, String sku, String precio, Categoria categoria, Persona proveedorPersona) {
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("sku", sku);
            values.put("precio",precio);
            values.put("id_categoria",categoria.getId());
            values.put("id_proveedor",proveedorPersona.getId());
            id = db.insert(TABLE_PRODUCTO,null,values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Producto> read() {
        ArrayList<Producto> listaProducto = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Producto producto;
            Cursor cursorProducto;

            cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO
                    + " ORDER BY id DESC", null);
            if (cursorProducto.moveToFirst()) {
                do {
                    producto = new Producto();
                    producto.setId(cursorProducto.getInt(0));
                    producto.setNombre(cursorProducto.getString(1));
                    producto.setSku(cursorProducto.getString(2));
                    producto.setPrecio(cursorProducto.getFloat(3));
                    producto.setId_categoria(cursorProducto.getInt(5));
                    producto.setId_proveedor(cursorProducto.getInt(6));
                    listaProducto.add(producto);
                } while (cursorProducto.moveToNext());
            }else {
                Toast.makeText(context, "No se encontraron registros de productos.", Toast.LENGTH_SHORT).show();
            }
            cursorProducto.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR EL DbHelper", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaProducto;
    }


    public ArrayList<ProductoFull> readFull(ArrayList<Producto> arrayProducto, ArrayList<Stock> arrayStock, ArrayList<Categoria> arrayCategoria, ArrayList<Proveedor> arrayProveedor, ArrayList<Persona> arrayPersona) {
        ArrayList<ProductoFull> arrayFull = new ArrayList<>();
        try{
            for (int i = 0; i < arrayProducto.size(); i++) {
                ProductoFull productoFull = new ProductoFull();
                productoFull.setId(arrayProducto.get(i).getId());
                productoFull.setNombre(arrayProducto.get(i).getNombre());
                productoFull.setSku(arrayProducto.get(i).getSku());
                productoFull.setPrecio(arrayProducto.get(i).getPrecio());
                for (int j = 0; j < arrayStock.size(); j++) {
                    if (arrayStock.get(j).getId_producto() == arrayProducto.get(i).getId()){
                        productoFull.setCantidad(arrayStock.get(j).getCantidad());
                        break;
                    }
                }
                for (int j = 0; j < arrayCategoria.size(); j++) {
                    if (arrayCategoria.get(j).getId() == arrayProducto.get(i).getId_categoria()){
                        productoFull.setId_categoria(arrayCategoria.get(j).getId());
                        productoFull.setNombreCategoria(arrayCategoria.get(j).getNombre());
                        break;
                    }
                }
                for (int j = 0; j < arrayProveedor.size(); j++) {
                    for (int k = 0; k < arrayPersona.size(); k++) {
                        if (arrayProveedor.get(j).getId_persona() == arrayPersona.get(k).getId()){
                            productoFull.setId_proveedor(arrayPersona.get(k).getId());
                            productoFull.setNombreProveedor(arrayPersona.get(k).getNombre());
                            break;
                        }
                    }
                }
                arrayFull.add(productoFull);
            }
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR REGISTRO MODEL", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return arrayFull;
    }

    public Producto readUno(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Producto producto = null;
        Cursor cursorProducto = null;
        cursorProducto = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTO
                + " WHERE id = " + id + " LIMIT 1", null);
        if (cursorProducto.moveToFirst()) {
            producto = new Producto();
            producto.setId(cursorProducto.getInt(0));
            producto.setNombre(cursorProducto.getString(1));
            producto.setSku(cursorProducto.getString(2));
            producto.setPrecio(cursorProducto.getFloat(3));
            //-> ENTRA AQUI IMAGEN
            producto.setId_categoria(cursorProducto.getInt(5));
            producto.setId_proveedor(cursorProducto.getInt(6));
        }
        cursorProducto.close();
        return producto;
    }

    public boolean update(int id, String nombre, String precio, String sku, Categoria categoria, Proveedor proveedor) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + TABLE_PRODUCTO + " SET nombre = ?, precio = ?, sku = ?, id_categoria = ?, id_proveedor = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, precio, sku, categoria.getId(), proveedor.getId(), id});
            b = true; // Se establece a true si la operación de actualización fue exitosa
            db.close();
        } catch (Exception ex) {
            Toast.makeText(context, "ERROR EN MODELO", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            b = false;
        }
        return  b;
    }

    public boolean delete(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCTO + " WHERE id = '" + id  +"'");
            correcto = true; // Se establece a true si la operación de eliminación fue exitosa
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return correcto;
    }
}
