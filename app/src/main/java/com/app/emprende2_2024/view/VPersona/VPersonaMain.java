package com.app.emprende2_2024.view.VPersona;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CPersona.CPersona;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProveedor.Proveedor;
import com.app.emprende2_2024.model.MProveedor.modelProveedor;
import com.app.emprende2_2024.view.VNotaVenta.MainActivity;

import java.util.ArrayList;

public class VPersonaMain extends AppCompatActivity {
    Button btnCrear, btnFiltro;
    RecyclerView listaPersonas;
    ListaPersonasAdapter adapter;
    public Button getBtnFiltro() {
        return findViewById(R.id.btnFiltroPersona);
    }
    public RecyclerView getListaPersonas() {
        return findViewById(R.id.rvVPersona);
    }
    public Button getBtnCrear() {
        return findViewById(R.id.btnInsertarPersona);
    }

    CPersona controller = new CPersona(VPersonaMain.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpersona_main);
        //Recyclerview
        controller.listar();
        //boton NUEVO CONTACTO
        getBtnCrear().setOnClickListener(v -> VPersonaInsertar());

        getBtnFiltro().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Opciones de filtro
                String[] filtros = {"[NINGUNO]", "Cliente", "Proveedor"};

                // Crear el AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(VPersonaMain.this);
                builder.setTitle("Selecciona un filtro");

                // Mostrar lista de opciones
                builder.setItems(filtros, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aquí puedes manejar qué opción fue seleccionada
                        String filtroSeleccionado = filtros[which];
                        // Filtrar la lista del RecyclerView de acuerdo con la opción seleccionada
                        //Toast.makeText(VPersonaMain.this, filtroSeleccionado, Toast.LENGTH_SHORT).show();
                        filtrarLista(filtroSeleccionado);

                    }
                });
                // Mostrar el diálogo
                builder.show();
            }
        });
    }

    private void filtrarLista(String filtroSeleccionado) {
        controller.listarFiltro(filtroSeleccionado);
    }

    public void listar(ArrayList<modelPersona> mostrarPersona, ArrayList<modelProveedor> mostrarProveedor) {
        adapter = new ListaPersonasAdapter(mostrarPersona, mostrarProveedor);
        try {
            listaPersonas = findViewById(R.id.rvVPersona);
            listaPersonas.setLayoutManager(new LinearLayoutManager(this));
            listaPersonas.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(this, "ERROR ERROR ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private void VPersonaInsertar() {
            Intent intent = new Intent(this, VPersonaInsertar.class);
            startActivity(intent);
    }


    public void mostrarFiltro(ArrayList<modelPersona> personas) {
        ArrayList<modelPersona> listaFiltrada = personas;
        // Actualiza el adaptador del RecyclerView con la lista filtrada
        if (listaFiltrada.size() > 0)
         adapter.updateList(listaFiltrada);
        else
            Toast.makeText(this, "LISTA VACIA", Toast.LENGTH_SHORT).show();

    }
    public void onBackPressed() {
        // Inicia la actividad que deseas cuando se presiona el botón de atrás
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Opcional, para finalizar la actividad actual si no deseas volver a ella al presionar atrás en la actividad destino
    }
}