package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.view.VProducto.VProductoMain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;
import java.io.FileOutputStream;

public class VCategoriaMain extends AppCompatActivity {
    Button btnInsertar, btnCompartirCatologo;
    RecyclerView recyclerView;
    ListaCategoriasAdapter adapter;

    public RecyclerView getRecyclerView() {
        return findViewById(R.id.rvCategoria);
    }

    public Button getBtnInsertar() {
        return findViewById(R.id.btnInsertarCategoria);
    }

    public Button getBtnCompartirCatologo() {
        return findViewById(R.id.btnCompartirCatalogo);
    }

    CCategoria controller = new CCategoria(VCategoriaMain.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcategoria_main);
        getBtnInsertar().setOnClickListener(v -> Insertar());
        controller.listar();
        getBtnCompartirCatologo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.compartirCatologo();
            }
        });

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

    public void listar(ArrayList<MCategoria> mostrar) {
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

    public void guardarYCompartirTexto(String contenido) {
        try {
            // Crear un archivo .txt en la carpeta de documentos del almacenamiento externo
            File directorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "docsEmprende");
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear el directorio si no existe
            }

            // Crear el archivo con un nombre
            File archivoTexto = new File(directorio, "archivo_compartido.txt");
            FileOutputStream fos = new FileOutputStream(archivoTexto);
            fos.write(contenido.getBytes());
            fos.close();

            // Compartir el archivo usando un Intent
            Uri uri = FileProvider.getUriForFile(this, "com.app.emprende2_2024.provider", archivoTexto);
            Intent compartirIntent = new Intent(Intent.ACTION_SEND);
            compartirIntent.setType("text/plain");
            compartirIntent.putExtra(Intent.EXTRA_STREAM, uri);
            compartirIntent.setPackage("com.whatsapp"); // WhatsApp

            // Permisos temporales para leer el archivo en la otra app
            compartirIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(compartirIntent, "Compartir archivo con"));
        } catch (IOException e) {
            Log.e("GuardarYCompartirTexto", "Error al crear o compartir el archivo", e);
        }
    }

}