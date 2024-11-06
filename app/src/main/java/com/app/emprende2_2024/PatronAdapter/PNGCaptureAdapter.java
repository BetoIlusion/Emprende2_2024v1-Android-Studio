package com.app.emprende2_2024.PatronAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import java.io.File;

public class PNGCaptureAdapter implements CaptureTarget{
    private View view;
    private Bitmap bitmap;
    private File file;
    PNGCapture pngCapture;
    public PNGCaptureAdapter(Context context,View view) {
        this.view = view;
        this.pngCapture = new PNGCapture(context);
    }

    @Override
    public void compartirWhatsapp() {
        this.bitmap = pngCapture.captureScreen(view);
        this.file = pngCapture.saveCapture(bitmap);
        pngCapture.shareCapture(file);
    }
}
