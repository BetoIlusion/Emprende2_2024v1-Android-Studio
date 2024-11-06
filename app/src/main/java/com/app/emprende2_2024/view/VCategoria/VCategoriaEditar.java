package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;

public class VCategoriaEditar extends AppCompatActivity {

    EditText etNombre, etDescripcion;
    Button btnGuardar;
    public EditText getEtNombre() {
        return findViewById(R.id.etNombreCategoriaEditar);
    }

    public EditText getEtDescripcion() {
        return findViewById(R.id.etDescripcionCategoriaEditar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarCategoriaEditar);
    }
    int id = 0;
    CCategoria controller = new CCategoria(VCategoriaEditar.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcategoria_editar);
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
        getBtnGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = getEtNombre().getText().toString().trim();
                String descripcion = getEtDescripcion().getText().toString().trim();
                if (nombre.isEmpty() || descripcion.isEmpty()){
                    mensaje("LLENA TODOS LOS ESPACIOS");
                }else {
                    controller.update(id,nombre,descripcion);
                }

            }
        });
    }

    public void mensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void llenarVista(int id) {
        controller.readUno(id);
    }
    public void readUno(MCategoria mCategoria) {
        String nombre = mCategoria.getNombre();
        String descripcion = mCategoria.getDescripcion();
        getEtNombre().setText(nombre);
        getEtDescripcion().setText(descripcion);
    }


    public void VCategoriaMain() {
        Intent intent = new Intent(this, VCategoriaMain.class);
        startActivity(intent);;
        finish();
    }

    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VCategoriaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}