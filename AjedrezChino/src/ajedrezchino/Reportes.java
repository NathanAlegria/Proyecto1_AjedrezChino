/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Nathan
 */
public class Reportes extends JFrame {
    private Jugador jugador;
    private GestorDatosImpl gestor;
    private MenuPrincipal menuPrincipal;

    public Reportes(Jugador jugador, GestorDatosImpl gestor, MenuPrincipal menuPrincipal) {
        this.jugador       = jugador;
        this.gestor        = gestor;
        this.menuPrincipal = menuPrincipal;

        setTitle("Reportes");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> { menuPrincipal.volverAqui(); dispose(); });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);

        JLabel titulo = new JLabel("REPORTES", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(new Color(255, 220, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(2, 1, 0, 20));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(100, 200, 180, 200));

        JButton btnRanking = BotonesEstilo.crearBoton("  🏆  Ranking de Jugadores  ", new Color(255, 200, 60));
        JButton btnLogs    = BotonesEstilo.crearBoton("  📋  Mis últimos partidos  ", new Color(150, 200, 255));
        btnRanking.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogs.setFont(new Font("Arial", Font.BOLD, 18));

        btnRanking.addActionListener(e -> { new VentanaRanking(gestor, this); setVisible(false); });
        btnLogs.addActionListener(e -> { new VentanaLogs(jugador, gestor, this); setVisible(false); });

        centro.add(btnRanking);
        centro.add(btnLogs);
        panel.add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    public void volverAqui() { setVisible(true); }
}