package com.app.emprende2_2024.view.VCategoria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.app.emprende2_2024.Manifest;
import com.app.emprende2_2024.PatronAdapter.CaptureInterface;
import com.app.emprende2_2024.PatronAdapter.PDFCaptureAdapter;
import com.app.emprende2_2024.PatronAdapter.PNGCaptureAdapter;
import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MProducto.MProducto;

import java.util.ArrayList;

public class VCategoriaProductoShowPDF extends AppCompatActivity {
private TextView tvTitulo;
    private TableLayout tableLayout;
    private Button btnCompartirPDF, btnCompartirPNG;

    public Button getBtnCompartirPDF() {
        return findViewById(R.id.btnCompartirPDF);
    }

    public TableLayout getTableLayout() {
        return findViewById(R.id.tlCatxProductosPDF);
    }

    public TextView getTvTitulo() {
        return findViewById(R.id.tvTituloCatalogo);
    }

    public Button getBtnCompartirPNG() {
        return findViewById(R.id.btnCompartirPNG);
    }


    int id = 0;
    CCategoria controller = new CCategoria(VCategoriaProductoShowPDF.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcategoria_producto_show_pdf);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                id = Integer.parseInt(null);
            else
                id = extras.getInt("ID");
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }
        controller.llenar(id);
        getBtnCompartirPDF().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBtnCompartirPDF().setVisibility(View.GONE);
                getBtnCompartirPNG().setVisibility(View.GONE);
                CaptureInterface captureInterface = new PDFCaptureAdapter(
                        VCategoriaProductoShowPDF.this,
                        findViewById(android.R.id.content)
                );
                captureInterface.compartirWhatsapp();
                getBtnCompartirPDF().setVisibility(View.VISIBLE);
                getBtnCompartirPNG().setVisibility(View.VISIBLE);
            }
        });
        getBtnCompartirPNG().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBtnCompartirPDF().setVisibility(View.GONE);
                getBtnCompartirPDF().setVisibility(View.GONE);
                getBtnCompartirPNG().setVisibility(View.GONE);
                CaptureInterface captureInterface = new PNGCaptureAdapter(
                        VCategoriaProductoShowPDF.this,
                        findViewById(android.R.id.content)
                );
                captureInterface.compartirWhatsapp();

                getBtnCompartirPDF().setVisibility(View.VISIBLE);
                getBtnCompartirPNG().setVisibility(View.VISIBLE);
                getBtnCompartirPDF().setVisibility(View.VISIBLE);
            }
        });
    }


    public void llenar(ArrayList<MProducto> listaProductos) {
        for (int i = 0; i < listaProductos.size(); i++) {
            TableRow fila = new TableRow(this);
            fila.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(this);
            String nombre = listaProductos.get(i).getNombre();
            tv.setText(nombre);
            fila.addView(tv);

            TextView tv2 = new TextView(this);
            float precio = listaProductos.get(i).getPrecio();
            tv2.setText("                  " + String.valueOf(precio) + " Bs.");
            fila.addView(tv2);

            TextView tv3 = new TextView(this);
            int cantidad = listaProductos.get(i).getStock().getCantidad();
            tv3.setText("  " + cantidad);

            fila.addView(tv3);
            getTableLayout().addView(fila);
        }
        String titulo = listaProductos.get(0).getCategoria().getNombre().toString();
        getTvTitulo().setText(titulo);
    }

    public void mensaje(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}