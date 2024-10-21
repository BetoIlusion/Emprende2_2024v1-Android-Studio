package com.app.emprende2_2024.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Emprende2_2024.db";
    public static final String TABLE_CATEGORIA = "t_categoria";
    public static final String TABLE_PRODUCTO = "t_producto";
    public static final String TABLE_STOCK = "t_stock";
    public static final String TABLE_PERSONA = "t_persona";
    public static final String TABLE_PROVEEDOR = "t_proveedor";
    public static final String TABLE_FACTURA = "t_factura";
    public static final String TABLE_DETALLE_NOTA_VENTA = "t_detalle_factura";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CATEGORIA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "descripcion TEXT," +
                "estado INTEGER)"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PERSONA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "telefono TEXT," +
                "direccion TEXT," +
                "correo TEXT," +
                "tipo_cliente TEXT," +
                "link_ubicacion TEXT," +
                "estado TEXT)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PROVEEDOR + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nit TEXT," +
                "id_persona INTEGER," +
                "FOREIGN KEY(id_persona) REFERENCES " + TABLE_PERSONA + "(id))"
        );
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTO + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "SKU TEXT, " +
                "precio REAL, " +
                "imagen BLOB, " +
                "estado INT, " +
                "id_categoria INTEGER, " +
                "id_proveedor INTEGER, " +
                "id_stock INTEGER, " +
                "FOREIGN KEY(id_categoria) REFERENCES " + TABLE_CATEGORIA + "(id), " +
                "FOREIGN KEY(id_proveedor) REFERENCES " + TABLE_PROVEEDOR + "(id)," +
                "FOREIGN KEY(id_stock) REFERENCES " + TABLE_STOCK + "(id)" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_STOCK + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cantidad INTEGER," +
                "minimo INTEGER)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_FACTURA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_codigo INTEGER," +
                "id_persona INTEGER, " +
                "monto_total REAL," +
                "efectivo REAL," +
                "cambio REAL," +
                "fecha DATE)"
        );
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DETALLE_NOTA_VENTA + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_factura INTEGER," +
                "id_producto INTEGER," +
                "cantidad REAL," +
                "subtotal REAL," +
                "FOREIGN KEY(id_factura) REFERENCES " + TABLE_FACTURA + "(id)," +
                "FOREIGN KEY(id_producto) REFERENCES " + TABLE_PRODUCTO + "(id))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVEEDOR);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FACTURA);
        onCreate(sqLiteDatabase);


    }
}