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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
                    // Crear una instancia de Calendar con la fecha seleccionada
                    Calendar fechaSeleccionada = Calendar.getInstance();
                    fechaSeleccionada.set(year, monthOfYear, dayOfMonth);

                    // Formatear la fecha al formato yyyy-MM-dd
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String fechaFormateada = dateFormat.format(fechaSeleccionada.getTime());

                    // Mostrar la fecha formateada en el EditText
                    getEtFechaFestejo().setText(fechaFormateada);
                }, anio, mes, dia);
        datePickerDialog.show();
    }

    public void llenarVista(ArrayList<MDescuento> descuentos) {
        for (MDescuento descuento :
                descuentos) {
            String porcentaje = String.valueOf(descuento.getPorcentaje());
            String frecuencia = String.valueOf(descuento.getFrecuencia().getFrecuencia());
            String fecha = String.valueOf(descuento.getFecha_inicio());
            if(descuento.getNombre().equals("Persona") &&
                    descuento.getFrecuencia().getTipoFrecuencia().getNombre().equals("mm")){
                getEtDesPersona().setText(porcentaje);
                getEtFrecuenciaPersona().setText(frecuencia);
            }else if (descuento.getNombre().equals("Festejo")){
                getEtDesFestejo().setText(porcentaje);
                getEtFechaFestejo().setText(fecha);
            }
        }
    }
    public void mensaje(String descuentoActualizado) {
        Toast.makeText(this, descuentoActualizado, Toast.LENGTH_LONG).show();
    }
}