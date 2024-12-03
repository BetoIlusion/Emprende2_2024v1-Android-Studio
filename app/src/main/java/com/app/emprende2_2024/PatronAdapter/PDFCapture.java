package com.app.emprende2_2024.PatronAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.app.emprende2_2024.view.VCategoria.VCategoriaProductoShowPDF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFCapture {
    private final Context context;


    public PDFCapture(Context context) {
        this.context = context;
    }


    public Bitmap captureScreen(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public File saveCapture(Bitmap bitmap) {
        File pdfPath = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "screen.pdf");
        PdfDocument pdfDocument = new PdfDocument();
        try {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            canvas.drawBitmap(bitmap, 0, 0, null);
            pdfDocument.finishPage(page);

            try (FileOutputStream fos = new FileOutputStream(pdfPath)) {
                pdfDocument.writeTo(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pdfDocument.close(); // Cerrar explícitamente el PdfDocument
        }
        return pdfPath;
    }


    public void shareCapture(File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "Whatsapp no está instalado.", Toast.LENGTH_SHORT).show();
        }
    }

}
