/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

/**
 *
 * @author Nathan
 */
public enum TipoPieza {

    GENERAL("General", "Rey del ejército. No puede salir del palacio.", 1000, true),
    OFICIAL("Oficial", "Guardián del palacio. Solo se mueve en diagonal.", 20, true),
    ELEFANTE("Elefante", "Defensor territorial. No puede cruzar el río.", 20, true),
    CABALLO("Caballo", "Movimiento en L. Se bloquea si hay pieza en su camino.", 40, false),
    CARRO("CarrodeGuerra", "La torre del Xiangqi. La pieza más poderosa.", 90, false),
    CANON("Canon", "Necesita una pantalla para capturar.", 45, false),
    SOLDADO("Soldado", "Avanza lento, pero gana poder al cruzar el río.", 10, false);

    private final String nombre;
    private final String descripcion;
    private final int valorStrategico;
    private final boolean restringidaPalacio;

    TipoPieza(String nombre, String descripcion,
              int valorStrategico, boolean restringidaPalacio) {
        this.nombre              = nombre;
        this.descripcion         = descripcion;
        this.valorStrategico     = valorStrategico;
        this.restringidaPalacio  = restringidaPalacio;
    }

    public String getNombre()              { return nombre; }
    public String getDescripcion()         { return descripcion; }
    public int    getValorStrategico()     { return valorStrategico; }
    public boolean isRestringidaPalacio()  { return restringidaPalacio; }

    // Devuelve el nombre del archivo de imagen según color
    public String getNombreImagen(boolean esRojo) {
        return nombre + (esRojo ? "B" : "N");
    }

    // Dado una Pieza devuelve su TipoPieza correspondiente
    public static TipoPieza desdePieza(Pieza p) {
        if (p instanceof General)  return GENERAL;
        if (p instanceof Oficial)  return OFICIAL;
        if (p instanceof Elefante) return ELEFANTE;
        if (p instanceof Caballo)  return CABALLO;
        if (p instanceof Carro)    return CARRO;
        if (p instanceof Canon)    return CANON;
        if (p instanceof Soldado)  return SOLDADO;
        return null;
    }

    // Calcula el valor total del ejército vivo en el tablero
    public static int calcularValorEjercito(boolean esRojo, Pieza[][] casillas) {
        int total = 0;
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++) {
                Pieza p = casillas[f][c];
                if (p != null && p.isEsRojo() == esRojo) {
                    TipoPieza tipo = desdePieza(p);
                    if (tipo != null) total += tipo.getValorStrategico();
                }
            }
        return total;
    }

    // Calcula el valor total de las piezas capturadas
    public static int calcularValorCapturadas(java.util.ArrayList<Pieza> muertas) {
        int total = 0;
        for (Pieza p : muertas) {
            TipoPieza tipo = desdePieza(p);
            if (tipo != null) total += tipo.getValorStrategico();
        }
        return total;
    }

    @Override
    public String toString() {
        return nombre + " (valor: " + valorStrategico + ")";
    }
}
