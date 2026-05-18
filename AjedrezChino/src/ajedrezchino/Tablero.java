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
        // Negras 
        casillas[0][0] = new Carro(0,0,false);
        casillas[0][1] = new Caballo(0,1,false);
        casillas[0][2] = new Elefante(0,2,false);
        casillas[0][3] = new Oficial(0,3,false);
        casillas[0][4] = new General(0,4,false);
        casillas[0][5] = new Oficial(0,5,false);
        casillas[0][6] = new Elefante(0,6,false);
        casillas[0][7] = new Caballo(0,7,false);
        casillas[0][8] = new Carro(0,8,false);
        casillas[2][1] = new Canon(2,1,false);
        casillas[2][7] = new Canon(2,7,false);
        casillas[3][0] = new Soldado(3,0,false);
        casillas[3][2] = new Soldado(3,2,false);
        casillas[3][4] = new Soldado(3,4,false);
        casillas[3][6] = new Soldado(3,6,false);
        casillas[3][8] = new Soldado(3,8,false);

        // Blancas 
        casillas[9][0] = new Carro(9,0,true);
        casillas[9][1] = new Caballo(9,1,true);
        casillas[9][2] = new Elefante(9,2,true);
        casillas[9][3] = new Oficial(9,3,true);
        casillas[9][4] = new General(9,4,true);
        casillas[9][5] = new Oficial(9,5,true);
        casillas[9][6] = new Elefante(9,6,true);
        casillas[9][7] = new Caballo(9,7,true);
        casillas[9][8] = new Carro(9,8,true);
        casillas[7][1] = new Canon(7,1,true);
        casillas[7][7] = new Canon(7,7,true);
        casillas[6][0] = new Soldado(6,0,true);
        casillas[6][2] = new Soldado(6,2,true);
        casillas[6][4] = new Soldado(6,4,true);
        casillas[6][6] = new Soldado(6,6,true);
        casillas[6][8] = new Soldado(6,8,true);
    }

    public Pieza getPieza(int fila, int col) { return casillas[fila][col]; }
    public void  setPieza(int fila, int col, Pieza p) { casillas[fila][col] = p; }
    public Pieza[][] getCasillas() { return casillas; }

    public static final int OK                   = 0;
    public static final int ERR_VACIO            = 1;
    public static final int ERR_NO_TU_TURNO      = 2;
    public static final int ERR_MOVIMIENTO       = 3;
    public static final int ERR_PIEZA_PROPIA      = 4;
    public static final int ERR_GENERALES         = 5;
    public static final int ERR_RIO               = 6;  
    public static final int ERR_FUERA_PALACIO     = 7;

    public int intentarMoverConRazon(int fo, int co, int fd, int cd, boolean turnoRojo) {
        Pieza p = casillas[fo][co];
        if (p == null)                        return ERR_VACIO;
        if (p.isEsRojo() != turnoRojo)        return ERR_NO_TU_TURNO;

        if (p instanceof Oficial || p instanceof General) {
            boolean enPalacio = estaEnPalacio(fd, cd, p.isEsRojo());
            if (!enPalacio)                   return ERR_FUERA_PALACIO;
        }

        if (!p.movimientoValido(fd, cd, casillas)) return ERR_MOVIMIENTO;

        Pieza dest = casillas[fd][cd];
        if (dest != null && dest.isEsRojo() == p.isEsRojo()) return ERR_PIEZA_PROPIA;

        if (dejaPiezasGeneralesEnfrentados(fo, co, fd, cd)) return ERR_GENERALES;

        casillas[fd][cd] = p;
        casillas[fo][co] = null;
        p.setFila(fd); p.setColumna(cd);
        return OK;
    }

    public boolean moverPieza(int fo, int co, int fd, int cd) {
        return intentarMoverConRazon(fo, co, fd, cd,
                casillas[fo][co] != null && casillas[fo][co].isEsRojo()) == OK;
    }

   public boolean estaEnPalacio(int fila, int col, boolean esRojo) {
        if (esRojo)  return fila >= 7 && fila <= 9 && col >= 3 && col <= 5;
        else         return fila >= 0 && fila <= 2 && col >= 3 && col <= 5;
    }

    private boolean dejaPiezasGeneralesEnfrentados(int fo, int co, int fd, int cd) {
        Pieza[][] copia = new Pieza[10][9];
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++)
                copia[f][c] = casillas[f][c];
        copia[fd][cd] = copia[fo][co];
        copia[fo][co] = null;

        int frojo=-1,crojo=-1,fneg=-1,cneg=-1;
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++)
                if (copia[f][c] instanceof General)
                    if (copia[f][c].isEsRojo()) { frojo=f; crojo=c; }
                    else                         { fneg=f;  cneg=c;  }

        if (frojo==-1||fneg==-1||crojo!=cneg) return false;
        int min=Math.min(frojo,fneg)+1, max=Math.max(frojo,fneg);
        for (int f=min; f<max; f++)
            if (copia[f][crojo]!=null) return false;
        return true;
    }

    public boolean esMovimientoLegal(int fo, int co, int fd, int cd) {
        Pieza p = casillas[fo][co];
        if (p == null) return false;
        if (p instanceof Oficial || p instanceof General)
            if (!estaEnPalacio(fd, cd, p.isEsRojo())) return false;
        if (!p.movimientoValido(fd, cd, casillas)) return false;
        Pieza dest = casillas[fd][cd];
        if (dest != null && dest.isEsRojo() == p.isEsRojo()) return false;
        if (dejaPiezasGeneralesEnfrentados(fo, co, fd, cd)) return false;
        return true;
    }

    public boolean hayGeneral(boolean esRojo) {
        for (int f=0; f<10; f++)
            for (int c=0; c<9; c++)
                if (casillas[f][c] instanceof General
                        && casillas[f][c].isEsRojo()==esRojo) return true;
        return false;
    }
}