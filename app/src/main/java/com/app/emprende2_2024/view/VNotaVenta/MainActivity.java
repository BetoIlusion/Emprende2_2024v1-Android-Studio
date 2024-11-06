package com.app.emprende2_2024.view.VNotaVenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CNotaVenta.CNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.view.VDescuento.VDescuento;
import com.app.emprende2_2024.view.VPersona.VPersonaMain;
import com.app.emprende2_2024.view.VProducto.VProductoMain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnPersona, btnProductos, btnDescuento;
    ImageButton btnCrear;
    RecyclerView recyclerView;
    ListaNotaVentaAdapter adapter;

    public Button getBtnDescuento() {
        return findViewById(R.id.btnDescuentoMain);
    }

    public RecyclerView getRecyclerView() {
        return findViewById(R.id.rvMain);
    }
    public ImageButton getBtnCrear() {
        return findViewById(R.id.btnPedidoCrear);
    }

    public Button getBtnProductos() {
        return findViewById(R.id.btnProducto);
    }

    public void setBtnProductos(Button btnProductos) {
        this.btnProductos = btnProductos;
    }

    public Button getBtnPersona() {
        return findViewById(R.id.btnFactura_Persona);
    }

    public void setBtnPersona(Button btnPersona) {
        this.btnPersona = btnPersona;
    }

    CNotaVenta controller = new CNotaVenta(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        controller.read();
        btnPersona = findViewById(R.id.btnFactura_Persona);
        btnPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VPersona();
            }
        });
        getBtnProductos().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VProducto();
            }
        });
        getBtnCrear().setOnClickListener(v -> vInsertar());
        getBtnDescuento().setOnClickListener(v -> vDescuento());
    }

    private void vDescuento() {
        Intent intent = new Intent(this, VDescuento.class);
        startActivity(intent);
    }


    private void vInsertar() {
        Intent intent = new Intent(this, VNotaVentaInsertar.class);
        startActivity(intent);
    }

    private void VProducto() {
        Intent intent = new Intent(this, VProductoMain.class);
        startActivity(intent);
    }

    private void VPersona() {
        Intent intent = new Intent(this, VPersonaMain.class);
        startActivity(intent);
    }

    public void read(ArrayList<MNotaVenta> notaVentas) {
        adapter = new ListaNotaVentaAdapter(notaVentas);
        try {
            getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
            getRecyclerView().setAdapter(adapter);
        }catch (Exception e){
            mensaje("ERROR EN RECICLERVIEW");
            e.printStackTrace();
        }
    }

    private void mensaje(String errorEnReciclerview) {
        Toast.makeText(this, errorEnReciclerview, Toast.LENGTH_LONG).show();
    }
}