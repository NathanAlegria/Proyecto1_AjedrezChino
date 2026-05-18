/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Caballo extends Pieza {

    public Caballo(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "C");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int df = nuevaFila - this.fila;
        int dc = nuevaCol - this.columna;

        // El caballo se mueve: 1 ortogonal + 1 diagonal
        if (Math.abs(df) == 2 && Math.abs(dc) == 1) {
            // Mueve 2 vertical, 1 horizontal
            int filaBloqueo = this.fila + (df > 0 ? 1 : -1);
            if (tablero[filaBloqueo][this.columna] != null) return false;
        } else if (Math.abs(df) == 1 && Math.abs(dc) == 2) {
            // Mueve 1 vertical, 2 horizontal 
            int colBloqueo = this.columna + (dc > 0 ? 1 : -1);
            if (tablero[this.fila][colBloqueo] != null) return false;
        } else {
            return false; 
        }

        // No puede caer sobre pieza propia
        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }
}
