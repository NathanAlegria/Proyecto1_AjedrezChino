/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 *
 * @author Nathan
 */
public class GestorDatosImpl implements GestorDatos {
    private ArrayList<Jugador> jugadores;
    private HashMap<String, ArrayList<String>> logs;
    private LinkedHashMap<String, Partida> partidas; // LinkedHashMap mantiene orden de inserción

    public GestorDatosImpl() {
        jugadores = new ArrayList<>();
        logs      = new HashMap<>();
        partidas  = new LinkedHashMap<>();
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
        logs.get(username).add(0, mensaje); // más reciente primero
    }

    @Override
    public ArrayList<String> obtenerLogsJugador(String username) {
        if (logs.containsKey(username)) return logs.get(username);
        return new ArrayList<>();
    }

    @Override
    public void guardarPartida(String id, Partida partida) {
        partidas.put(id, partida);
    }

    @Override
    public Partida cargarPartida(String id) {
        return partidas.get(id);
    }

    @Override
    public boolean eliminarPartida(String id) {
        if (partidas.containsKey(id)) {
            partidas.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<String> listarPartidas() {
        // Retorna en orden de más reciente a más viejo
        ArrayList<String> lista = new ArrayList<>(partidas.keySet());
        ArrayList<String> invertida = new ArrayList<>();
        for (int i = lista.size() - 1; i >= 0; i--)
            invertida.add(lista.get(i));
        return invertida;
    }

    public boolean usernameExiste(String username) {
        return buscarJugador(username) != null;
    }

    public ArrayList<Jugador> getRankingJugadores() {
        ArrayList<Jugador> activos = new ArrayList<>();
        for (Jugador j : jugadores)
            if (j.isActivo()) activos.add(j);

        // Burbuja por puntos de mayor a menor
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