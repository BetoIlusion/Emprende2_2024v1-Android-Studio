package com.app.emprende2_2024.view.VProveedor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProveedor.CProveedor;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MProveedor.MProveedor;
import com.app.emprende2_2024.view.VPersona.VPersonaMain;

public class VProveedorEditar extends AppCompatActivity {
    EditText etNombre, etNIT, etTelefono, etDireccion, etCorreo, etUbicacion;
    Button btnGuardar;

    public EditText getEtUbicacion() {
        return findViewById(R.id.etUbicacionProveedorEditar);
    }

    public EditText getEtNombre() {
        return findViewById(R.id.etNombreProveedorEditar);
    }

    public EditText getEtNIT() {
        return findViewById(R.id.etNitProveedorEditar);
    }

    public EditText getEtTelefono() {
        return findViewById(R.id.etTelefonoProveedorEditar);
    }

    public EditText getEtDireccion() {
        return findViewById(R.id.etDireccionProveedorEditar);
    }

    public EditText getEtCorreo() {
        return findViewById(R.id.etCorreoProveedorEditar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarProveedorEditar);
    }

    CProveedor controller = new CProveedor(this);
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproveedor_editar);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                id = Integer.parseInt(null);
            else
                id = extras.getInt("ID");
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }
        llenarVista(id);
        getBtnGuardar().setOnClickListener(v -> update());
    }

    private void update() {
        String nombre = getEtNombre().getText().toString().trim();
        String NIT = getEtNIT().getText().toString().trim();
        String telefono = getEtTelefono().getText().toString().trim();
        String direccion = getEtDireccion().getText().toString().trim();
        String correo = getEtCorreo().getText().toString().trim();
        String ubicacion = getEtUbicacion().getText().toString().trim();
        controller.update(id,nombre,NIT,telefono,direccion,correo,ubicacion);
    }

    private void llenarVista(int id) {
        controller.llenarVista(id);
    }
    public void llenarVista(MPersona persona, MProveedor proveedor) {
        String nombre = persona.getNombre().trim();
        String NIT = proveedor.getNit().trim();
        String telefono = persona.getTelefono().trim();
        String direccion = persona.getDireccion().trim();
        String correo = persona.getCorreo().trim();
        String ubicacion = persona.getUbicacion().trim();
        //int estado = persona.getEstado();
        getEtNombre().setText(nombre);
        getEtNIT().setText(NIT);
        getEtTelefono().setText(telefono);
        getEtDireccion().setText(direccion);
        getEtCorreo().setText(correo);
        //int posEs = spinnerEs(estado);
        //getSpEstado().setSelection(posEs);
        getEtUbicacion().setText(ubicacion);
    }
    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VPersonaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}