package com.app.emprende2_2024.view.VProveedor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProveedor.CProveedor;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.view.VPersona.VPersonaMain;

public class VProveedorEditar extends AppCompatActivity {
    EditText etNombre, etNIT, etTelefono, etDireccion, etCorreo, etUbicacion;
    Spinner spEstado;
    Button btnGuardar;

    public EditText getEtUbicacion() {
        return findViewById(R.id.etUbicacionProveedorEditar);
    }

    public Spinner getSpEstado() {
        return findViewById(R.id.spEstadoProveedorEditar);
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
        String estado = getSpEstado().getSelectedItem().toString().trim();
        String ubicacion = getEtUbicacion().getText().toString().trim();
        controller.update(id,nombre,NIT,telefono,direccion,correo,estado,ubicacion);
    }

    private void llenarVista(int id) {
        controller.llenarVista(id);
    }
    public void llenarVista(Persona persona, Proveedor proveedor) {
        String nombre = persona.getNombre().trim();
        String NIT = proveedor.getNit().trim();
        String telefono = persona.getTelefono().trim();
        String direccion = persona.getDireccion();
        String correo = persona.getCorreo();
        String estado = persona.getEstado();
        String ubicacion = persona.getUbicacion();
        getEtNombre().setText(nombre);
        getEtNIT().setText(NIT);
        getEtTelefono().setText(telefono);
        getEtDireccion().setText(direccion);
        getEtCorreo().setText(correo);
        int posEs = spinnerEs(estado);
        getSpEstado().setSelection(posEs);
        getEtUbicacion().setText(ubicacion);
    }
    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VPersonaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}