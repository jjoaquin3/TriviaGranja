package com.example.joaquin.triviagranja.victor;

/**
 * Created by victor on 27/08/2016.
 */
public class Pregunta {
    private Long rowid;
    private String texto;
    private Long categoria;
    private int puntos;
    private String audio;

    public Pregunta(String txt, Long categoria, int puntos, String audio, Long rowid)
    {
        this.rowid = rowid;
        this.texto = txt;
        this.categoria = categoria;
        this.puntos = puntos;
        this.audio = audio;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getTexto() {
        return texto;
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
