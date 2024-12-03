package com.app.emprende2_2024.PatronAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import java.io.File;

public class PDFCaptureAdapter implements CaptureInterface {
    private final PDFCapture pdfCapture;
    private View view;
    private Bitmap bitmap;
    private File file;
    public PDFCaptureAdapter(Context context, View view) {
        this.pdfCapture = new PDFCapture(context);
        this.view = view;
    }

    @Override
    public void compartirWhatsapp() {
        this.bitmap = pdfCapture.captureScreen(view);
        this.file = pdfCapture.saveCapture(bitmap);
        pdfCapture.shareCapture(file);
    }
}
