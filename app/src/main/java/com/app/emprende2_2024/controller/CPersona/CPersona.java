package com.app.emprende2_2024.controller.CPersona;

import android.content.Context;
import android.widget.Toast;

import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.view.VPersona.VPersonaEditar;
import com.app.emprende2_2024.view.VPersona.VPersonaInsertar;
import com.app.emprende2_2024.view.VPersona.VPersonaMain;

import java.util.ArrayList;

public class CPersona {
    private VPersonaMain vMain;
    private VPersonaInsertar vInsertar;
    private VPersonaEditar vEditar;
    private Context adapter;

    public CPersona(VPersonaMain vPersonaMain) {
        this.vMain = vPersonaMain;
    }
    public CPersona(VPersonaInsertar vPersonaInsertar) {
        this.vInsertar =  vPersonaInsertar;
    }
    public CPersona(VPersonaEditar vPersonaEditar) {
        this.vEditar = vPersonaEditar;
    }
    public CPersona(Context context) {
        this.adapter = context;
    }

    public void listar() {
        MPersona modelPersona = new MPersona(vMain);
        MProveedor modelProveedor = new MProveedor(vMain);

        ArrayList<Persona> personas = modelPersona.read();
        ArrayList<Proveedor> proveedors = modelProveedor.read();
        vMain.listar(personas,proveedors);
    }
    public void create(String nombre, String telefono, String direccion, String correo, String tipoCliente, String estado, String ubicacion) {
        try(MPersona model = new MPersona(vInsertar)){
            long id = model.create(
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    tipoCliente,
                    estado,
                    ubicacion
            );
            if(id > 0){
                Toast.makeText(vInsertar, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                vInsertar.limpiar();
            } else {
                Toast.makeText(vInsertar, "ERROR AL GUARDAR REGISTRO MODEL", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(vInsertar, "ERROR AL GUARDAR REGISTRO Contexto", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void readUno(int id) {
        MPersona model = new MPersona(vEditar);
        Persona persona = model.readUno(id);
        if (persona != null)
         vEditar.llenarVista(persona);
        else
            Toast.makeText(vInsertar, "NO HAY INFORMACION DEL ID", Toast.LENGTH_SHORT).show();
    }
    public boolean delete(int id) {
        boolean bandera = false;
        try(MProveedor modelProveedor = new MProveedor(adapter)) {
            try(MPersona modelPersona = new MPersona(adapter)){
                Proveedor proveedor = modelProveedor.readUno(id);
                if (proveedor != null ){
                    boolean b = modelProveedor.delete(id);
                }
                if(modelPersona.delete(id)){
                    bandera =  true;
                }else {
                    Toast.makeText(vInsertar, "ERROR AL ELIMINAR", Toast.LENGTH_SHORT).show();
                    bandera = false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  bandera;
    }
    public void listarFiltro(String filtroSeleccionado) {
        MPersona model = new MPersona(vMain);
        if (!filtroSeleccionado.equals("[NINGUNO]")){
            vMain.mostrarFiltro(model.mostrarFiltro(filtroSeleccionado));
        }else{
            MPersona modelPersona = new MPersona(vMain);
            MProveedor modelProveedor = new MProveedor(vMain);
            ArrayList<Persona> personas = modelPersona.read();
            ArrayList<Proveedor> proveedors = modelProveedor.read();
            vMain.listar(personas,proveedors);
        }
    }

    public void update(int id, String nombre, String telefono, String direccion, String correo, String estado, String ubicacion) {
        boolean b = false;
        VPersonaEditar view = vEditar;
        try{
            MPersona modelPersona = new MPersona(view);
            b = modelPersona.update(
                    id,
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    estado,
                    ubicacion
            );
            if (b){
                view.mensaje("REGISTRO PERSONA MODIFICADO");
            }else{
                view.mensaje("ERROR AL MODIFICAR REGISTRO");
            }
        }catch (Exception e){
            view.mensaje("ERROR EN CONTROLLER PERSONA");
            e.printStackTrace();
        }
    }
}
