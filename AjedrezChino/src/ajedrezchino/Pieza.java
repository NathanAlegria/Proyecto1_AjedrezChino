/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public abstract class Pieza {

    protected int fila;
    protected int columna;
    protected boolean esRojo;
    protected String nombre;

    public Pieza(int fila, int columna, boolean esRojo, String nombre) {
        this.fila = fila;
        this.columna = columna;
        this.esRojo = esRojo;
        this.nombre = nombre;
    }

    public abstract boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero);

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isEsRojo() {
        return esRojo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getSimbolo() {
        return esRojo ? nombre.toUpperCase() : nombre.toLowerCase();
    }
}
