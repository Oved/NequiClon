package com.example.recargasnequi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class operadores extends AppCompatActivity {

    private String telSesion;

    private EditText celRecarga, precioRecarga;
    private Spinner operador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operadores);

        celRecarga = findViewById(R.id.numCelularARecargar);
        precioRecarga = findViewById(R.id.valorARecargar);
        operador = findViewById(R.id.spinnerOperadores);

        String[] opcionesOperador = {"Claro", "Movistar", "WOM", "Flash Mobile", "Avantel", "Tigo", "Virgin", "ETB"};

        //Conecto el arreglo con el spinner del xml
        ArrayAdapter<String> conexAlSpinner = new ArrayAdapter<String>(this, R.layout.spinner_item_estilo, opcionesOperador);
        operador.setAdapter(conexAlSpinner);
    }


    //Método del botón recarga
    public void enviarRecarga(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String telefonorecarga = celRecarga.getText().toString();
        String valorrecargado = precioRecarga.getText().toString();
        telSesion = getIntent().getStringExtra("cel");
        String telefonoSesion=telSesion;
        
        String seleccionDeOperador = operador.getSelectedItem().toString();

        Cursor saldoInicial = BaseDeDatos.rawQuery("Select saldo from usuarios where phone= " + telSesion, null);

        saldoInicial.moveToFirst();
        String capturaSaldoInicial = saldoInicial.getString(0);
        int saldoParseado = Integer.parseInt(capturaSaldoInicial);

        
        if (!telefonorecarga.isEmpty() && telefonorecarga.length()>=10 && !valorrecargado.isEmpty()) {

            int enteroRecarga= Integer.parseInt(valorrecargado);

            if (!(saldoParseado<enteroRecarga)) {

                if (enteroRecarga >= 1000) {
                    ContentValues registroMovimientos = new ContentValues();
                    registroMovimientos.put("telefonorecarga", telefonorecarga);
                    registroMovimientos.put("valorrecargado", enteroRecarga);
                    registroMovimientos.put("operador", seleccionDeOperador);
                    registroMovimientos.put("phone", telefonoSesion);

                    BaseDeDatos.insert("registrosmovimientos", null, registroMovimientos);

                    celRecarga.setText("");
                    precioRecarga.setText("");

                    int saldoNuevo = saldoParseado - enteroRecarga;

                    ContentValues saldoActualizado = new ContentValues();
                    saldoActualizado.put("saldo", saldoNuevo);

                    String saldoString = String.valueOf(saldoNuevo);

                    int ActualizaSaldo = BaseDeDatos.update("usuarios", saldoActualizado, "phone=" + telSesion, null);
                    BaseDeDatos.close();

                    Toast.makeText(this, "Recargar Exitosa", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(this, recargasOperador.class);
                    intent.putExtra("cel", telSesion);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(this, "Valor a recargar inválido", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}