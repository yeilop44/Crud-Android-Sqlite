package com.example.lds.votacionesbd;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText et1, et2, et3, et4,et5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        et4 = (EditText) findViewById(R.id.editText4);
        et5 = (EditText) findViewById(R.id.editText5);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.activity_main, menu);
        return true;
    }

    public void Ingresar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        String nombre = et2.getText().toString();
        String descripcion = et3.getText().toString();
        String unidades = et4.getText().toString();
        String valor = et5.getText().toString();


        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("nombre", nombre);
        registro.put("descripcion", descripcion);
        registro.put("unidades", unidades);
        registro.put("valor", valor);
        bd.insert("productos", null, registro);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");
        Toast.makeText(this, "Se ingreso el producto",
                Toast.LENGTH_SHORT).show();
    }

    public void Consultar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        Cursor r_consulta = bd.rawQuery(
                "select nombre,descripcion,unidades, valor from productos where codigo=" + codigo, null);
        if (r_consulta.moveToFirst()== true) {
            et2.setText(r_consulta.getString(0));
            et3.setText(r_consulta.getString(1));
            et4.setText(r_consulta.getString(2));
            et5.setText(r_consulta.getString(3));

        } else
            Toast.makeText(this, "No existe un producto con ese codigo",
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void Eliminar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        int cant = bd.delete("productos", "codigo=" + codigo, null);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");

        if (cant == 1)
            Toast.makeText(this, "Se borró el producto",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe ese codigo de producto",
                    Toast.LENGTH_SHORT).show();
    }

    public void Modificar(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "almacen", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String codigo = et1.getText().toString();
        String nombre = et2.getText().toString();
        String descripcion = et3.getText().toString();
        String unidades = et4.getText().toString();
        String valor = et5.getText().toString();


        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("descripcion", descripcion);
        registro.put("unidades", unidades);
        registro.put("valor", valor);
        int cant = bd.update("productos", registro, "codigo=" + codigo, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos del producto", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una producto con ese codigo",
                    Toast.LENGTH_SHORT).show();
    }

}