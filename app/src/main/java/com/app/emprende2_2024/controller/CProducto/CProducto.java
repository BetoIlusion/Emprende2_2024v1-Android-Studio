package com.app.emprende2_2024.controller.CProducto;

import android.content.Context;
import android.widget.Toast;

import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.model.MProducto.ProductoFull;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.model.MStock.MStock;
import com.app.emprende2_2024.model.MStock.Stock;
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
        try{

            MCategoria modelCategoria = new MCategoria(view);
            MProveedor modelProveedor = new MProveedor(view);
            MPersona modelPersona = new MPersona(view);

            ArrayList<Proveedor> arrayProveedor = modelProveedor.read();
            ArrayList<Persona> arrayPersona = modelPersona.read();
            ArrayList<Persona> arrayPersonaProveedor = new ArrayList<>();


            for (int i = 0; i < arrayProveedor.size(); i++) {
                for (int j = 0; j < arrayPersona.size(); j++) {
                    if (arrayProveedor.get(i).getId_persona() == arrayPersona.get(j).getId())
                        arrayPersonaProveedor.add(arrayPersona.get(j));
                }
            }

            ArrayList<Categoria> arrayCategoria = modelCategoria.read();

            view.llenarSpinners(arrayCategoria,arrayPersonaProveedor);
        }catch (Exception e){
            view.mensaje("ERROR EN EL CONTROLLER");
            e.printStackTrace();
        }
    }
    public void llenarSpinnersEditar() {
        VProductoEditar view = vEditar;
        try{

            MCategoria modelCategoria = new MCategoria(view);
            MProveedor modelProveedor = new MProveedor(view);
            MPersona modelPersona = new MPersona(view);

            ArrayList<Proveedor> arrayProveedor = modelProveedor.read();
            ArrayList<Persona> arrayPersona = modelPersona.read();
            ArrayList<Persona> arrayPersonaProveedor = new ArrayList<>();


            for (int i = 0; i < arrayProveedor.size(); i++) {
                for (int j = 0; j < arrayPersona.size(); j++) {
                    if (arrayProveedor.get(i).getId_persona() == arrayPersona.get(j).getId())
                        arrayPersonaProveedor.add(arrayPersona.get(j));
                }
            }

            ArrayList<Categoria> arrayCategoria = modelCategoria.read();

            view.llenarSpinners(arrayCategoria,arrayPersonaProveedor);
        }catch (Exception e){
            view.mensaje("ERROR EN EL CONTROLLER");
            e.printStackTrace();
        }

    }

    public void create(String nombre, String precio, String sku, String cantidad, Categoria categoria, Persona proveedorPersona) {
        VProductoInsertar view = vInsertar;
        try{
            MProducto modelProducto = new MProducto(view);
            MStock modelStock = new MStock(view);
            long id_producto = modelProducto.create(
               nombre,
                sku,
                precio,
                categoria,
                proveedorPersona
           );
           long id2 = modelStock.create(
                   cantidad,
                   id_producto
           );
           if (id2 > 0 && id_producto > 0){
               view.mensaje("REGISTRO GUARDADO EXITOSAMENTE");
               view.limpiar();
           }else{
               view.mensaje("ERROR EN LOS id´s");
           }
        }catch (Exception e){
            view.mensaje("ERROR EN EL CONTROLLER");
            e.printStackTrace();
        }
    }

    public void read() {
        VProductoMain view = vMain;
        MProducto modelProducto = new MProducto(view);
        MStock modelStock = new MStock(view);
        MCategoria modelCategoria = new MCategoria(view);
        MProveedor modelProveedor = new MProveedor(view);
        MPersona modelPersona = new MPersona(view);

        ArrayList<Producto> arrayProducto   = modelProducto.read();
        ArrayList<Stock> arrayStock         = modelStock.read();
        ArrayList<Categoria> arrayCategoria = modelCategoria.read();
        ArrayList<Proveedor> arrayProveedor = modelProveedor.read();
        ArrayList<Persona> arrayPersona     = modelPersona.read();

        ArrayList<ProductoFull> arrayListProductoFull = modelProducto.readFull(arrayProducto,
                arrayStock,
                arrayCategoria,
                arrayProveedor,
                arrayPersona
                );
        view.listar(arrayListProductoFull);


    }

    public void readUno(int id) {
        VProductoEditar view = vEditar;
        try{
            MProducto modelProducto = new MProducto(view);
            MStock modelStock = new MStock(view);
            MCategoria modelCategoria = new MCategoria(view);
            MProveedor modelProveedor = new MProveedor(view);
            MPersona modelPersona = new MPersona(view);
            Producto producto = modelProducto.readUno(id);
            ArrayList<Stock> stocks = modelStock.read();
            Stock stock = new Stock();
            for (int i = 0; i < stocks.size(); i++) {
                if(producto.getId() == stocks.get(i).getId_producto()){
                    stock = stocks.get(i);
                }
            }

            Categoria categoria = modelCategoria.readUno(producto.getId_categoria());
            Proveedor proveedor = modelProveedor.readUno(producto.getId_proveedor());
            Persona persona = modelPersona.readUno(proveedor.getId_persona());

            view.llenarVista(
                    producto.getNombre(),
                    producto.getSku(),
                    producto.getPrecio(),
                    stock.getCantidad(),
                    categoria,
                    persona
            );
        }catch (Exception e ){
            view.mensaje("ERROR EN CONTROLLER");
            e.printStackTrace();
        }


    }


    public void update(int id, String nombre, String precio, String sku, String cantidad, Categoria categoria, Persona persona) {
        boolean b = false;
        boolean b2 = false;
        VProductoEditar view = vEditar;
        try{
            MProveedor modelProveedor = new MProveedor(view);
            Proveedor proveedor = modelProveedor.readUno(persona.getId());
            MProducto producto = new MProducto(view);
            MStock stock = new MStock(view);
            b = stock.updateUno(
                    cantidad,
                    id
            );
            if(b){
                b2 = producto.update(
                        id,
                        nombre,
                        precio,
                        sku,
                        categoria,
                        proveedor
                );
                if (b2){
                    view.mensaje("PRODUCTO ACTUALIZADO EXITOSAMENTE");
                }
            }
        }catch (Exception e){
            view.mensaje("ERROR EN CONTROLLADOR");
            e.printStackTrace();
        }
    }

    public boolean delete(int id) {
        boolean b = false;
        boolean b2 = false;
        try {
            MProducto modelProducto = new MProducto(context);
            MStock modelStock = new MStock(context);

            b = modelStock.delete(id);
            b2 = modelProducto.delete(id);
            if (b && b2){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            Toast.makeText(context, "ERROR EN CONTROLLER", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return false;
    }
}
