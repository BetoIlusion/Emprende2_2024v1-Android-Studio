package com.app.emprende2_2024.view.VProducto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProducto.CProducto;
import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MPersona.Persona;

import java.util.ArrayList;

public class VProductoInsertar extends AppCompatActivity {

    EditText etNombre, etPrecio, etSKU, etCantidad;
    Spinner spCategoria, spProveedor;

    Button btnGuardar;

    public EditText getEtNombre() {
        return findViewById(R.id.etNombreProductoInsertar);
    }

    public EditText getEtPrecio() {
        return findViewById(R.id.etPrecioProductoInsertar);
    }

    public EditText getEtSKU() {
        return findViewById(R.id.etSKUProductoInsertar);
    }

    public EditText getEtCantidad() {
        return findViewById(R.id.etCantidadProductoInsertar);
    }

    public Spinner getSpCategoria() {
        return findViewById(R.id.spCategoriaProductoInsertar);
    }

    public Spinner getSpProveedor() {
        return findViewById(R.id.spProveedorProductoInsertar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarProductoInsertar);
    }



    CProducto controller = new CProducto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproducto_insertar);
        llenarSpinners();
        getBtnGuardar().setOnClickListener(v -> insertar());
    }

    private void llenarSpinners() {
        controller.llenarSpinners();
    }
    public void llenarSpinners(ArrayList<Categoria> arrayCategoria, ArrayList<Persona> arrayPersonaProveedor) {
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategoria);
        ArrayAdapter<Persona> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayPersonaProveedor);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCategoria().setAdapter(adapterCategoria);
        adapterPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpProveedor().setAdapter(adapterPersona);

    }
    private void insertar() {
        String nombre = getEtNombre().getText().toString().trim();
        String precio = getEtPrecio().getText().toString().trim();
        String SKU = getEtSKU().getText().toString().trim();
        String cantidad = getEtCantidad().getText().toString().trim();
        Categoria categoria = (Categoria) getSpCategoria().getSelectedItem();
        Persona proveedorPersona = (Persona) getSpProveedor().getSelectedItem();

        if(nombre.isEmpty() || precio.isEmpty() || SKU.isEmpty() ||
        cantidad.isEmpty()){
            mensaje("ERROR, LLENA TODOS LOS ESPACIOS");
        }else{
            controller.create(
                    nombre,
                    precio,
                    SKU,
                    cantidad,
                    categoria,
                    proveedorPersona
            );
        }
    }



    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void limpiar() {
        getEtNombre().setText("");
        getEtPrecio().setText("");
        getEtSKU().setText("");
        getEtCantidad().setText("");
        getSpCategoria().setSelection(0);
        getSpProveedor().setSelection(0);
    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VProductoMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}