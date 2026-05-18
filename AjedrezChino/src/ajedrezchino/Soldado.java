/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Soldado extends Pieza {

    public Soldado(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "S");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int df = nuevaFila - this.fila;
        int dc = nuevaCol - this.columna;

        boolean cruzoRio = esRojo ? (this.fila < 5) : (this.fila >= 5);

        if (!cruzoRio) {
            // Solo avanza 1 paso hacia adelante
            // Rojo avanza hacia arriba , Negro avanza hacia abajo
            int avance = esRojo ? -1 : 1;
            if (df != avance || dc != 0) return false;
        } else {
            // Puede avanzar o moverse horizontal
            int avance = esRojo ? -1 : 1;
            boolean esAvance = (df == avance && dc == 0);
            boolean esHorizontal = (df == 0 && Math.abs(dc) == 1);
            if (!esAvance && !esHorizontal) return false;
        }

        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }
}