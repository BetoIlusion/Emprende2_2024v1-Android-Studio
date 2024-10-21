package com.app.emprende2_2024.controller.CPersona;

import android.content.Context;
import android.widget.Toast;

import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
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
            modelPersona modelPersona = new modelPersona(vMain);
            modelProveedor modelProveedor = new modelProveedor(vMain);

        ArrayList<modelPersona> personas = modelPersona.read();
        ArrayList<modelProveedor> proveedors = modelProveedor.read();

        vMain.listar(personas,proveedors);
    }
    public void create(String nombre, String telefono, String direccion, String correo, String tipoCliente, String ubicacion) {
        try{
            modelPersona mPersona = new modelPersona(vInsertar);
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
        modelPersona modelPersona = new modelPersona(vEditar);
        if (modelPersona.readUno(id) != null){
            vEditar.llenarVista(modelPersona.readUno(id));
        }else
            vEditar.mensaje("Persona No Encontrada");

//        MPersona model = new MPersona(vEditar);
//        Persona persona = model.readUno(id);
//        if (persona != null)
//         vEditar.llenarVista(persona);
//        else
//            Toast.makeText(vInsertar, "NO HAY INFORMACION DEL ID", Toast.LENGTH_SHORT).show();
    }
    public boolean delete(int id) {
        boolean bandera = false;
        modelPersona mPersona = new modelPersona(adapter);
        if (mPersona.delete(id)){
            bandera = true;
        }
        return bandera;
    }
    public void listarFiltro(String filtroSeleccionado) {
        modelPersona mPersona = new modelPersona(vMain);
        modelProveedor mProveedor = new modelProveedor(vMain);
        if(!filtroSeleccionado.equals("[NINGUNO]")){
            vMain.mostrarFiltro(mPersona.readFiltro(filtroSeleccionado));
        }else{
            ArrayList<modelPersona> personas = mPersona.read();
            ArrayList<modelProveedor> proveedors = mProveedor.read();
            vMain.listar(personas,proveedors);
        }
    }

    public void update(int id, String nombre, String telefono, String direccion, String correo, String ubicacion) {
        boolean b = false;
        modelPersona mPersona = new modelPersona(vEditar);
        if (mPersona.update(id,
                nombre,
                telefono,
                direccion,
                correo,
                ubicacion)){
            vEditar.mensaje("Persona ACTUALIZADA");
        }else{
            vEditar.mensaje("ERROR al actualizar Persona");
        }
    }
}
