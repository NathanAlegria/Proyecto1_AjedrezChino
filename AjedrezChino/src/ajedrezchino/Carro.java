/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Carro extends Pieza {

    public Carro(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "R");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        // Solo horizontal o vertical
        if (nuevaFila != this.fila && nuevaCol != this.columna) {
            return false;
        }

        // Verificar que no haya piezas en el camino 
        if (!caminoLibre(this.fila, this.columna, nuevaFila, nuevaCol, tablero)) {
            return false;
        }

        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }

    // Recursividad
    private boolean caminoLibre(int filaActual, int colActual, int filaFin, int colFin, Pieza[][] tablero) {
        int df = 0, dc = 0;
        if (filaFin > filaActual) df = 1;
        else if (filaFin < filaActual) df = -1;
        if (colFin > colActual) dc = 1;
        else if (colFin < colActual) dc = -1;

        int siguienteFila = filaActual + df;
        int siguienteCol = colActual + dc;

        // Caso base
        if (siguienteFila == filaFin && siguienteCol == colFin) {
            return true;
        }

        if (tablero[siguienteFila][siguienteCol] != null) {
            return false;
        }

        return caminoLibre(siguienteFila, siguienteCol, filaFin, colFin, tablero);
    }
}