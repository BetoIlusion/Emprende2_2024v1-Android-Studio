package com.app.emprende2_2024.view.VNotaVenta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.emprende2_2024.R;
import com.app.emprende2_2024.controller.CNotaVenta.CNotaVenta;
import com.app.emprende2_2024.model.MCategoria.modelCategoria;
import com.app.emprende2_2024.model.MDetalleFactura.modelDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.modelNotaVenta;
import com.app.emprende2_2024.model.MPersona.modelPersona;
import com.app.emprende2_2024.model.MProducto.modelProducto;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class VNotaVentaInsertar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spCliente, spCategoria, spProducto;
    private EditText etCantidad, etEfectivo;
    private Button btnAñadir, btnCalcular, btnRegistrar;
    private modelNotaVenta notaVenta = new modelNotaVenta(this);
    private TextView tvMontoTotal, tvCambio;
    private ListaDetalleNotaVenta detalleAdapter;
    private ArrayList<modelDetalleNotaVenta> detalles = new ArrayList<>();

    public TextView getTvCambio() {
        return findViewById(R.id.tvCambio);
    }

    public TextView getTvMontoTotal() {
        return findViewById(R.id.tvMontoTotalNotaVenta);
    }

    public modelNotaVenta getNotaVenta() {
        return notaVenta;
    }

    public ArrayList<modelDetalleNotaVenta> getDetalles() {
        return detalles;
    }

    public Button getBtnAñadir() {
        return findViewById(R.id.btnAñadirNotaVenta);
    }

    public Button getBtnCalcular() {
        return findViewById(R.id.btnCalcular);
    }

    public Button getBtnRegistrar() {
        return findViewById(R.id.btnRegistrar);
    }

    public RecyclerView getRecyclerView() {
        return findViewById(R.id.rvDetalleNotaVenta);
    }

    public Spinner getSpCliente() {
        return findViewById(R.id.spClienteNotaVentaInsertar);
    }

    public Spinner getSpCategoria() {
        return findViewById(R.id.spCategoriaNotaVenta);
    }

    public Spinner getSpProducto() {
        return findViewById(R.id.spProductoNotaVenta);
    }

    public EditText getEtCantidad() {
        return findViewById(R.id.etCantidadNotaVenta);
    }

    public EditText getEtEfectivo() {
        return findViewById(R.id.etEfectivo);
    }

    CNotaVenta controller = new CNotaVenta(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vfactura_insertar);
        controller.llenarSpinners();
        getSpCategoria().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner sp = getSpCategoria();
                modelCategoria categoria = (modelCategoria) getSpCategoria().getSelectedItem();
                filtrarProductos(categoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getBtnAñadir().setOnClickListener(v -> añadirDetalle());
        getBtnCalcular().setOnClickListener(v -> calcularCambio());
        getBtnRegistrar().setOnClickListener(v -> registrarNotaVenta());

    }

    private void registrarNotaVenta() {
        modelPersona cliente = (modelPersona) getSpCliente().getSelectedItem();
        modelNotaVenta NotaVenta = getNotaVenta();
        ArrayList<modelDetalleNotaVenta> detalles = getDetalles();
        if(cliente != null && NotaVenta.getCambio() >= 0 && detalles.size() > 0){
            controller.craete(cliente, NotaVenta, detalles);
        }else{
            mensaje("ERROR, LLENAR TODOS LOS DATOS");
        }
    }

    private void calcularCambio() {
        String efectivo = getEtEfectivo().getText().toString().trim();
        controller.calcularCambio(Integer.parseInt(efectivo));

    }

    private void añadirDetalle() {
        modelProducto producto = (modelProducto) getSpProducto().getSelectedItem();
        String cantidad = getEtCantidad().getText().toString().isEmpty() ? "0" :
                getEtCantidad().getText().toString().trim();
        if ( producto == null || cantidad.isEmpty() ){
            mensaje("FALTA DATOS EN LLENAR");
        }else
            controller.añadirDetalle(Integer.parseInt(cantidad),producto);
    }

    private void filtrarProductos(modelCategoria categoria) {
        controller.filtrarProductos(categoria);
    }
    public void filtrarProductos(ArrayList<modelProducto> productos) {
        ArrayAdapter<modelProducto> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpProducto().setAdapter(adapter);
    }


    public void llenarSpinners(ArrayList<modelPersona> personas, ArrayList<modelCategoria> categorias) {
        ArrayAdapter<modelCategoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categorias);
        ArrayAdapter<modelPersona> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,personas);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCategoria().setAdapter(adapterCategoria);
        adapterPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCliente().setAdapter(adapterPersona);
    }

    public void mensaje(String errorEnController) {
        Toast.makeText(this, errorEnController, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessMessage(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#32CD32")); // Verde lima para éxito
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public void showErrorMessage(String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#FF4500")); // Rojo anaranjado para error
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }
    public void actualizar() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        detalleAdapter = new ListaDetalleNotaVenta(detalles);
        getRecyclerView().setAdapter(detalleAdapter);
        detalleAdapter.notifyDataSetChanged();
        getTvMontoTotal().setText("Total: " + getNotaVenta().getMonto_total());
        boolean b = getRecyclerView().hasPendingAdapterUpdates();
        mensaje("ACTUALIZADO");

    }

    public void actualizarNotaVenta() {
        float cambio = getNotaVenta().getCambio();
        getTvCambio().setText("Cambio: " + cambio);
    }

    public void vShow(long id) {
        Intent intent = new Intent( this, VDetalleNotaVentaShow.class);
        intent.putExtra("ID", (int) id);
        startActivity(intent);

    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}