package com.app.emprende2_2024.view.VDescuento;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CDescuento.CDescuento;
import com.app.emprende2_2024.model.MDescuento.MDescuento;
import com.app.emprende2_2024.model.MFrecuencia.MFrecuencia;

import java.util.Calendar;

public class VDescuento extends AppCompatActivity {

    EditText etDesPersona, etDesFestejo, etFrecuenciaPersona, etFechaFestejo;
    Button btnGuardar;
    CDescuento controller = new CDescuento(VDescuento.this);

    public EditText getEtFechaFestejo() {
        return findViewById(R.id.editTextFecha);
    }

    public EditText getEtDesPersona() {
        return findViewById(R.id.etDescuentoPersona);
    }

    public EditText getEtFrecuenciaPersona() {
        return findViewById(R.id.etDescuentoFrecuenciaPersona);
    }

    public EditText getEtDesFestejo() {
        return findViewById(R.id.etDescuentoFestejo);
    }

    public Button getBtnGuardar() {
        return findViewById(R.id.btnGuardarCambiosDescuento);
    }

    private String fechaSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdescuento);
        controller.llenarVista();
        // Configurar el evento de clic para mostrar el DatePickerDialog
        getEtFechaFestejo().setOnClickListener(v -> mostrarCalendario());
        getBtnGuardar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descuentoPersona = getEtDesPersona().getText().toString().trim();
                String descuentoFestejo = getEtDesFestejo().getText().toString().trim();
                String frecuenciaPersona = getEtFrecuenciaPersona().getText().toString().trim();
                fechaSeleccionada = getEtFechaFestejo().getText().toString().trim();
                String fechaFestejo = fechaSeleccionada;
                if(descuentoPersona.isEmpty() || descuentoFestejo.isEmpty()
                        || frecuenciaPersona.isEmpty() || fechaFestejo.isEmpty()){
                    mensaje("LLENE LOS ESPACIOS EN BLANCO");
                }else{
                    controller.update(descuentoPersona, descuentoFestejo, frecuenciaPersona,
                            fechaFestejo);
                }
            }
        });
    }

    private void mostrarCalendario() {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
                    fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    getEtFechaFestejo().setText(fechaSeleccionada);
                }, anio, mes, dia);
        datePickerDialog.show();
    }

    public void llenarVista(MDescuento mDescuento, MFrecuencia mFrecuencia) {
        String descuentoPersona = String.valueOf(mDescuento.getDescuento_persona());
        String descuentoFestejo = String.valueOf(mDescuento.getDescuento_festejo());
        String frecuenciaPersona = String.valueOf(mFrecuencia.getFrecuencia_mes());
        String fechaFestejo = String.valueOf(mDescuento.getFechaFestivo());
        getEtDesPersona().setText(descuentoPersona);
        getEtDesFestejo().setText(descuentoFestejo);
        getEtFrecuenciaPersona().setText(frecuenciaPersona);
        getEtFechaFestejo().setText(fechaFestejo);
    }
    public void mensaje(String descuentoActualizado) {
        Toast.makeText(this, descuentoActualizado, Toast.LENGTH_LONG).show();
    }
}