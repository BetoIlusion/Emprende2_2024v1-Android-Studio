package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;

public class VCategoriaInsertar extends AppCompatActivity {
    EditText etNombre, etDescripcion;
    Button btnGuardar;
    Spinner spEstado;

    MCategoria mCategoria;

    public EditText getEtNombre() {
        return findViewById(R.id.etNombreCategoriaInsertar);
    }

    public EditText getEtDescripcion() {
        return findViewById(R.id.etDescripcionCategoriaInsertar);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarCategoriaInsertar);
    }


    CCategoria controller = new CCategoria(VCategoriaInsertar.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcategoria_insertar);
        getBtnGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = getEtNombre().getText().toString().trim();
                String descripcion = getEtDescripcion().getText().toString().trim();
                if (nombre.isEmpty() || descripcion.isEmpty()){
                    mensaje("ERROR, LLENAR TODOS LOS ESPACIOS");
                }else{
                    controller.create(nombre,descripcion);
                }
            }
        });
    }
    public void limpiar() {
        getEtNombre().setText("");
        getEtDescripcion().setText("");
    }
    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VCategoriaMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }



}