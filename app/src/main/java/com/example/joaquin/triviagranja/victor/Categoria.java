package com.example.joaquin.triviagranja.victor;

/**
 * Created by victor on 27/08/2016.
 */
public class Categoria {
    private String nombre, texto, imgp, imgs, audio;
    private Long rowid;

    public Categoria(String nombre, String texto, String audio, String imgp, String imps, Long rowid) {
        this.nombre = nombre;
        this.texto = texto;
        this.audio = audio;
        this.imgp = imgp;
        this.imgs = imps;
        this.rowid = rowid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImgp() {
        return imgp;
    }

    public void setImgp(String imgp) {
        this.imgp = imgp;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }
}
