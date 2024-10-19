package com.app.emprende2_2024.view.VProveedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProveedor.CProveedor;
import com.app.emprende2_2024.view.VPersona.VPersonaInsertar;
import com.app.emprende2_2024.view.VPersona.VPersonaMain;

public class VProveedorInsertar extends AppCompatActivity {
    EditText etNombre, etTelefono, etDireccion, etCorreo, etNit, etUbicacion;
    Spinner spTipoCliente;
    Button btnGuardar;

    public EditText getEtUbicacion() {
        return findViewById(R.id.etUbicacionProveedorInsertar);
    }
    public EditText getEtNit() {
        return findViewById(R.id.etNitProveedorInsertar);
    }
    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarProveedorInsertar);
    }
    public EditText getEtNombre() {
        return findViewById(R.id.etNombreProveedorInsertar);
    }
    public EditText getEtTelefono() {
        return findViewById(R.id.etTelefonoProveedorInsertar);
    }
    public EditText getEtDireccion() {
        return findViewById(R.id.etDireccionProveedorInsertar);
    }
    public EditText getEtCorreo() {
        return findViewById(R.id.etCorreoProveedorInsertar);
    }
    public Spinner getSpTipoCliente() {
        return findViewById(R.id.spTipoClienteProveedorInsertar);
    }

    CProveedor controller = new CProveedor(VProveedorInsertar.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproveedor_insertar);
        getSpTipoCliente().setSelection(2);
        getSpTipoCliente().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1 || position == 0){
                    Intent intent = new Intent(VProveedorInsertar.this, VPersonaInsertar.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getBtnGuardar().setOnClickListener(v -> insertar());
    }

    private void insertar() {
        String nombre = getEtNombre().getText().toString().trim();
        String nit = getEtNit().getText().toString().trim();
        String telefono = getEtTelefono().getText().toString().trim();
        String direccion = getEtDireccion().getText().toString().trim();
        String correo = getEtCorreo().getText().toString().trim();
        String tipo_cliente = getSpTipoCliente().getSelectedItem().toString().trim();
        //String estado = getSpEstado().getSelectedItem().toString().trim();
        String ubicacion = getEtUbicacion().getText().toString().trim();
        if(nombre.isEmpty() || nit.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
        telefono.isEmpty() || correo.isEmpty()  || tipo_cliente.isEmpty()){
            Toast.makeText(this, "Por favor, llena todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            controller.create(
                    nombre,
                    nit,
                    telefono,
                    direccion,
                    correo,
                    tipo_cliente,
                    ubicacion
            );
        }


    }

    public void limpiar() {
        getEtNombre().setText("");
        getEtNit().setText("");
        getEtTelefono().setText("");
        getEtDireccion().setText("");
        getEtCorreo().setText("");
    }

    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VPersonaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }

    public void mensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}