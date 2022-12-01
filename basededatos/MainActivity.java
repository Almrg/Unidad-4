package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText ed1, ed2, ed3, ed4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = findViewById(R.id.control);
        ed2 = findViewById(R.id.nombre);
        ed3 = findViewById(R.id.semestre);
        ed4 = findViewById(R.id.carrera);
    }

    public void altas(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nc = ed1.getText().toString();
        String n = ed2.getText().toString();
        String s = ed3.getText().toString();
        String c = ed4.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("control", nc);
        registro.put("nombre", n);
        registro.put("semestre", s);
        registro.put("carrera", c);
        //INSERTO EN LA BASE DE DATOS//
        bd.insert("alumno", null, registro);
        bd.close();
        Toast.makeText(this, "Datos registrados con exito", Toast.LENGTH_LONG).show();
        this.limpiar();

    }

    public void mlimpia(View view) {
        limpiar();
    }

    public void limpiar() {
        ed1.setText(" ");
        ed2.setText(" ");
        ed3.setText(" ");
        ed4.setText(" ");
    }

    public void busqueda(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nc = ed1.getText().toString();
        Cursor fila = bd.rawQuery("select nombre, semestre, carrera, from alumno where control =" + nc, null);
        if (fila.moveToFirst()) {
            ed2.setText(fila.getString(0));
            ed3.setText(fila.getString(1));
            ed4.setText(fila.getString(2));
        } else {
            Toast.makeText(this, "No existe ningun alumno con ese numero de control", Toast.LENGTH_SHORT).show();
            bd.close();

        }
    }

    public void bajas(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nc = ed1.getText().toString();
        int cant = bd.delete("alumno", "control=" + nc, null);
        if (cant == 1) {
            Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Alumno no encontrado", Toast.LENGTH_SHORT).show();
            bd.close();
        }
    }
}
