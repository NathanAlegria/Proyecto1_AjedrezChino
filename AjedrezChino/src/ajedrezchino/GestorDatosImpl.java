/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nathan
 */

public class GestorDatosImpl implements GestorDatos {
    private ArrayList<Jugador> jugadores;
    private HashMap<String, ArrayList<String>> logs;

    public GestorDatosImpl() {
        jugadores = new ArrayList<>();
        logs      = new HashMap<>();
    }

    @Override
    public void crearJugador(Jugador j) {
        jugadores.add(j);
        logs.put(j.getUsername(), new ArrayList<>());
    }

    @Override
    public Jugador buscarJugador(String username) {
        for (Jugador j : jugadores)
            if (j.getUsername().equals(username)) return j;
        return null;
    }

    @Override
    public boolean eliminarJugador(String username) {
        Jugador j = buscarJugador(username);
        if (j != null) {
            jugadores.remove(j);
            logs.remove(username);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Jugador> obtenerTodosJugadores() {
        return jugadores;
    }

    @Override
    public void guardarLog(String username, String mensaje) {
        if (!logs.containsKey(username))
            logs.put(username, new ArrayList<>());
        logs.get(username).add(0, mensaje);
    }

    @Override
    public ArrayList<String> obtenerLogsJugador(String username) {
        if (logs.containsKey(username)) return logs.get(username);
        return new ArrayList<>();
    }

    public boolean usernameExiste(String username) {
        return buscarJugador(username) != null;
    }

    public ArrayList<Jugador> getRankingJugadores() {
        ArrayList<Jugador> activos = new ArrayList<>();
        for (Jugador j : jugadores)
            if (j.isActivo()) activos.add(j);
        for (int i = 0; i < activos.size() - 1; i++)
            for (int k = 0; k < activos.size() - 1 - i; k++)
                if (activos.get(k).getPuntos() < activos.get(k+1).getPuntos()) {
                    Jugador tmp = activos.get(k);
                    activos.set(k, activos.get(k+1));
                    activos.set(k+1, tmp);
                }
        return activos;
    }
}