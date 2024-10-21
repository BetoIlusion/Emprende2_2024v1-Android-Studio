package com.app.emprende2_2024.controller.CProveedor;

import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
import com.app.emprende2_2024.view.VProveedor.VProveedorEditar;
import com.app.emprende2_2024.view.VProveedor.VProveedorInsertar;

public class CProveedor {

    private VProveedorEditar vEditar;
    private VProveedorInsertar vInsertar;

    public CProveedor(VProveedorInsertar vProveedorInsertar) {
        this.vInsertar = vProveedorInsertar;
    }

    public CProveedor(VProveedorEditar vProveedorEditar) {
        this.vEditar = vProveedorEditar;
    }


    public void create(String nombre, String nit, String telefono, String direccion, String correo, String tipoCliente, String ubicacion) {
        modelPersona mPersona = new modelPersona(vInsertar);
        modelProveedor mProveedor = new modelProveedor(vInsertar);
        if(mPersona.create(
                nombre,
                telefono,
                direccion,
                correo,
                tipoCliente,
                ubicacion,
                1
        )){
            if(mProveedor.create(
                    nit,
                    mPersona.getId()
            )){
                vInsertar.mensaje("Proveedor Creado");
                vInsertar.limpiar();
            }else{
                vInsertar.mensaje("Error al crear Proveedor");
            }
        }else{
            vInsertar.mensaje("ERROR al Crear");
        }
    }

    public void llenarVista(int id) {
        VProveedorEditar view = vEditar;
        modelProveedor mProveedor = new modelProveedor(view);
        modelPersona mPersona = new modelPersona(view);
        view.llenarVista(mPersona.readUno(id),mProveedor.findById(id));
    }

    public void update(int id,
                       String nombre,
                       String NIT,
                       String telefono,
                       String direccion, String correo, String ubicacion) {
        modelProveedor mProveedor = new modelProveedor(vEditar);
        modelPersona mPersona = new modelPersona(vEditar);
        if(mProveedor.update(
                NIT,
                id)){
            if(mPersona.update(
                    id,
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    ubicacion)){
                vEditar.mensaje("CONTACTO ACTUALIZADO");
            }else
                vEditar.mensaje("ERROR AL ACTAULIZAR CProveedor/mPersona");
        }else{
            vEditar.mensaje("ERROR AL ACTUALIZAR mPROVEEDOR");
        }

    }
}
