package com.app.emprende2_2024.model.MProveedor;

import com.app.emprende2_2024.model.MPersona.Persona;

public class Proveedor {
    private int id;
    private String nit;
    private int id_persona;

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", nit='" + nit + '\'' +
                ", id_persona=" + id_persona +
                '}';
    }
}
