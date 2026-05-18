/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class Partida {

    private Jugador jugadorRojo;
    private Jugador jugadorNegro;
    private Tablero tablero;
    private boolean turnoRojo;
    private boolean activa;
    private int ultimoError = Tablero.OK;

    // Cementerios
    private ArrayList<Pieza> muertosBlancos = new ArrayList<>();
    private ArrayList<Pieza> muertosNegros = new ArrayList<>();

    public Partida(Jugador jugadorRojo, Jugador jugadorNegro) {
        this.jugadorRojo = jugadorRojo;
        this.jugadorNegro = jugadorNegro;
        this.tablero = new Tablero();
        this.turnoRojo = true;
        this.activa = true;
    }

    public boolean intentarMover(int fo, int co, int fd, int cd) {
        if (!activa) {
            return false;
        }
        Pieza p = tablero.getPieza(fo, co);
        if (p == null) {
            ultimoError = Tablero.ERR_VACIO;
            return false;
        }

        // Guardar pieza capturada
        Pieza capturada = tablero.getPieza(fd, cd);

        ultimoError = tablero.intentarMoverConRazon(fo, co, fd, cd, turnoRojo);
        if (ultimoError != Tablero.OK) {
            return false;
        }

        // Agregar al cementerio correcto
        if (capturada != null) {
            if (capturada.isEsRojo()) {
                muertosBlancos.add(capturada);
            } else {
                muertosNegros.add(capturada);
            }
        }

        if (!tablero.hayGeneral(!turnoRojo)) {
            activa = false;
        }
        turnoRojo = !turnoRojo;
        return true;
    }

    public int getUltimoError() {
        return ultimoError;
    }

    public void retirar(boolean b) {
        activa = false;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean isTurnoRojo() {
        return turnoRojo;
    }

    public boolean isActiva() {
        return activa;
    }

    public Jugador getJugadorRojo() {
        return jugadorRojo;
    }

    public Jugador getJugadorNegro() {
        return jugadorNegro;
    }

    public ArrayList<Pieza> getMuertosBlancos() {
        return muertosBlancos;
    }

    public ArrayList<Pieza> getMuertosNegros() {
        return muertosNegros;
    }

    public Jugador getGanador(boolean seRetiroRojo) {
        if (seRetiroRojo) {
            return jugadorNegro;
        }
        if (!tablero.hayGeneral(false)) {
            return jugadorRojo;
        }
        if (!tablero.hayGeneral(true)) {
            return jugadorNegro;
        }
        return null;
    }
}
