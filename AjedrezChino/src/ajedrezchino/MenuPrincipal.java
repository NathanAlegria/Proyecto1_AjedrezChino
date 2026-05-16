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
    private JLabel lblBienvenido;

    public MenuPrincipal(Jugador jugador, GestorDatosImpl gestor, JFrame menuInicio) {
        this.jugadorActual = jugador;
        this.gestor        = gestor;
        this.menuInicio    = menuInicio;

        setTitle("Menú Principal");
        setSize(520, 460);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JPanel panelTop = new JPanel(new GridLayout(2, 1));
        panelTop.setOpaque(false);
        panelTop.setBorder(BorderFactory.createEmptyBorder(28, 0, 10, 0));

        JLabel titulo = new JLabel("XIANGQI", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(new Color(255, 220, 80));

        lblBienvenido = new JLabel(textoInfo(), SwingConstants.CENTER);
        lblBienvenido.setFont(new Font("Arial", Font.PLAIN, 14));
        lblBienvenido.setForeground(new Color(200, 200, 200));

        panelTop.add(titulo);
        panelTop.add(lblBienvenido);
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(4, 1, 0, 12));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(15, 100, 40, 100));

        JButton btnJugar    = BotonesEstilo.crearBoton("  ♟  JUGAR XIANGQI  ",  new Color(255, 220, 80));
        JButton btnCuenta   = BotonesEstilo.crearBoton("  ◈  MI CUENTA  ",      new Color(100, 180, 255));
        JButton btnReportes = BotonesEstilo.crearBoton("  ≡  REPORTES  ",       new Color(180, 130, 255));
        JButton btnLogout   = BotonesEstilo.crearBoton("  ⏻  LOG OUT  ",        new Color(200, 80, 80));

        for (JButton b : new JButton[]{btnJugar, btnCuenta, btnReportes, btnLogout})
            b.setFont(new Font("Arial", Font.BOLD, 15));

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

    private String textoInfo() {
        return "Bienvenido,  " + jugadorActual.getUsername()
             + "  •  Puntos: " + jugadorActual.getPuntos();
    }

    // Llamado al volver de una partida para refrescar puntos
    public void volverAqui() {
        lblBienvenido.setText(textoInfo());
        setVisible(true);
    }

    private void iniciarJuego() {
        java.util.ArrayList<Jugador> todos = gestor.obtenerTodosJugadores();
        java.util.ArrayList<String> opciones = new java.util.ArrayList<>();
        for (Jugador j : todos)
            if (!j.getUsername().equals(jugadorActual.getUsername()) && j.isActivo())
                opciones.add(j.getUsername());

        if (opciones.isEmpty()) {
            VentanaMensaje.mostrar(this, "No hay otros jugadores registrados.", "Sin oponente");
            return;
        }
        new VentanaSeleccionOponente(jugadorActual, opciones, gestor, this);
        setVisible(false);
    }

    private void hacerLogout() {
        menuInicio.setVisible(true);
        dispose();
    }
}