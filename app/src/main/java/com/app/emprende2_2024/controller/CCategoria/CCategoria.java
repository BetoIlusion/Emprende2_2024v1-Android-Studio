package com.app.emprende2_2024.controller.CCategoria;

import android.content.Context;
import android.widget.Toast;

import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.model.MStock.MStock;
import com.app.emprende2_2024.model.MStock.Stock;
import com.app.emprende2_2024.view.VCategoria.VCategoriaEditar;
import com.app.emprende2_2024.view.VCategoria.VCategoriaInsertar;
import com.app.emprende2_2024.view.VCategoria.VCategoriaMain;
import com.app.emprende2_2024.view.VCategoria.VCategoriaProductoShowPDF;

import java.util.ArrayList;

public class CCategoria {
    private VCategoriaProductoShowPDF vProductos;
    private Context adapter;
    private VCategoriaEditar vEditar;
    private VCategoriaMain vMain;
    private VCategoriaInsertar vInsertar;
    public CCategoria(VCategoriaProductoShowPDF vCategoriaProductoShowPDF){
        this.vProductos = vCategoriaProductoShowPDF;
    }
    public CCategoria(VCategoriaMain vCategoriaMain) {
        this.vMain = vCategoriaMain;
    }
    public CCategoria(VCategoriaInsertar vCategoriaInsertar) {
        this.vInsertar = vCategoriaInsertar;
    }
    public CCategoria(VCategoriaEditar vCategoriaEditar) {
        this.vEditar = vCategoriaEditar;
    }
    public CCategoria(Context context) {
        this.adapter = context;
    }

    public void create(String nombre, String descripcion) {
        try(MCategoria model = new MCategoria(vInsertar)){
            long id = model.create(
                    nombre,
                    descripcion
            );
            if(id > 0){
                Toast.makeText(vInsertar, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                vInsertar.limpiar();
            } else {
                Toast.makeText(vInsertar, "ERROR AL GUARDAR REGISTRO MODEL", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listar() {
        MCategoria model = new MCategoria(vMain);
        vMain.listar(model.read());
    }

    public void readUno(int id) {
        MCategoria model = new MCategoria(vEditar);
        vEditar.readUno(model.readUno(id));
    }

    public void update(int id, String nombre, String descripcion) {
        MCategoria model = new MCategoria(vEditar);
        boolean b = model.update(
                id,
                nombre,
                descripcion);
        if (b){
            vEditar.VCategoriaMain();
        }else{
            Toast.makeText(vEditar, "ERROR AL MODIFICAR", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean delete(int id) {
        MCategoria model = new MCategoria(adapter);
        if(model.delete(id)){
            return true;
        }
        return false;
    }

    public void productos(int id) {

        MCategoria modelCategoria = new MCategoria(vProductos);
        MProducto modelProducto = new MProducto(vProductos);
        MStock modelStock = new MStock(vProductos);

        Categoria categoria = modelCategoria.readUno(id);
        ArrayList<Producto> productos = modelProducto.read();
        ArrayList<Producto> categeriaXproductos = new ArrayList<>();
        ArrayList<Stock> stocks = modelStock.read();
        for (int i = 0; i < productos.size(); i++) {
            if (categoria.getId() == productos.get(i).getId_categoria()){
                categeriaXproductos.add(productos.get(i));
            }
        }
        vProductos.readProductos(categeriaXproductos,stocks);


    }
}
