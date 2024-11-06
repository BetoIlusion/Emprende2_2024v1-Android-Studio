package com.app.emprende2_2024.view.VDetalleNotaVenta;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CDetalleNotaVenta.CDetalleNotaVenta;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VDetalleNotaVentaShow extends AppCompatActivity {

    private TableLayout tableLayout;
    private TextView  tvMontoTotal, tvNota, tvCliente, tvDescuento;
    private Button btnCompartirPDF,btnCompartirUbicacion;

    public TextView getTvDescuento() {
        return findViewById(R.id.tvDescuentoDetalleVer);
    }

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

    public TextView getTvNota() {
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
        setContentView(R.layout.activity_vdetalle_nota_venta_ver);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                id = Integer.parseInt(null);
            else
                id = extras.getInt("ID");
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        controller.read(id);
        getBtnCompartirPDF().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBtnCompartirPDF().setVisibility(View.GONE);
                getBtnCompartirUbicacion().setVisibility(View.GONE);
                // 1. Capturar la pantalla
                Bitmap screenshot = getScreenshot(findViewById(android.R.id.content));
                // 2. Guardar la captura
                File imagePath = saveScreenshot(screenshot);
                // 3. CaptureTarget en Whatsapp
                shareOnWhatsapp(imagePath);
                getBtnCompartirPDF().setVisibility(View.VISIBLE);
                getBtnCompartirUbicacion().setVisibility(View.VISIBLE);
            }
        });

    }


    // Método para compartir en Whatsapp
    private void shareOnWhatsapp(File imagePath) {
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", imagePath); // Reemplaza ".provider" con la autoridad de tu FileProvider en el AndroidManifest.xml
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("image/*");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Agregar este flag para permitir que Whatsapp lea el archivo

        try {
            startActivity(whatsappIntent);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "Whatsapp no está instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para guardar la captura como un archivo
    private File saveScreenshot(Bitmap bitmap) {
        File imagePath = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png"); // Usando getExternalFilesDir para mayor seguridad y privacidad
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    // Método para capturar la pantalla
    private Bitmap getScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }




    public void llenarVista(MNotaVenta NotaVenta, ArrayList<MDetalleNotaVenta> detalles) {
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
        String descricpiones = NotaVenta.getDescuentoString();
        getTvNota().setText("0" + String.valueOf(id_nota_venta));
        getTvMontoTotal().setText("Monto: " + monto_total);
        getTvCliente().setText("Señor/a.: " +  nombrePersona);
        getTvDescuento().setText("Descuentos: " + descricpiones);
    }

    public void mensaje(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}