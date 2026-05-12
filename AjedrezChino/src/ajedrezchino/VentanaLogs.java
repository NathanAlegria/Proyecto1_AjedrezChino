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
public class VentanaLogs extends JFrame {

    public VentanaLogs(Jugador jugador, GestorDatosImpl gestor, Reportes reportes) {
        setTitle("Mis últimos partidos");
        setSize(500, 420);
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
            reportes.volverAqui();
            dispose();
        });

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        ArrayList<String> logs = gestor.obtenerLogsJugador(jugador.getUsername());
        StringBuilder sb = new StringBuilder();
        if (logs.isEmpty()) {
            sb.append("No hay partidos registrados.");
        } else {
            for (String log : logs) sb.append("- ").append(log).append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setOpaque(false);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnExportar = new JButton("Exportar a .txt");
        btnExportar.addActionListener(e -> exportar(sb.toString(), jugador.getUsername()));
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(btnExportar);
        panel.add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void exportar(String contenido, String username) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(username + "_logs.txt"));
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile());
                fw.write(contenido);
                fw.close();
            } catch (IOException ex) {
                // Error silencioso
            }
        }
    }
}
