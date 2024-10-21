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
import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;

import java.util.ArrayList;

public class VProductoEditar extends AppCompatActivity {

    EditText etNombre, etPrecio, etSKU, etCantidad, etMinimo;
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

    public EditText getEtMinimo() {
        return findViewById(R.id.etMinimoProductoEditar);
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
        controller.llenarSpinnersEditar();
        controller.llenarVista(id);
        getBtnGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = getEtNombre().getText().toString().trim();
                String precio = getEtPrecio().getText().toString().trim();
                String sku = getEtSKU().getText().toString().trim();
                String cantidad = getEtCantidad().getText().toString().trim();
                String minimo = getEtMinimo().getText().toString().trim();
                modelCategoria categoria = (modelCategoria) getSpCategoria().getSelectedItem();
                modelProveedor proveedor = (modelProveedor) getSpProveedor().getSelectedItem();

                if (nombre.isEmpty() || precio.isEmpty() || sku.isEmpty() || cantidad.isEmpty()){
                    mensaje("LLENE TODOS LOS ESPACIOS");
                }else{
                    controller.update(
                            id,
                            nombre,
                            precio,
                            sku,
                            cantidad,
                            minimo,
                            categoria,
                            proveedor
                    );
                }
            }
        });

    }
    public void llenarVista(String nombre, String sku, float precio, int cantidad, int minimo, modelCategoria categoria, modelProveedor proveedor) {
        getEtNombre().setText(nombre);
        getEtSKU().setText(sku);
        getEtPrecio().setText(String.valueOf(precio));
        getEtCantidad().setText(String.valueOf(cantidad));
        getEtMinimo().setText(String.valueOf(minimo));
        getSpCategoria().setSelection(posCat(categoria));
        getSpProveedor().setSelection(posProv(proveedor));
    }

    private int posProv(modelProveedor proveedor) {
        for (int i = 0; i < getSpProveedor().getCount(); i++) {
            modelProveedor proveedor1 = (modelProveedor) getSpProveedor().getItemAtPosition(i);
            if (proveedor1.getId() == proveedor.getId()){
                return i;
            }
        }
        return 0;
    }

    private int posCat(modelCategoria categoria) {
        for (int i = 0; i < getSpCategoria().getCount(); i++) {
            modelCategoria categoria1 = (modelCategoria) getSpCategoria().getItemAtPosition(i);
            if (categoria1.getId() == categoria.getId()){
                return i;
            }
        }
        return -1;
    }

    public void llenarSpinners(ArrayList<modelCategoria> arrayCategoria, ArrayList<modelProveedor> arrayPersonaProveedor) {
        ArrayAdapter<modelCategoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayCategoria);
        ArrayAdapter<modelProveedor> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayPersonaProveedor);

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

    public void showSuccessMessage(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#32CD32")); // Verde lima para éxito
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public void showErrorMessage(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#FF4500")); // Rojo anaranjado para error
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public void update() {
        showSuccessMessage("Actualizado");
    }
}