package com.app.emprende2_2024.controller.CCategoria;

import android.content.Context;
import android.view.View;

import com.app.emprende2_2024.PatronAdapter.CaptureInterface;
import com.app.emprende2_2024.PatronAdapter.PDFCaptureAdapter;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MProducto.MProducto;
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
        MCategoria mCategoria = new MCategoria(vInsertar,
                -1,
                nombre,
                descripcion);
        if(mCategoria.create()){
            vInsertar.limpiar();
          vInsertar.mensaje("Categoria Creada");
        }else{
            vInsertar.mensaje("ERROR en el Model");
        }
    }

    public void listar() {
        MCategoria model = new MCategoria(vMain);
        vMain.listar(model.read());
    }

    public void readUno(int id) {
        MCategoria mCategoria = new MCategoria(vEditar);
        vEditar.readUno(mCategoria.findById(id));
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
            vEditar.mensaje("ERROR en el MODEL");
        }
    }

    public boolean delete(int id) {
        MCategoria mCategoria = new MCategoria(adapter);
        return mCategoria.delete(id);
    }

    public void llenar(int id) {
        MProducto mProducto = new MProducto(vProductos);
        ArrayList<MProducto> listaProductos = new ArrayList<>();
        listaProductos = mProducto.ProductoXCategoria(id);
        if(listaProductos.size() > 0){
            vProductos.llenar(listaProductos);
        }else{
            vProductos.mensaje("No hay productos en esta categoria");
        }

    }

    public void compartirCatologo() {
        MCategoria mCategoria = new MCategoria(vMain);
        String catalogoString = mCategoria.compartirCatalogo();
        vMain.guardarYCompartirTexto(catalogoString);
    }

//    public boolean destroy(int id){
//        MCategoria model = new MCategoria(adapter);
//        if(model.destroy(id)){
//            return true;
//        }
//        return false;
//    }
}
