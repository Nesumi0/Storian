package com.example.storian.model;

public class Logros {
    public Logros(){};
    String NombreLogro, DescripcionLogro;

    public Logros(String nombreLogro, String descripcionLogro) {
        NombreLogro = nombreLogro;
        DescripcionLogro = descripcionLogro;
    }

    public String getNombreLogro() {
        return NombreLogro;
    }

    public void setNombreLogro(String nombreLogro) {
        NombreLogro = nombreLogro;
    }

    public String getDescripcionLogro() {
        return DescripcionLogro;
    }

    public void setDescripcionLogro(String descripcionLogro) {
        DescripcionLogro = descripcionLogro;
    }
}
