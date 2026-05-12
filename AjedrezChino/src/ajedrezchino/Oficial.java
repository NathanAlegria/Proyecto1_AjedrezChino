/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Oficial extends Pieza {

    public Oficial(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "O");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int df = Math.abs(nuevaFila - this.fila);
        int dc = Math.abs(nuevaCol - this.columna);

        // Solo 1 casilla diagonal
        if (df != 1 || dc != 1) {
            return false;
        }

        // Debe quedarse en el palacio
        if (esRojo) {
            if (nuevaFila < 7 || nuevaFila > 9 || nuevaCol < 3 || nuevaCol > 5) return false;
        } else {
            if (nuevaFila < 0 || nuevaFila > 2 || nuevaCol < 3 || nuevaCol > 5) return false;
        }

        // No puede caer sobre pieza propia
        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }
}
