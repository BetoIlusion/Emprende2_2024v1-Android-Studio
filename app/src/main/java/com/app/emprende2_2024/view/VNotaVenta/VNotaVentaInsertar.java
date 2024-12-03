package com.app.emprende2_2024.view.VNotaVenta;

import android.content.Intent;
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
import com.app.emprende2_2024.model.MCategoria.MCategoria;
import com.app.emprende2_2024.model.MDetalleFactura.MDetalleNotaVenta;
import com.app.emprende2_2024.model.MNotaVenta.MNotaVenta;
import com.app.emprende2_2024.model.MPersona.MPersona;
import com.app.emprende2_2024.model.MProducto.MProducto;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class VNotaVentaInsertar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spCliente, spCategoria, spProducto;
    private EditText etCantidad, etEfectivo;
    private Button btnAñadir, btnCalcular, btnRegistrar;
    private MNotaVenta notaVenta = new MNotaVenta(this);
    private TextView tvMontoTotal, tvCambio;
    private ListaDetalleNotaVenta detalleAdapter;
    private ArrayList<MDetalleNotaVenta> detalles = new ArrayList<>();

    public TextView getTvCambio() {
        return findViewById(R.id.tvCambio);
    }

    public TextView getTvMontoTotal() {
        return findViewById(R.id.tvMontoTotalNotaVenta);
    }

    public MNotaVenta getNotaVenta() {
        return notaVenta;
    }

    public ArrayList<MDetalleNotaVenta> getDetalles() {
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
        setContentView(R.layout.activity_vnota_venta_insertar);
        controller.llenarSpinners();
        getSpCategoria().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner sp = getSpCategoria();
                MCategoria categoria = (MCategoria) getSpCategoria().getSelectedItem();
                controller.filtrarProductos(categoria);
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
        MPersona cliente = (MPersona) getSpCliente().getSelectedItem();
        MNotaVenta NotaVenta = getNotaVenta();
        ArrayList<MDetalleNotaVenta> detalles = getDetalles();
        if(cliente != null && NotaVenta.getCambio() > 0 && detalles.size() > 0){
            controller.create(cliente, NotaVenta, detalles);
        }else{
            mensaje("ERROR, LLENAR TODOS LOS DATOS");
        }
    }

    private void calcularCambio() {
        String efectivo = getEtEfectivo().getText().toString().trim();
        controller.calcularCambio(Integer.parseInt(efectivo));

    }

    private void añadirDetalle() {
        MProducto producto = (MProducto) getSpProducto().getSelectedItem();
        String cantidad = getEtCantidad().getText().toString().isEmpty() ? "0" :
                getEtCantidad().getText().toString().trim();
        if ( producto == null || cantidad.isEmpty() ){
            mensaje("FALTA DATOS EN LLENAR");
        }else
            controller.añadirDetalle(Integer.parseInt(cantidad),producto);
    }


    public void filtrarProductos(ArrayList<MProducto> productos) {
        ArrayAdapter<MProducto> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpProducto().setAdapter(adapter);
    }


    public void llenarSpinners(ArrayList<MPersona> personas, ArrayList<MCategoria> categorias) {
        ArrayAdapter<MCategoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categorias);
        ArrayAdapter<MPersona> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,personas);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCategoria().setAdapter(adapterCategoria);
        adapterPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCliente().setAdapter(adapterPersona);
    }

    public void mensaje(String errorEnController) {
        Toast.makeText(this, errorEnController, Toast.LENGTH_SHORT).show();
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