package com.app.emprende2_2024.controller.CProducto;

import android.content.Context;
import android.widget.Toast;

import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.model.MProducto.modelProducto;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
import com.app.emprende2_2024.model.MStock.modelStock;
import com.app.emprende2_2024.view.VProducto.VProductoEditar;
import com.app.emprende2_2024.view.VProducto.VProductoInsertar;
import com.app.emprende2_2024.view.VProducto.VProductoMain;

import java.util.ArrayList;

public class CProducto {
    private VProductoEditar vEditar = null;
    private VProductoInsertar vInsertar = null;
    private VProductoMain vMain = null;

    Context context;

    public CProducto(Context context) {
        this.context = context;
    }

    public CProducto(VProductoInsertar vProductoInsertar) {
        this.vInsertar = vProductoInsertar;
    }
    public CProducto(VProductoMain vProductoMain) {
        this.vMain = vProductoMain;

    }

    public CProducto(VProductoEditar vProductoEditar) {
        this.vEditar = vProductoEditar;
    }

    public void llenarSpinners() {
        VProductoInsertar view = vInsertar;
        modelCategoria mCategoria = new modelCategoria(view);
        modelProveedor mProveedor = new modelProveedor(view);
        ArrayList<modelCategoria> listaCategoria = mCategoria.read();
        ArrayList<modelProveedor> listaProveedor = mProveedor.read();

        view.llenarSpinners(listaCategoria,listaProveedor);
    }
    public void llenarSpinnersEditar() {
        VProductoEditar view = vEditar;
        modelCategoria mCategoria = new modelCategoria(view);
        modelProveedor mProveedor = new modelProveedor(view);
        ArrayList<modelCategoria> listaCategoria = mCategoria.read();
        ArrayList<modelProveedor> listaProveedor = mProveedor.read();
        view.llenarSpinners(listaCategoria,listaProveedor);
    }

    public void create(String nombre, String precio, String sku, String cantidad, String minimo, modelCategoria categoria, modelProveedor proveedor) {
        VProductoInsertar view = vInsertar;
        modelProducto mProducto = new modelProducto(view);
        modelStock mStock = new modelStock(view);

        long id_stock = mStock.create(
                cantidad,
                minimo
        );
        if(id_stock > 0){
            long id_producto = mProducto.create(
                    nombre,
                    precio,
                    sku,
                    categoria,
                    proveedor,
                    id_stock
            );
            if (id_producto > 0){
                view.showSuccessMessage("Producto Creado Exitisamente");
                view.limpiar();
            }else {
                view.showErrorMessage("ERROR CProducto/create");
            }
        }else {
            view.showErrorMessage("ERROR CProducto/create");
        }
    }

    public void read() {
        VProductoMain view = vMain;
        modelProducto mProducto = new modelProducto(view);

        ArrayList<modelProducto> listaProducto = mProducto.read();

        if (listaProducto.size() > 0) {
            view.listar(listaProducto);
        }else
            view.showErrorMessage("LISTA VACIA");

    }

    public void llenarVista(int id) {
        VProductoEditar view = vEditar;
        modelProducto mProducto = new modelProducto(view);
        mProducto = mProducto.findById(id);
        if(mProducto != null){
            view.llenarVista(
                    mProducto.getNombre(),
                    mProducto.getSku(),
                    mProducto.getPrecio(),
                    mProducto.getStock().getCantidad(),
                    mProducto.getStock().getMinimo(),
                    mProducto.getCategoria(),
                    mProducto.getProveedor()
            );
        }else{
            view.showErrorMessage("erro al rellenar,CProducto/llenarVista");
        }
    }


    public void update(int id, String nombre, String precio, String sku, String cantidad, String minimo, modelCategoria categoria, modelProveedor proveedor) {
        VProductoEditar view = vEditar;
        modelProducto mProducto = new modelProducto(view);
        if(mProducto.update(
                id,
                nombre,
                precio,
                sku,
                cantidad,
                minimo,
                categoria,
                proveedor
        )){
            view.update();
        }else{
            view.showErrorMessage("ERROR, CProducto/update");
        }
    }

    public boolean delete(int id) {
        modelProducto mProducto = new modelProducto(context);
        if(mProducto.delete(id)){
            return true;
        }
        return false;
    }

//    public boolean destroy(int id){
//        boolean b = false;
//        boolean b2 = false;
//        try {
//            MProducto modelProducto = new MProducto(context);
//            MStock modelStock = new MStock(context);
//
//            b = modelStock.delete(id);
//            b2 = modelProducto.delete(id);
//            if (b && b2){
//                return true;
//            }else{
//                return false;
//            }
//        }catch (Exception e){
//            Toast.makeText(context, "ERROR EN CONTROLLER", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//        return false;
//    }
}
