/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public final class General extends Pieza {

    public General(int fila, int columna, boolean esRojo) {
        super(fila, columna, esRojo, "G");
    }

    @Override
    public boolean movimientoValido(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int df = Math.abs(nuevaFila - this.fila);
        int dc = Math.abs(nuevaCol - this.columna);

        // Solo 1 paso ortogonal
        if (!((df == 1 && dc == 0) || (df == 0 && dc == 1))) {
            return false;
        }

        // Debe quedarse en el palacio 3x3
        if (esRojo) {
            // Palacio blanco
            if (nuevaFila < 7 || nuevaFila > 9 || nuevaCol < 3 || nuevaCol > 5) {
                return false;
            }
        } else {
            // Palacio negro
            if (nuevaFila < 0 || nuevaFila > 2 || nuevaCol < 3 || nuevaCol > 5) {
                return false;
            }
        }

        Pieza destino = tablero[nuevaFila][nuevaCol];
        if (destino != null && destino.isEsRojo() == this.esRojo) {
            return false;
        }

        return true;
    }

    // Función final: verifica la regla de generales enfrentados
    public final boolean generalesEnfrentados(int nuevaFila, int nuevaCol, Pieza[][] tablero) {
        int colEnemigo = -1;
        int filaEnemigo = -1;
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                Pieza p = tablero[f][c];
                if (p instanceof General && p.isEsRojo() != this.esRojo) {
                    colEnemigo = c;
                    filaEnemigo = f;
                }
            }
        }
        if (colEnemigo == -1) {
            return false;
        }

        if (nuevaCol == colEnemigo) {
            int filaMin = Math.min(nuevaFila, filaEnemigo) + 1;
            int filaMax = Math.max(nuevaFila, filaEnemigo);
            for (int f = filaMin; f < filaMax; f++) {
                if (tablero[f][colEnemigo] != null) {
                    return false; 
                }
            }
            return true;
        }
        return false;
    }
}
