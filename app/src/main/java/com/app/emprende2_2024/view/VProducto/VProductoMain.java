package com.app.emprende2_2024.view.VProducto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CProducto.CProducto;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.view.VCategoria.VCategoriaMain;
import com.app.emprende2_2024.view.VNotaVenta.MainActivity;

import java.util.ArrayList;

public class VProductoMain extends AppCompatActivity {
    Button btnCategoria,btnInsertar;
    RecyclerView recyclerView;
    ListaProductoAdapter adapter;

    public RecyclerView getRecyclerView() {
        return findViewById(R.id.rvProducto);
    }

    public Button getBtnInsertar() {
        return findViewById(R.id.btnInsertarProducto);
    }
    public Button getBtnCategoria() {
        return findViewById(R.id.btnCategoriaProducto);
    }

    CProducto controller = new CProducto(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproducto_main);
        controller.read();
        getBtnCategoria().setOnClickListener(v -> VCategoria());
        getBtnInsertar().setOnClickListener(v -> VInsertar());

    }

    public void listar(ArrayList<MProducto> arrayListProductoFull) {
        adapter = new ListaProductoAdapter(arrayListProductoFull);
        try{
            getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
            getRecyclerView().setAdapter(adapter);
        }catch (Exception e){
            mensaje("ERROR EN ADAPTER");
            e.printStackTrace();
        }
    }
    private void VInsertar() {
        Intent intent = new Intent(this, VProductoInsertar.class);
        startActivity(intent);
    }

    private void VCategoria() {
        Intent intent = new Intent(this, VCategoriaMain.class);
        startActivity(intent);
    }
    public void mensaje(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}