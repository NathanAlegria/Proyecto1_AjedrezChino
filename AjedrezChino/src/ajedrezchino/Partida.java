/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

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

    public Partida(Jugador jugadorRojo, Jugador jugadorNegro) {
        this.jugadorRojo = jugadorRojo;
        this.jugadorNegro = jugadorNegro;
        this.tablero = new Tablero();
        this.turnoRojo = true;
        this.activa = true;
    }

    public boolean intentarMover(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        if (!activa) return false;

        Pieza pieza = tablero.getPieza(filaOrigen, colOrigen);
        if (pieza == null) return false;
        if (pieza.isEsRojo() != turnoRojo) return false;

        Pieza capturada = tablero.getPieza(filaDestino, colDestino);
        boolean movido = tablero.moverPieza(filaOrigen, colOrigen, filaDestino, colDestino);

        if (movido) {
            // Verificar si se capturó un General
            if (capturada instanceof General) {
                activa = false;
            }
            turnoRojo = !turnoRojo;
        }
        return movido;
    }

    public void retirar(boolean seretiróRojo) {
        activa = false;
        // El que no se retiró gana
    }

    public Tablero getTablero() { return tablero; }
    public boolean isTurnoRojo() { return turnoRojo; }
    public boolean isActiva() { return activa; }
    public Jugador getJugadorRojo() { return jugadorRojo; }
    public Jugador getJugadorNegro() { return jugadorNegro; }

    public Jugador getGanador(boolean seRetiroRojo) {
        if (seRetiroRojo) return jugadorNegro;
        if (!tablero.hayGeneral(false)) return jugadorRojo;
        if (!tablero.hayGeneral(true)) return jugadorNegro;
        return null;
    }
}
