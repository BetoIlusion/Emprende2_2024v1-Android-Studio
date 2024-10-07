package com.app.emprende2_2024.view.VPersona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CPersona.CPersona;
import com.app.emprende2_2024.view.VProveedor.VProveedorInsertar;

public class VPersonaInsertar extends AppCompatActivity {
    EditText etNombre, etTelefono, etDireccion, etCorreo, etLinkUbicacion;
    Spinner spTipo, spEstado;
    Button btnGuardar;

    public EditText getEtLinkUbicacion() {
        return findViewById(R.id.etUbicacionPersonaInsertar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarPersonaInsertar);
    }

    public void setBtnGuardar(Button btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public EditText getEtNombre() {
        return findViewById(R.id.etNombrePersonaInsertar);
    }

    public void setEtNombre(EditText etNombre) {
        this.etNombre = etNombre;
    }

    public EditText getEtTelefono() {
        return findViewById(R.id.etTelefonoPersonaInsertar);
    }

    public void setEtTelefono(EditText etTelefono) {
        this.etTelefono = findViewById(R.id.etTelefonoPersonaInsertar);
    }

    public EditText getEtDireccion() {
        return findViewById(R.id.etDireccionPersonaInsertar);
    }

    public void setEtDireccion(EditText etDireccion) {
        this.etDireccion = etDireccion;
    }

    public EditText getEtCorreo() {
        return findViewById(R.id.etCorreoPersonaInsertar);
    }

    public void setEtCorreo(EditText etCorreo) {
        this.etCorreo = etCorreo;
    }

    public Spinner getSpTipo() {
        return findViewById(R.id.spTipoClientePersonaInsertar);
    }

    public void setSpTipo(Spinner spTipo) {
        this.spTipo = spTipo;
    }

    public Spinner getSpEstado() {
        return findViewById(R.id.spEstadoPersonaInsertar);
    }

    public void setSpEstado(Spinner spEstado) {
        this.spEstado = spEstado;
    }

    CPersona controller = new CPersona(VPersonaInsertar.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpersona_insertar);
        getBtnGuardar().setOnClickListener(v -> create() );
        getSpTipo().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2){
                    Intent intent = new Intent(VPersonaInsertar.this, VProveedorInsertar.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void create() {
        String nombre = getEtNombre().getText().toString();
        String telefono = getEtTelefono().getText().toString();
        String direccion = getEtDireccion().getText().toString();
        String correo = getEtCorreo().getText().toString();
        String tipo_cliente = getSpTipo().getSelectedItem().toString();
        String estado = getSpEstado().getSelectedItem().toString();
        String ubicacion = getEtLinkUbicacion().getText().toString().trim();
        controller.create(nombre,telefono,direccion,correo,tipo_cliente,estado,ubicacion);
    }

    public void limpiar(){
        getEtNombre().setText("");
        getEtTelefono().setText("");
        getEtDireccion().setText("");
        getEtCorreo().setText("");
        getSpTipo().setSelection(0);
        getSpEstado().setSelection(0);
        getEtLinkUbicacion().setText("");
    }


    @Override
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VPersonaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}