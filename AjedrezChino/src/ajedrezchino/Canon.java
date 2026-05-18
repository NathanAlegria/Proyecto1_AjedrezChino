/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Canon extends Pieza {

    public Canon(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "N");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        // Solo horizontal o vertical
        if (nuevaFila != this.fila && nuevaCol != this.columna) {
            return false;
        }

        Pieza destino = tablero[nuevaFila][nuevaCol];
        int piezasEnMedio = contarPiezasEnMedio(this.fila, this.columna, nuevaFila, nuevaCol, tablero, 0);

        if (destino == null) {
            // Movimiento sin captura: camino libre
            return piezasEnMedio == 0;
        } else {
            // Captura: exactamente 1 pieza en medio
            if (piezasEnMedio == 1 && destino.isEsRojo() != this.esRojo) {
                return true;
            }
            return false;
        }
    }

    // Recursividad
    private int contarPiezasEnMedio(int filaActual, int colActual, int filaFin, int colFin, Pieza[][] tablero, int conteo) {
        int df = 0, dc = 0;
        if (filaFin > filaActual) df = 1;
        else if (filaFin < filaActual) df = -1;
        if (colFin > colActual) dc = 1;
        else if (colFin < colActual) dc = -1;

        int siguienteFila = filaActual + df;
        int siguienteCol = colActual + dc;

        // Caso base
        if (siguienteFila == filaFin && siguienteCol == colFin) {
            return conteo;
        }

        int nuevoConteo = conteo;
        if (tablero[siguienteFila][siguienteCol] != null) {
            nuevoConteo++;
        }

        return contarPiezasEnMedio(siguienteFila, siguienteCol, filaFin, colFin, tablero, nuevoConteo);
    }
}
