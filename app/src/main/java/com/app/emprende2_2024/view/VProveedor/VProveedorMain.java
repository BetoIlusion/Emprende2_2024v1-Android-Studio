package com.app.emprende2_2024.view.VProveedor;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;

public class VProveedorMain extends AppCompatActivity {
    Button btnInsertar;
    RecyclerView listaCategoria;

    public Button getBtnInsertar() {
        return findViewById(R.id.btnInsertarCategoria);
    }

    public void setBtnInsertar(Button btnInsertar) {
        this.btnInsertar = btnInsertar;
    }

    public RecyclerView getListaCategoria() {
        return findViewById(R.id.rvCategoria);
    }

    public void setListaCategoria(RecyclerView listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vproveedor_main);

    }
}