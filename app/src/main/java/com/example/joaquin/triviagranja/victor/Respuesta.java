package com.example.joaquin.triviagranja.victor;

/**
 * Created by victor on 28/08/2016.
 */
public class Respuesta {
    private String texto;
    private int correcta;
    private Long pregunta;
    private Long rowid;

    public Respuesta(String texto, int correcta, Long pregunta, Long rowid) {
        this.texto = texto;
        this.correcta = correcta;
        this.pregunta = pregunta;
        this.rowid = rowid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int isCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public Long getPregunta() {
        return pregunta;
    }

    public void setPregunta(Long pregunta) {
        this.pregunta = pregunta;
    }

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }
}
