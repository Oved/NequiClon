package com.example.recargasnequi;

public class ListElement {
    public String telefono;
    public String Operador;
    public String Saldo;


    public ListElement(String telefono, String operador, String saldo) {
        this.telefono = telefono;
        this.Operador = operador;
        this.Saldo = saldo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getOperador() {
        return Operador;
    }

    public void setOperador(String operador) {
        Operador = operador;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }
}
