/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;
import java.time.LocalDate;

/**
 *
 * @author Nathan
 */
public class Jugador extends Persona {
    private int puntos;
    private LocalDate fechaIngreso;
    private boolean activo;

    public Jugador(String username, String password) {
        super(username, password);
        this.puntos = 0;
        this.fechaIngreso = LocalDate.now();
        this.activo = true;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int cantidad) {
        this.puntos += cantidad;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return username + " | Puntos: " + puntos + " | Desde: " + fechaIngreso;
    }
}
