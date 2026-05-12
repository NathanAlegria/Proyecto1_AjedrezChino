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
public interface GestorDatos {

    void crearJugador(Jugador j);

    Jugador buscarJugador(String username);

    boolean eliminarJugador(String username);

    ArrayList<Jugador> obtenerTodosJugadores();

    void guardarLog(String username, String mensaje);

    ArrayList<String> obtenerLogsJugador(String username);
}
