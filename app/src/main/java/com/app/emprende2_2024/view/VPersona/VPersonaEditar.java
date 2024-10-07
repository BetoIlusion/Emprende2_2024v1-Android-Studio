package com.app.emprende2_2024.view.VPersona;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CPersona.CPersona;
import com.app.emprende2_2024.model.MPersona.Persona;

public class VPersonaEditar extends AppCompatActivity {
    EditText etNombre, etTelefono, etDireccion, etCorreo, etLinkUbicacion;
    Spinner spEstado;
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

//    public Spinner getSpTipoCliente() {
//        return findViewById(R.id.spTipoClientePersonaEditar);
//    }

//    public void setSpTipoCliente(Spinner spTipoCliente) {
//        this.spTipoCliente = spTipoCliente;
//    }

    public Spinner getSpEstado() {
        return findViewById(R.id.spEstadoPersonaEditar);
    }

    public void setSpEstado(Spinner spEstado) {
        this.spEstado = spEstado;
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
        String estado = getSpEstado().getSelectedItem().toString().trim();
        String ubicacion = getEtLinkUbicacion().getText().toString().trim();
        if(nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
                telefono.isEmpty() || correo.isEmpty() || estado.isEmpty()){
            Toast.makeText(this, "Por favor, llena todos los campos obligatorios", Toast.LENGTH_SHORT).show();
        }else{
            controller.update(
                    id,
                    nombre,
                    telefono,
                    direccion,
                    correo,
                    estado,
                    ubicacion
            );
        }
    }


    private void llenarVista(int id) {
        controller.readUno(id);
    }
    public void llenarVista(Persona persona) {
        String nombre = persona.getNombre();
        String telefono = persona.getTelefono();
        String direccion = persona.getDireccion();
        String correo = persona.getCorreo();
        String tipo = persona.getTipo_cliente();
        String estado = persona.getEstado();
        String ubicacion = persona.getUbicacion();

        //int posTC = spinnerTC(tipo);
        int posEs = spinnerEs(estado);

        getEtNombre().setText(nombre);
        getEtTelefono().setText(telefono);
        getEtDireccion().setText(direccion);
        getEtCorreo().setText(correo);
        //getSpTipoCliente().setSelection(posTC);
        getSpEstado().setSelection(posEs);
        getEtLinkUbicacion().setText(ubicacion);
    }

    private int spinnerEs(String estado) {
        int i = 0;
        Spinner spEs = getSpEstado();
        for (int j = 0; j < spEs.getCount(); j++) {
            String s = spEs.getItemAtPosition(j).toString();
            if (s.equals(estado))
                return j;
        }
        return i;
    }

//    private int spinnerTC(String stipoCliente){
//        int i = 0;
//        Spinner spTC = getSpTipoCliente();
//        for (int j = 0; j < spTC.getCount(); j++) {
//            String s = spTC.getItemAtPosition(j).toString();
//            if (s.equals(stipoCliente))
//                return j;
//        }
//        return i;
//    }
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