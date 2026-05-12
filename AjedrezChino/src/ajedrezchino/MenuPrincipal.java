/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Nathan
 */
public class MenuPrincipal extends JFrame {
    private Jugador jugadorActual;
    private GestorDatosImpl gestor;
    private JFrame menuInicio;

    public MenuPrincipal(Jugador jugador, GestorDatosImpl gestor, JFrame menuInicio) {
        this.jugadorActual = jugador;
        this.gestor = gestor;
        this.menuInicio = menuInicio;

        setTitle("Menú Principal - " + jugador.getUsername());
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JLabel titulo = new JLabel("Bienvenido: " + jugador.getUsername(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4, 1, 10, 10));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton btnJugar   = new JButton("1. JUGAR XIANGQI");
        JButton btnCuenta  = new JButton("2. MI CUENTA");
        JButton btnReportes = new JButton("3. REPORTES");
        JButton btnLogout  = new JButton("4. LOG OUT");

        btnJugar.addActionListener(e -> iniciarJuego());

        btnCuenta.addActionListener(e -> {
            new MiCuenta(jugadorActual, gestor, this);
            setVisible(false);
        });

        btnReportes.addActionListener(e -> {
            new Reportes(jugadorActual, gestor, this);
            setVisible(false);
        });

        btnLogout.addActionListener(e -> hacerLogout());

        centro.add(btnJugar);
        centro.add(btnCuenta);
        centro.add(btnReportes);
        centro.add(btnLogout);
        panel.add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    private void iniciarJuego() {
        java.util.ArrayList<Jugador> todos = gestor.obtenerTodosJugadores();
        java.util.ArrayList<String> opciones = new java.util.ArrayList<>();
        for (Jugador j : todos) {
            if (!j.getUsername().equals(jugadorActual.getUsername()) && j.isActivo()) {
                opciones.add(j.getUsername());
            }
        }

        if (opciones.isEmpty()) {
            VentanaMensaje.mostrar(this, "No hay otros jugadores registrados para jugar.", "Sin oponente");
            return;
        }

        new VentanaSeleccionOponente(jugadorActual, opciones, gestor, this);
        setVisible(false);
    }

    private void hacerLogout() {
        menuInicio.setVisible(true);
        dispose();
    }

    public void volverAqui() {
        setVisible(true);
    }
}