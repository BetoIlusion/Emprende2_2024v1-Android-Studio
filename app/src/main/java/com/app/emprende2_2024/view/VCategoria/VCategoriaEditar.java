package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.Categoria;

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
        getBtnGuardar().setOnClickListener(v -> update(id));
    }

    private void update(int id) {
        String nombre = getEtNombre().getText().toString();
        String descripcion = getEtDescripcion().getText().toString();
        controller.update(id,nombre,descripcion);
    }

    private void llenarVista(int id) {
        controller.readUno(id);
    }

    public void readUno(Categoria categoria) {
        String nombre = categoria.getNombre();
        String descripcion = categoria.getDescripcion();

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