package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.view.VProducto.VProductoMain;

import java.util.ArrayList;


public class VCategoriaMain extends AppCompatActivity {
    Button btnInsertar;
    RecyclerView recyclerView;
    ListaCategoriasAdapter adapter;

    public RecyclerView getRecyclerView() {
        return findViewById(R.id.rvCategoria);
    }

    public Button getBtnInsertar() {
        return findViewById(R.id.btnInsertarCategoria);
    }

    CCategoria controller = new CCategoria(VCategoriaMain.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcategoria_main);
        getBtnInsertar().setOnClickListener(v -> Insertar());
        controller.listar();

    }
    private void Insertar() {
        Intent intent = new Intent(this, VCategoriaInsertar.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, VProductoMain.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }

    public void listar(ArrayList<modelCategoria> mostrar) {
        adapter = new ListaCategoriasAdapter(mostrar);
        try {
            recyclerView = findViewById(R.id.rvCategoria);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this, "ERROR AL LISTAR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

}