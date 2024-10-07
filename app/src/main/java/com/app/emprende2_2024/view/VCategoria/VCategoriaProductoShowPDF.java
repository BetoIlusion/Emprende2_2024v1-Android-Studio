package com.app.emprende2_2024.view.VCategoria;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

//import com.app.emprende2_2024.Manifest;
import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CCategoria.CCategoria;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.model.MStock.Stock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class VCategoriaProductoShowPDF extends AppCompatActivity {

    private TableLayout tableLayout;
    private Button btnCompartirPDF;

    public Button getBtnCompartirPDF() {
        return findViewById(R.id.btnCompartirPDF);
    }

    public TableLayout getTableLayout() {
        return findViewById(R.id.tlCatxProductosPDF);
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
        llenar();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION}, 1);
//        }

        getBtnCompartirPDF().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pdfFile = generarPDF2();
                if (pdfFile != null) {
                    compartirPDF(pdfFile);  // Llamada al método para compartir el PDF
                } else {
                    Toast.makeText(VCategoriaProductoShowPDF.this, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para compartir el PDF
    private void compartirPDF(File file) {
        // Obtener el URI del archivo usando FileProvider
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

        Intent intentCompartir = new Intent(Intent.ACTION_SEND);
        intentCompartir.setType("application/pdf");
        intentCompartir.putExtra(Intent.EXTRA_STREAM, uri);
        intentCompartir.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Dar permisos temporales para leer el archivo

        startActivity(Intent.createChooser(intentCompartir, "Compartir PDF usando"));
    }



    // Método para generar el PDF
    private File generarPDF() {
        // Aquí generas el PDF y lo guardas en una ubicación
        // Como ejemplo, guardamos el PDF en la carpeta de Descargas
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "CatalogoProductos.pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            PdfDocument pdfDocument = new PdfDocument();
            // Aquí agregarías tu contenido al PDF
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawText("Catálogo de Productos", 80, 50, paint);
            pdfDocument.finishPage(page);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void generarPDF1() {
        // Obtener la vista completa que contiene el catálogo
        View catalogView = findViewById(R.id.tlCatxProductosPDF);

        // Crear un documento PDF
        PdfDocument documentoPDF = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(catalogView.getWidth(), catalogView.getHeight(), 1).create();
        PdfDocument.Page pagina = documentoPDF.startPage(pageInfo);

        // Obtener el canvas para dibujar
        Canvas canvas = pagina.getCanvas();
        catalogView.draw(canvas);  // Dibujar la vista en el canvas del PDF

        // Finalizar página
        documentoPDF.finishPage(pagina);

        // Crear archivo PDF en almacenamiento externo
        File file = new File(Environment.getExternalStorageDirectory(), "CatalogoProductos.pdf");
        try {
            documentoPDF.writeTo(new FileOutputStream(file));
            Toast.makeText(this, "PDF creado con éxito!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear el PDF.", Toast.LENGTH_SHORT).show();
        } finally {
            // Cerrar el documento para liberar recursos
            documentoPDF.close();
        }
    }
    private File generarPDF2() {
        // Crear archivo PDF en la carpeta de Descargas
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "CatalogoProductos.pdf");

        try {
            // Crear flujo de salida para escribir en el archivo
            FileOutputStream fos = new FileOutputStream(file);

            // Crear un documento PDF
            PdfDocument pdfDocument = new PdfDocument();

            // Obtener las vistas del título y la tabla
            TextView tituloCatalogo = findViewById(R.id.tvTituloCatalogo);
            TableLayout tablaProductos = findViewById(R.id.tlCatxProductosPDF);

            // Definir tamaño de la página (según la altura del título y la tabla)
            int pageWidth = Math.max(tituloCatalogo.getWidth(), tablaProductos.getWidth());
            int pageHeight = tituloCatalogo.getHeight() + tablaProductos.getHeight() + 50;  // Espacio adicional al final de la página
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();

            // Crear una nueva página del PDF
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            // Obtener el canvas del PDF
            Canvas canvas = page.getCanvas();

            // Dibujar el título en el PDF
            tituloCatalogo.draw(canvas);

            // Mover el canvas para dibujar la tabla debajo del título
            canvas.translate(0, tituloCatalogo.getHeight());

            // Dibujar la tabla de productos en el PDF
            tablaProductos.draw(canvas);

            // Finalizar la página
            pdfDocument.finishPage(page);

            // Escribir el documento PDF en el flujo de salida
            pdfDocument.writeTo(fos);

            // Cerrar el documento y el flujo de salida
            pdfDocument.close();
            fos.close();

            // Retornar el archivo generado
            return file;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void llenar() {
        controller.productos(id);
    }

    public void readProductos(ArrayList<Producto> categeriaXproductos, ArrayList<Stock> stocks) {
        for (int i = 0; i < categeriaXproductos.size(); i++) {
            TableRow fila = new TableRow(this);
            fila.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView tv = new TextView(this);
            String nombre = categeriaXproductos.get(i).getNombre();
            tv.setText(nombre);
            fila.addView(tv);

            TextView tv2 = new TextView(this);
            float precio = categeriaXproductos.get(i).getPrecio();
            tv2.setText("   " + String.valueOf(precio) + " Bs.");
            fila.addView(tv2);

            TextView tv3 = new TextView(this);
            for (int j = 0; j < stocks.size(); j++) {
                if (stocks.get(j).getId_producto() == categeriaXproductos.get(i).getId()){
                    int cantidad = stocks.get(j).getCantidad();
                    tv3.setText(String.valueOf(cantidad));
                    break;
                }
            }
            fila.addView(tv3);
            getTableLayout().addView(fila);
        }

    }
}