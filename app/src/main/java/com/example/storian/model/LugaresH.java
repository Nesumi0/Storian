package com.example.storian.model;

public class LugaresH {
    private String NombreLugar, Reseña, UrlImagenLugar;

    // Constructor vacío requerido para Firestore
    public LugaresH() {
    }

    public LugaresH(String nombreLugar, String reseña, String urlImagenLugar) {
        this.NombreLugar = nombreLugar;
        this.Reseña = reseña;
        this.UrlImagenLugar = urlImagenLugar;
    }

    public String getNombreLugar() {
        return NombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.NombreLugar = nombreLugar;
    }

    public String getReseña() {
        return Reseña;
    }

    public void setReseña(String reseña) {
        this.Reseña = reseña;
    }

    public String getUrlImagenLugar() {
        return UrlImagenLugar;
    }

    public void setUrlImagenLugar(String urlImagenLugar) {
        this.UrlImagenLugar = urlImagenLugar;
    }
}


