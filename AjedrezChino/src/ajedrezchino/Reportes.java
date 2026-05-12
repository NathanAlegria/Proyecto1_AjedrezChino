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
        this.jugador = jugador;
        this.gestor = gestor;
        this.menuPrincipal = menuPrincipal;

        setTitle("Reportes");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = new JButton("← Atrás");
        btnAtras.setOpaque(false);
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFont(new Font("Arial", Font.BOLD, 13));
        btnAtras.addActionListener(e -> {
            menuPrincipal.volverAqui();
            dispose();
        });

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(2, 1, 10, 10));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(30, 60, 40, 60));

        JButton btnRanking = new JButton("1. Ranking de Jugadores");
        JButton btnLogs = new JButton("2. Log de mis últimos partidos");

        btnRanking.addActionListener(e -> mostrarRanking());
        btnLogs.addActionListener(e -> mostrarLogs());

        centro.add(btnRanking);
        centro.add(btnLogs);
        panel.add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarRanking() {
        new VentanaRanking(gestor, this);
        setVisible(false);
    }

    private void mostrarLogs() {
        new VentanaLogs(jugador, gestor, this);
        setVisible(false);
    }

    public void volverAqui() {
        setVisible(true);
    }
}