package com.app.emprende2_2024.view.VDetalleNotaVenta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CDetalleNotaVenta.CDetalleNotaVenta;
import com.app.emprende2_2024.model.MDetalleFactura.modelDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.modelNotaVenta;

import java.util.ArrayList;

public class VDetalleNotaVentaShow extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView  tvMontoTotal, tvFactura, tvCliente;
    private Button btnCompartirPDF,btnCompartirUbicacion;

    public Button getBtnCompartirPDF() {
        return findViewById(R.id.btnCompartirPDF);
    }

    public Button getBtnCompartirUbicacion() {
        return findViewById(R.id.btnCompartirUbicacion);
    }

    public TableLayout getTableLayout() {
        return findViewById(R.id.tableProductos);
    }
    public TextView getTvMontoTotal() {
        return findViewById(R.id.tvMontoTotalDetalleVer);
    }

    public TextView getTvFactura() {
        return findViewById(R.id.tvNroFactura);
    }

    public TextView getTvCliente() {
        return findViewById(R.id.tvPersonaDetalleVer);
    }
    CDetalleNotaVenta controller = new CDetalleNotaVenta(this);
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdetalle_factura_ver);
//        TableRow headerRow = findViewById(R.id.tableHeader);
//        Animation blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
//        headerRow.startAnimation(blinkAnimation);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                id = Integer.parseInt(null);
            else
                id = extras.getInt("ID");
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        llenarVista();
        getBtnCompartirPDF().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.compartirPDF(id);
            }
        });
    }

    private void llenarVista() {
        controller.read(id);
    }

    public void llenarVista(modelNotaVenta NotaVenta, ArrayList<modelDetalleNotaVenta> detalles) {
        for (int i = 0; i < detalles.size(); i++) {
            TableRow fila = new TableRow(this);
            fila.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView tv = new TextView(this);
            String nombre = detalles.get(i).getProducto().getNombre();
            tv.setText(nombre);
            tv.setPadding(8, 8, 8, 8);
            fila.addView(tv);

            TextView tv2 = new TextView(this);
            float precio = detalles.get(i).getProducto().getPrecio();
            tv2.setText(String.valueOf(precio));
            tv2.setPadding(8, 8, 8, 8);
            fila.addView(tv2);

            TextView tv3 = new TextView(this);
            int cantidad = detalles.get(i).getCantidad();
            tv3.setText(String.valueOf(cantidad));
            tv3.setPadding(8, 8, 8, 8);
            fila.addView(tv3);

            TextView tv4 = new TextView(this);
            double subtotal = detalles.get(i).getSubtotal();
            tv4.setText(String.valueOf(subtotal));
            tv4.setPadding(8, 8, 8, 8);
            fila.addView(tv4);

            getTableLayout().addView(fila);
        }
        int id_nota_venta = NotaVenta.getId();
        String nombrePersona = NotaVenta.getPersona().getNombre();
        float monto_total = NotaVenta.getMonto_total();
        getTvFactura().setText("0" + String.valueOf(id_nota_venta));
        getTvCliente().setText("Señor/a.: " +  nombrePersona);
        getTvMontoTotal().setText("Monto Total: " + monto_total);
    }

    public void mensaje(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}