/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public class Tablero {
    private Pieza[][] casillas;

    public Tablero() {
        casillas = new Pieza[10][9];
        inicializar();
    }

    private void inicializar() {
        // --- PIEZAS NEGRAS (arriba, fila 9 visual = índice 0) ---
        casillas[0][0] = new Carro(0, 0, false);
        casillas[0][1] = new Caballo(0, 1, false);
        casillas[0][2] = new Elefante(0, 2, false);
        casillas[0][3] = new Oficial(0, 3, false);
        casillas[0][4] = new General(0, 4, false);
        casillas[0][5] = new Oficial(0, 5, false);
        casillas[0][6] = new Elefante(0, 6, false);
        casillas[0][7] = new Caballo(0, 7, false);
        casillas[0][8] = new Carro(0, 8, false);

        casillas[2][1] = new Canon(2, 1, false);
        casillas[2][7] = new Canon(2, 7, false);

        casillas[3][0] = new Soldado(3, 0, false);
        casillas[3][2] = new Soldado(3, 2, false);
        casillas[3][4] = new Soldado(3, 4, false);
        casillas[3][6] = new Soldado(3, 6, false);
        casillas[3][8] = new Soldado(3, 8, false);

        // --- PIEZAS ROJAS/BLANCAS (abajo, fila 1 visual = índice 9) ---
        casillas[9][0] = new Carro(9, 0, true);
        casillas[9][1] = new Caballo(9, 1, true);
        casillas[9][2] = new Elefante(9, 2, true);
        casillas[9][3] = new Oficial(9, 3, true);
        casillas[9][4] = new General(9, 4, true);
        casillas[9][5] = new Oficial(9, 5, true);
        casillas[9][6] = new Elefante(9, 6, true);
        casillas[9][7] = new Caballo(9, 7, true);
        casillas[9][8] = new Carro(9, 8, true);

        casillas[7][1] = new Canon(7, 1, true);
        casillas[7][7] = new Canon(7, 7, true);

        casillas[6][0] = new Soldado(6, 0, true);
        casillas[6][2] = new Soldado(6, 2, true);
        casillas[6][4] = new Soldado(6, 4, true);
        casillas[6][6] = new Soldado(6, 6, true);
        casillas[6][8] = new Soldado(6, 8, true);
    }

    public Pieza getPieza(int fila, int col) {
        return casillas[fila][col];
    }

    public void setPieza(int fila, int col, Pieza p) {
        casillas[fila][col] = p;
    }

    public Pieza[][] getCasillas() {
        return casillas;
    }

    public boolean moverPieza(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        Pieza pieza = casillas[filaOrigen][colOrigen];
        if (pieza == null) return false;
        if (!pieza.movimientoValido(filaDestino, colDestino, casillas)) return false;

        // Regla generales enfrentados para cualquier movimiento
        if (dejaPiezasGeneralesEnfrentados(filaOrigen, colOrigen, filaDestino, colDestino)) {
            return false;
        }

        casillas[filaDestino][colDestino] = pieza;
        casillas[filaOrigen][colOrigen] = null;
        pieza.setFila(filaDestino);
        pieza.setColumna(colDestino);
        return true;
    }

    // Verifica si tras el movimiento los generales quedan enfrentados
    private boolean dejaPiezasGeneralesEnfrentados(int fo, int co, int fd, int cd) {
        // Simular movimiento
        Pieza[][] copia = new Pieza[10][9];
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++)
                copia[f][c] = casillas[f][c];

        copia[fd][cd] = copia[fo][co];
        copia[fo][co] = null;

        // Buscar ambos generales
        int filaGenRojo = -1, colGenRojo = -1;
        int filaGenNeg = -1, colGenNeg = -1;

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                if (copia[f][c] instanceof General) {
                    if (copia[f][c].isEsRojo()) {
                        filaGenRojo = f; colGenRojo = c;
                    } else {
                        filaGenNeg = f; colGenNeg = c;
                    }
                }
            }
        }

        if (filaGenRojo == -1 || filaGenNeg == -1) return false;
        if (colGenRojo != colGenNeg) return false;

        // Misma columna: verificar si hay piezas entre ellos
        int filaMin = Math.min(filaGenRojo, filaGenNeg) + 1;
        int filaMax = Math.max(filaGenRojo, filaGenNeg);
        for (int f = filaMin; f < filaMax; f++) {
            if (copia[f][colGenRojo] != null) return false;
        }
        return true; // Enfrentados ilegalmente
    }

    public boolean hayGeneral(boolean esRojo) {
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++)
                if (casillas[f][c] instanceof General && casillas[f][c].isEsRojo() == esRojo)
                    return true;
        return false;
    }
    
    // Usado por VentanaJuego para calcular movimientos válidos visualmente
    public boolean esMovimientoLegal(int fo, int co, int fd, int cd) {
        Pieza p = casillas[fo][co];
        if (p == null) return false;
        if (!p.movimientoValido(fd, cd, casillas)) return false;
        if (dejaPiezasGeneralesEnfrentados(fo, co, fd, cd)) return false;
        return true;
    }
}