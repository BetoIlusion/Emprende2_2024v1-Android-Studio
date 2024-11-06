package com.app.emprende2_2024.controller.CProducto;

import android.content.Context;

import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.model.MStock.MStock;
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
        MCategoria mCategoria = new MCategoria(view);
        MProveedor mProveedor = new MProveedor(view);
        ArrayList<MCategoria> listaCategoria = mCategoria.read();
        ArrayList<MProveedor> listaProveedor = mProveedor.read();

        view.llenarSpinners(listaCategoria,listaProveedor);
    }
    public void llenarSpinnersEditar() {
        VProductoEditar view = vEditar;
        MCategoria mCategoria = new MCategoria(view);
        MProveedor mProveedor = new MProveedor(view);
        ArrayList<MCategoria> listaCategoria = mCategoria.read();
        ArrayList<MProveedor> listaProveedor = mProveedor.read();
        view.llenarSpinners(listaCategoria,listaProveedor);
    }

    public void create(String nombre, String precio, String sku, String cantidad, String minimo, MCategoria categoria, MProveedor proveedor) {
        VProductoInsertar view = vInsertar;
        MProducto mProducto = new MProducto(view);
        MStock mStock = new MStock(view);

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
                view.mensaje("Producto creado exitosamente");
                view.limpiar();
            }else {
                view.mensaje("ERROR CProducto/create");
            }
        }else {
            view.mensaje("ERROR CProducto/create");
        }
    }

    public void read() {
        VProductoMain view = vMain;
        MProducto mProducto = new MProducto(view);

        ArrayList<MProducto> listaProducto = mProducto.read();

        if (listaProducto.size() > 0) {
            view.listar(listaProducto);
        }else
            view.mensaje("LISTA VACIA");

    }

    public void llenarVista(int id) {
        VProductoEditar view = vEditar;
        MProducto mProducto = new MProducto(view);
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
            view.mensaje("error al rellenar,CProducto/llenarVista");
        }
    }


    public void update(int id, String nombre, String precio, String sku, String cantidad, String minimo, MCategoria categoria, MProveedor proveedor) {
        VProductoEditar view = vEditar;
        MProducto mProducto = new MProducto(view);
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
            view.mensaje("ERROR, CProducto/update");
        }
    }

    public boolean delete(int id) {
        MProducto mProducto = new MProducto(context);
        if(mProducto.delete(id)){
            return true;
        }
        return false;
    }

//    public boolean destroy(int id){
//        boolean b = false;
//        boolean b2 = false;
//        try {
//            MProducto MProducto = new MProducto(context);
//            MStock MStock = new MStock(context);
//
//            b = MStock.delete(id);
//            b2 = MProducto.delete(id);
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
