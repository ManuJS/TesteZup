package com.manu.projeto.testezup.Models;

/**
 * Created by emanu on 31/08/2016.
 */
public class Filme {

    private String tituloFilme;
    private String sinopseFilme;
    private int image; // drawable reference id

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Filme() {
    }

    public Filme(String tituloFilme, String sinopseFilme, int image) {
        this.tituloFilme = tituloFilme;
        this.sinopseFilme = sinopseFilme;
        this.image = image;
    }

    public String getTituloFilme() {
        return tituloFilme;
    }

    public void setTituloFilme(String tituloFilme) {
        this.tituloFilme = tituloFilme;
    }

    public String getSinopseFilme() {
        return sinopseFilme;
    }

    public void setSinopseFilme(String sinopseFilme) {
        this.sinopseFilme = sinopseFilme;
    }
}
