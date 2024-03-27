package com.example.recargasnequi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class recargas extends AppCompatActivity {

    private TextView tvUsuario, tvValorDisponible, tvTotalSaldo;
    private String telSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recargas);

    }

    @Override
    protected void onStart() {
        super.onStart();

    tvUsuario = findViewById(R.id.nom_usuario);
        tvValorDisponible = findViewById(R.id.valorDsponible);
        tvTotalSaldo = findViewById(R.id.totalsaldo);


        //recibo telefono de clase iniciosesion
        telSesion = getIntent().getStringExtra("cel");

        //Consulta de nombre
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor nom = BaseDeDatos.rawQuery("Select nombres from usuarios where phone= " + telSesion, null);
        Cursor sal = BaseDeDatos.rawQuery("Select saldo from usuarios where phone= " + telSesion, null);

        sal.moveToFirst();
        String capturaSaldo = sal.getString(0);
        tvValorDisponible.setText(capturaSaldo);
        tvTotalSaldo.setText(capturaSaldo);


        nom.moveToFirst();
        String capturaNombre = nom.getString(0);
        tvUsuario.setText(capturaNombre);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Consulta de nombre
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        Cursor sal = BaseDeDatos.rawQuery("Select saldo from usuarios where phone= " + telSesion, null);

        sal.moveToFirst();
        String capturaSaldo = sal.getString(0);
        tvValorDisponible.setText(capturaSaldo);
        tvTotalSaldo.setText(capturaSaldo);
    }

    public void HomeARecargar(View view) {

        Intent intent = new Intent(this, recargasOperador.class);
        intent.putExtra("cel", telSesion);
        startActivity(intent);
    }

    public void HomeAPerfil(View view) {

        Intent intent = new Intent(this, perfil.class);
        intent.putExtra("cel", telSesion);
        startActivity(intent);
        finish();
    }

    public void HomeAovimientos(View view) {
        Intent intent = new Intent(this, movimientoDeRecargas.class);
        intent.putExtra("cel", telSesion);
        startActivity(intent);
    }
}