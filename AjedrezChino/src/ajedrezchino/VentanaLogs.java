/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
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

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
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
        scroll.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));
        panel.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}