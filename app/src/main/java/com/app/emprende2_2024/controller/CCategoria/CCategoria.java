package com.app.emprende2_2024.controller.CCategoria;

import android.content.Context;

import com.app.emprende2_2024.model.MCategoria.modelCategoria;
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
        modelCategoria mCategoria = new modelCategoria(vInsertar,
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
        modelCategoria model = new modelCategoria(vMain);
        vMain.listar(model.read());
    }

    public void readUno(int id) {
        modelCategoria mCategoria = new modelCategoria(vEditar);
        vEditar.readUno(mCategoria.findById(id));
    }

    public void update(int id, String nombre, String descripcion) {
        modelCategoria model = new modelCategoria(vEditar);
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
        modelCategoria mCategoria = new modelCategoria(adapter);
        return mCategoria.delete(id);
    }

//    public boolean destroy(int id){
//        MCategoria model = new MCategoria(adapter);
//        if(model.destroy(id)){
//            return true;
//        }
//        return false;
//    }
}
