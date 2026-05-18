/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Elefante extends Pieza {

    public Elefante(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "E");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int df = nuevaFila - this.fila;
        int dc = nuevaCol - this.columna;

        // Debe moverse exactamente 2 en diagonal
        if (Math.abs(df) != 2 || Math.abs(dc) != 2) {
            return false;
        }

        // No puede cruzar el río
        if (esRojo && nuevaFila < 5) return false;
        if (!esRojo && nuevaFila > 4) return false;

        int filaOjo = this.fila + df / 2;
        int colOjo = this.columna + dc / 2;
        if (tablero[filaOjo][colOjo] != null) {
            return false; 
        }

        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }
}