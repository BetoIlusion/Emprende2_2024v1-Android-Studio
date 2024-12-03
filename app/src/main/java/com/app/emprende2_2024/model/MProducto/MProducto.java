package com.app.emprende2_2024.model.MProducto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.emprende2_2024.PatronComposite.ReporteI;
import com.app.emprende2_2024.db.DbHelper;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.model.MStock.MStock;

import java.util.ArrayList;

public class MProducto implements ReporteI {
    private int id;
    private String nombre;
    private String sku;
    private float precio;
    private MCategoria categoria;
    private MProveedor proveedor;
    private MStock stock;
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

    public MCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(MCategoria categoria) {
        this.categoria = categoria;
    }

    public MProveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(MProveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public MStock getStock() {
        return stock;
    }



    public void setStock(MStock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre + "(" + getStock().getCantidad() + ")";
    }

    public MProducto(@Nullable Context context) {
        this.id = -1;
        this.nombre = "";
        this.sku = "";
        this.precio = 0;
        this.categoria = new MCategoria(context);
        this.proveedor = new MProveedor(context);
        this.estado = 0;
        this.context = context;
    }

    public long create(String nombre, String precio, String sku, MCategoria categoria, MProveedor proveedor, long id_stock) {
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
            id = db.insert(dbHelper.TABLE_PRODUCTO,null,values);
        }catch (Exception e){
            Toast.makeText(context, "ERROR AL GUARDAR PRODUCTO", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return id;
    }

    public MProducto findById(int id) {
        MProducto producto = null;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorProducto = null;
            cursorProducto = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_PRODUCTO
                    + " WHERE id = " + id + " LIMIT 1", null);
            if (cursorProducto.moveToFirst()) {
                producto = new MProducto(context);
                producto.setId(cursorProducto.getInt(0));
                producto.setNombre(cursorProducto.getString(1));
                producto.setSku(cursorProducto.getString(2));
                producto.setPrecio(cursorProducto.getFloat(3));
                //-> ENTRA AQUI IMAGEN
                producto.setEstado(cursorProducto.getInt(5));
                MCategoria categoria1 = new MCategoria(context);
                categoria1 = categoria1.findById(cursorProducto.getInt(6));
                MProveedor proveedor1 = new MProveedor(context);
                proveedor1 = proveedor1.findById(cursorProducto.getInt(7));
                MStock stock1 = new MStock(context);
                stock1 = stock1.findById(cursorProducto.getInt(8));

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
    public ArrayList<MProducto> ProductoXCategoria(int id_categoria) {
        ArrayList<MProducto> listaProducto = new ArrayList<MProducto>();
        ArrayList<MProducto> listaProductoFinal = new ArrayList<MProducto>();
        MProducto producto = new MProducto(context);
        listaProducto = producto.read();
        for (int i = 0; i < listaProducto.size(); i++) {
            if(listaProducto.get(i).getCategoria().getId() == id_categoria)
                listaProductoFinal.add(listaProducto.get(i));
        }
        return listaProductoFinal;
    }
    public ArrayList<MProducto> read() {
        ArrayList<MProducto> listaProducto = new ArrayList<>();
        MProducto producto;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorProducto;
            cursorProducto = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_PRODUCTO
                    + " ORDER BY id DESC", null);
            if (cursorProducto.moveToFirst()) {
                do {
                    if(cursorProducto.getInt(5) == 1){
                        producto = new MProducto(context);
                        producto.setId(cursorProducto.getInt(0));
                        producto.setNombre(cursorProducto.getString(1));
                        producto.setSku(cursorProducto.getString(2));
                        producto.setPrecio(cursorProducto.getFloat(3));

                        producto.setEstado(cursorProducto.getInt(5));
                        MCategoria categoria1 = new MCategoria(context);
                        categoria1 = categoria1.findById(cursorProducto.getInt(6));
                        producto.setCategoria(categoria1);

                        MProveedor proveedor1 = new MProveedor(context);
                        proveedor1 = proveedor1.findById(cursorProducto.getInt(7));
                        producto.setProveedor(proveedor1);

                        MStock stock1 = new MStock(context);
                        stock1 = stock1.findById(cursorProducto.getInt(8));
                        producto.setStock(stock1);

                        listaProducto.add(producto);
                    }
                } while (cursorProducto.moveToNext());
            }
            cursorProducto.close();
        }catch (Exception e){
            Toast.makeText(context, "ERROR EL DbHelper", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaProducto;
    }

    public boolean update(int id, String nombre, String precio, String sku, String cantidad, String minimo, MCategoria categoria, MProveedor proveedor) {
        boolean b = false;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "UPDATE " + dbHelper.TABLE_PRODUCTO + " SET nombre = ?, precio = ?, sku = ?, id_categoria = ?, id_proveedor = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{nombre, precio, sku, categoria.getId(), proveedor.getId(), id});


            MProducto producto = new MProducto(context);
            producto = producto.findById(id);
            MStock stock = new MStock(context);
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
            String sql = "UPDATE " + dbHelper.TABLE_PRODUCTO + " SET estado = ? WHERE id = ?";
            db.execSQL(sql, new Object[]{0, id});
            b = true;
            db.close();
        } catch (Exception ex) {
            Toast.makeText(context, "ERROR EN MODELO", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            b = false;
        }
        return b;
    }

    public ArrayList<MProducto> fingByCategoria(int id_categoria) {
        ArrayList<MProducto> listaProducto = new ArrayList<>();
        MProducto producto;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursorProducto;
            cursorProducto = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_PRODUCTO
                    + " WHERE id_categoria = " + id_categoria
                    + " ORDER BY id DESC", null);
            if (cursorProducto.moveToFirst()) {
                do {
                    if(cursorProducto.getInt(5) == 1){
                        producto = new MProducto(context);
                        producto.setId(cursorProducto.getInt(0));
                        producto.setNombre(cursorProducto.getString(1));
                        producto.setSku(cursorProducto.getString(2));
                        producto.setPrecio(cursorProducto.getFloat(3));

                        producto.setEstado(cursorProducto.getInt(5));
                        MCategoria categoria1 = new MCategoria(context);
                        categoria1 = categoria1.findById(cursorProducto.getInt(6));
                        producto.setCategoria(categoria1);

                        MProveedor proveedor1 = new MProveedor(context);
                        proveedor1 = proveedor1.findByIdPersona(cursorProducto.getInt(7));
                        producto.setProveedor(proveedor1);

                        MStock stock1 = new MStock(context);
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

    @Override
    public String getNombreReporte() {
        return getNombre();
    }

    @Override
    public int getStockReporte() {
        return getStock().getCantidad();
    }

    @Override
    public double getTotalReporte() {
        return getStockReporte() * getPrecio();
    }

    @Override
    public String generarReporte() {
        return "   "+"Producto: " + getNombreReporte() + ", sub total: $" + getTotalReporte() +
                ", Stock disponible: " + getStockReporte() + "\n";
    }
}
