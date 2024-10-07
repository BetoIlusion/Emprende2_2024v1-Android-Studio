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

public class VProductoEditar extends AppCompatActivity {

    EditText etNombre, etPrecio, etSKU, etCantidad;
    Spinner spCategoria, spProveedor;

    Button btnGuardar;

    public EditText getEtNombre() {
        return findViewById(R.id.etNombreProductoEditar);
    }

    public EditText getEtPrecio() {
        return findViewById(R.id.etPrecioProductoEditar);
    }

    public EditText getEtSKU() {
        return findViewById(R.id.etSKUProductoEditar);
    }

    public EditText getEtCantidad() {
        return findViewById(R.id.etCantidadProductoEditar);
    }

    public Spinner getSpCategoria() {
        return findViewById(R.id.spCategoriaProductoEditar);
    }

    public Spinner getSpProveedor() {
        return findViewById(R.id.spProveedorProductoEditar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarProductoEditar);
    }

    CProducto controller = new CProducto(VProductoEditar.this);
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproducto_editar);
        //OBTENIENDO EL ID DE LA LISTA
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                id = Integer.parseInt(null);
            else
                id = extras.getInt("ID");
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        llenarVista();
        getBtnGuardar().setOnClickListener(v -> update());

    }

    private void update() {
        String nombre = getEtNombre().getText().toString().trim();
        String precio = getEtPrecio().getText().toString().trim();
        String sku = getEtSKU().getText().toString().trim();
        String cantidad = getEtCantidad().toString().trim();
        Categoria categoria = (Categoria) getSpCategoria().getSelectedItem();
        Persona persona = (Persona) getSpProveedor().getSelectedItem();

        if (nombre.isEmpty() || precio.isEmpty() || sku.isEmpty() || cantidad.isEmpty()){
            mensaje("LLENE TODOS LOS ESPACIOS");
        }else{
            controller.update(
                    id,
                    nombre,
                    precio,
                    sku,
                    cantidad,
                    categoria,
                    persona
            );
        }
    }

    private void llenarVista() {
        controller.llenarSpinnersEditar();
        controller.readUno(id);
    }

    public void llenarVista(String nombre, String sku, float precio, int cantidad, Categoria categoria, Persona persona) {
        getEtNombre().setText(nombre);
        getEtSKU().setText(sku);
        getEtPrecio().setText(String.valueOf(precio));
        getEtCantidad().setText(String.valueOf(cantidad));
        getSpCategoria().setSelection(posCat(categoria));
        getSpProveedor().setSelection(posProv(persona));
    }

    private int posProv(Persona persona) {
        for (int i = 0; i < getSpProveedor().getCount(); i++) {
            Persona persona1 = (Persona) getSpProveedor().getItemAtPosition(i);
            if (persona1.getId() == persona.getId()){
                return i;
            }
        }
        return 0;
    }

    private int posCat(Categoria categoria) {
        for (int i = 0; i < getSpCategoria().getCount(); i++) {
            Categoria categoria1 = (Categoria) getSpCategoria().getItemAtPosition(i);
            if (categoria1.getId() == categoria.getId()){
                return i;
            }
        }
        return -1;
    }

    public void llenarSpinners(ArrayList<Categoria> arrayCategoria, ArrayList<Persona> arrayPersonaProveedor) {
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategoria);
        ArrayAdapter<Persona> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayPersonaProveedor);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCategoria().setAdapter(adapterCategoria);
        adapterPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpProveedor().setAdapter(adapterPersona);
    }
    public void mensaje(String errorEnController) {
        Toast.makeText(this, errorEnController, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VProductoMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }

}