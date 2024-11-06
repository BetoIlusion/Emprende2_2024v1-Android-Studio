package com.app.emprende2_2024.view.VPersona;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CPersona.CPersona;
import com.app.emprende2_2024.model.MPersona.MPersona;

public class VPersonaEditar extends AppCompatActivity {
    EditText etNombre, etTelefono, etDireccion, etCorreo, etLinkUbicacion;
    Button btnGuardar;

    public EditText getEtLinkUbicacion() {
        return findViewById(R.id.etUbicacionPersonaEditar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarPersonaEditar);
    }

    public void setBtnGuardar(Button btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public EditText getEtNombre() {
        return findViewById(R.id.etNombrePersonaEditar);
    }

    public void setEtNombre(EditText etNombre) {
        this.etNombre = etNombre;
    }

    public EditText getEtTelefono() {
        return findViewById(R.id.etTelefonoPersonaEditar);
    }

    public void setEtTelefono(EditText etTelefono) {
        this.etTelefono = etTelefono;
    }

    public EditText getEtDireccion() {
        return findViewById(R.id.etDireccionPersonaEditar);
    }

    public void setEtDireccion(EditText etDireccion) {
        this.etDireccion = etDireccion;
    }

    public EditText getEtCorreo() {
        return findViewById(R.id.etCorreoPersonaEditar);
    }

    public void setEtCorreo(EditText etCorreo) {
        this.etCorreo = etCorreo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private int id = 0;
    CPersona controller = new CPersona(VPersonaEditar.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpersona_editar);
        //OBTENIENDO EL ID DE LA LISTA
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
        getBtnGuardar().setOnClickListener(v -> cargar(id));

    }

    private void cargar(int id) {
        String nombre = getEtNombre().getText().toString().trim();
        String telefono = getEtTelefono().getText().toString().trim();
        String direccion = getEtDireccion().getText().toString().trim();
        String correo = getEtCorreo().getText().toString().trim();
        String ubicacion = getEtLinkUbicacion().getText().toString().trim();
        if(nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
                telefono.isEmpty() || correo.isEmpty()){
            Toast.makeText(this, "Por favor, llena todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            controller.update(
                    id,
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    ubicacion
            );
        }
    }


    private void llenarVista(int id) {
        controller.readUno(id);
    }
    public void llenarVista(MPersona persona) {
        String nombre = persona.getNombre();
        String telefono = persona.getTelefono();
        String direccion = persona.getDireccion();
        String correo = persona.getCorreo();
        String ubicacion = persona.getUbicacion();

        getEtNombre().setText(nombre);
        getEtTelefono().setText(telefono);
        getEtDireccion().setText(direccion);
        getEtCorreo().setText(correo);
        getEtLinkUbicacion().setText(ubicacion);
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