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
import com.app.emprende2_2024.model.MCategoria.Categoria;
import com.app.emprende2_2024.model.MDetalleFactura.DetalleFactura;
import com.app.emprende2_2024.model.MNotaVenta.NotaVenta;
import com.app.emprende2_2024.model.MPersona.Persona;
import com.app.emprende2_2024.model.MProducto.Producto;
import com.app.emprende2_2024.view.VDetalleNotaVenta.VDetalleNotaVentaShow;

import java.util.ArrayList;

public class VNotaVentaInsertar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner spCliente, spCategoria, spProducto;
    private EditText etCantidad, etEfectivo;
    private Button btnAñadir, btnCalcular, btnRegistrar;
    private NotaVenta factura = new NotaVenta();
    private TextView tvMontoTotal, tvCambio;
    private ListaDetalleNotaVenta detalleAdapter;
    private ArrayList<DetalleFactura> detalles = new ArrayList<>();

    public TextView getTvCambio() {
        return findViewById(R.id.tvCambio);
    }

    public TextView getTvMontoTotal() {
        return findViewById(R.id.tvMontoTotalNotaVenta);
    }

    public NotaVenta getFactura() {
        return factura;
    }

    public void setFactura(NotaVenta factura) {
        this.factura = factura;
    }



    public ArrayList<DetalleFactura> getDetalles() {
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
        llenarSpinners();
        getSpCategoria().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner sp = getSpCategoria();
                Categoria categoria = (Categoria) getSpCategoria().getSelectedItem();
                filtrarProductos(categoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getBtnAñadir().setOnClickListener(v -> añadirDetalle());
        getBtnCalcular().setOnClickListener(v -> calcularCambio());
        getBtnRegistrar().setOnClickListener(v -> registrarFactura());

    }

    private void registrarFactura() {
        Persona cliente = (Persona) getSpCliente().getSelectedItem();
        NotaVenta factura = getFactura();
        ArrayList<DetalleFactura> detalles = getDetalles();
        if(cliente != null && factura.getCambio() >= 0 && detalles.size() > 0){
            controller.craete(cliente, factura, detalles);
        }else{
            mensaje("ERROR, LLENAR TODOS LOS DATOS");
        }
    }

    private void calcularCambio() {
        String efectivo = getEtEfectivo().getText().toString().trim();
        if (Integer.parseInt(efectivo) >= getFactura().getMontoTotal()){
            controller.calcularCambio(Integer.parseInt(efectivo));
        }else{
            mensaje("ERROR, EFECTIVO MENOR AL MONTO TOTAL");
        }
    }

    private void añadirDetalle() {
        Persona persona = (Persona) getSpCliente().getSelectedItem();
        Categoria categoria = (Categoria) getSpCategoria().getSelectedItem();
        Producto producto = (Producto) getSpProducto().getSelectedItem();
        String cantidad = getEtCantidad().getText().toString().isEmpty() ? "0" :
                getEtCantidad().getText().toString().trim();
        if (persona == null || categoria == null || producto == null ){
            mensaje("FALTA DATOS EN LLENAR");
        }else
            controller.añadirDetalle(Integer.parseInt(cantidad),persona,categoria,producto);


    }

    private void filtrarProductos(Categoria categoria) {
        controller.filtrarProductos(categoria);
    }
    public void filtrarProductos(ArrayList<Producto> productos) {
        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, productos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpProducto().setAdapter(adapter);
    }

    private void llenarSpinners() {
        controller.llenarSpinners();
    }
    public void llenarSpinners(ArrayList<Persona> personas, ArrayList<Categoria> categorias, ArrayList<Producto> productos) {
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,categorias);
        ArrayAdapter<Persona> adapterPersona = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,personas);
        //ArrayAdapter<Producto> adapterProducto = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,productos);

        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCategoria().setAdapter(adapterCategoria);
        adapterPersona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpCliente().setAdapter(adapterPersona);
//        adapterProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        getSpProducto().setAdapter(adapterProducto);
    }

    public void mensaje(String errorEnController) {
        Toast.makeText(this, errorEnController, Toast.LENGTH_SHORT).show();
    }


    public void actualizar() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        detalleAdapter = new ListaDetalleNotaVenta(detalles);
        getRecyclerView().setAdapter(detalleAdapter);
        detalleAdapter.notifyDataSetChanged();
        getTvMontoTotal().setText("Total: " + getFactura().getMontoTotal());
        getRecyclerView().hasPendingAdapterUpdates();
        mensaje("ACTUALIZADO");

    }

    public void actualizarFactura() {
        float cambio = getFactura().getCambio();
        getTvCambio().setText(String.valueOf(cambio));
    }

    public void vShow(long id) {
        Intent intent = new Intent( this, VDetalleNotaVentaShow.class);
        int id_factura = (int) id;
        intent.putExtra("ID", id_factura);
        startActivity(intent);

    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}