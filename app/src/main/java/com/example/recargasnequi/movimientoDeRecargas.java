package com.example.recargasnequi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class movimientoDeRecargas extends AppCompatActivity {

    List<ListElement> elements;
    private String telSesion, capturaTelefono, capturaValor, capturaOperador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_de_recargas);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        elements = new ArrayList<>();
        ListAdapter listAdapter = new ListAdapter(elements, this);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();

    telSesion = getIntent().getStringExtra("cel");

        //Consulta de nombre
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor valor = BaseDeDatos.rawQuery("Select * from registrosmovimientos where phone=" + telSesion, null);

            while (valor.moveToNext()) {
                capturaTelefono = valor.getString(1);
                capturaValor = valor.getString(2);
                capturaOperador = valor.getString(3);

                elements.add(new ListElement("Telefono: " + capturaTelefono, "Operador: " + capturaOperador, "Valor: " + capturaValor));
            }

        }

    public void MovimientosARecargar(View view) {

        Intent irARecargar = new Intent(this, recargasOperador.class);
        irARecargar.putExtra("cel", telSesion);
        startActivity(irARecargar);
        finish();
    }

    public void MovimientosAPerfil(View view) {

        Intent irAlPerfil = new Intent(this, perfil.class);
        irAlPerfil.putExtra("cel", telSesion);
        startActivity(irAlPerfil);
        finish();
    }

    public void MovimientosAinicioHome(View view) {
        Intent irAInicio = new Intent(this, recargas.class);
        irAInicio.putExtra("cel", telSesion);
        startActivity(irAInicio);
        finish();
    }
}