package com.app.emprende2_2024.view.VProducto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProducto.CProducto;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MProveedor.MProveedor;

import java.util.ArrayList;

public class VProductoInsertar extends AppCompatActivity {

    EditText etNombre, etPrecio, etSKU, etCantidad, etMinimo;
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

    public EditText getEtMinimo() {
        return findViewById(R.id.etMinimoProductoInsertar);
    }

    CProducto controller = new CProducto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproducto_insertar);
        controller.llenarSpinners();
        getBtnGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = getEtNombre().getText().toString().trim();
                String precio = getEtPrecio().getText().toString().trim();
                String SKU = getEtSKU().getText().toString().trim();
                String cantidad = getEtCantidad().getText().toString().trim();
                String minimo = getEtMinimo().getText().toString().trim();
                MCategoria categoria = (MCategoria) getSpCategoria().getSelectedItem();
                MProveedor proveedor = (MProveedor) getSpProveedor().getSelectedItem();

                if(nombre.isEmpty() || precio.isEmpty() || SKU.isEmpty() ||
                        cantidad.isEmpty() || minimo.isEmpty()){
                    mensaje("ERROR, LLENA TODOS LOS ESPACIOS");
                }else{
                    controller.create(
                            nombre,
                            precio,
                            SKU,
                            cantidad,
                            minimo,
                            categoria,
                            proveedor
                    );
                }
            }
        });
    }
    public void llenarSpinners(ArrayList<MCategoria> arrayCategoria, ArrayList<MProveedor> arrayPersonaProveedor) {
            ArrayAdapter<MCategoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategoria);
            ArrayAdapter<MProveedor> adapterProveedor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayPersonaProveedor);

            adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            getSpCategoria().setAdapter(adapterCategoria);
            adapterProveedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            getSpProveedor().setAdapter(adapterProveedor);
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