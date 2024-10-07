package com.app.emprende2_2024.controller.CProveedor;

import android.widget.Toast;

import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
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


    public void create(String nombre, String nit, String telefono, String direccion, String correo, String tipoCliente, String estado, String ubicacion) {
        try(MProveedor modelProveedor = new MProveedor(vInsertar)){
            try(MPersona modelPersona = new MPersona(vInsertar)) {
                long id1 = modelPersona.create(
                        nombre,
                        telefono,
                        direccion,
                        correo,
                        tipoCliente,
                        estado,
                        ubicacion);
                int id_persona = (int) id1;
                long id2 = modelProveedor.create(
                        nit,
                        id_persona
                );
                if (id1 > 0 && id2 > 0){
                    Toast.makeText(vInsertar, "REGISTRO GUARDADO", Toast.LENGTH_SHORT).show();
                    vInsertar.limpiar();
                }else {
                    Toast.makeText(vInsertar, "ERROR AL GUARDAR REGISTRO MODEL", Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            Toast.makeText(vInsertar, "ERROR EN LAS SENTENCIAS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void llenarVista(int id) {
        VProveedorEditar view = vEditar;
        MProveedor modelProveedor = new MProveedor(view);
        MPersona modelPersona = new MPersona(view);
        view.llenarVista(modelPersona.readUno(id),modelProveedor.readUno(id));
    }

    public void update(int id, String nombre, String NIT, String telefono, String direccion, String correo, String estado, String ubicacion) {
        boolean b = false;
        VProveedorEditar view = vEditar;
        try(MProveedor modelProveedor = new MProveedor(view)){
            try(MPersona modelPersona = new MPersona(view)) {
                b = modelProveedor.update(
                        NIT,
                        id
                );
                if (b){
                    b = modelPersona.update(
                            id,
                            nombre,
                            telefono,
                            direccion,
                            correo,
                            estado,
                            ubicacion);
                    if (b){
                        view.mensaje("REGISTRO ACTUALIZADO");
                    }
                }else{
                    view.mensaje("ERROR EN EN CONTROLADOR");
                }

            }
        }
    }
}
