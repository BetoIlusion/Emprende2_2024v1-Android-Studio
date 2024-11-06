package com.app.emprende2_2024.controller.CPersona;

import android.content.Context;

import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
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
       MPersona MPersona = new MPersona(vMain);
       MProveedor MProveedor = new MProveedor(vMain);
       ArrayList<MPersona> personas = MPersona.read();
       ArrayList<MProveedor> proveedors = MProveedor.read();

        vMain.listar(personas,proveedors);
    }
    public void create(String nombre, String telefono, String direccion, String correo, String tipoCliente, String ubicacion) {
        try{
            MPersona mPersona = new MPersona(vInsertar);
            if(mPersona.create(
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    tipoCliente,
                    ubicacion,
                    1
            )){
                vInsertar.limpiar();
                vInsertar.mensaje("Contacto creado");
            }else
                vInsertar.mensaje("ERROR al crear Contacto");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void readUno(int id) {
        MPersona MPersona = new MPersona(vEditar);
        if (MPersona.findById(id) != null){
            vEditar.llenarVista(MPersona.findById(id));
        }else
            vEditar.mensaje("Persona No Encontrada");
    }
    public boolean delete(int id) {
        boolean bandera = false;
        MPersona mPersona = new MPersona(adapter);
        if (mPersona.delete(id)){
            bandera = true;
        }
        return bandera;
    }
    public void listarFiltro(String filtroSeleccionado) {
        MPersona mPersona = new MPersona(vMain);
        MProveedor mProveedor = new MProveedor(vMain);
        if(!filtroSeleccionado.equals("[NINGUNO]")){
            vMain.mostrarFiltro(mPersona.readFiltro(filtroSeleccionado));
        }else{
            ArrayList<MPersona> personas = mPersona.read();
            ArrayList<MProveedor> proveedors = mProveedor.read();
            vMain.listar(personas,proveedors);
        }
    }

    public void update(int id, String nombre, String telefono, String direccion, String correo, String ubicacion) {
        boolean b = false;
        MPersona mPersona = new MPersona(vEditar);
        if (mPersona.update(id,
                nombre,
                telefono,
                direccion,
                correo,
                ubicacion,
                0)){
            vEditar.mensaje("Persona ACTUALIZADA");
        }else{
            vEditar.mensaje("ERROR al actualizar Persona");
        }
    }
}
