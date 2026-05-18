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
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> { reportes.volverAqui(); dispose(); });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        JLabel titulo = new JLabel("MIS ÚLTIMOS PARTIDOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(new Color(255, 220, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titulo, BorderLayout.BEFORE_FIRST_LINE);
        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        ArrayList<String> logs = gestor.obtenerLogsJugador(jugador.getUsername());
        StringBuilder sb = new StringBuilder();
        if (logs.isEmpty()) {
            sb.append("No hay partidos registrados.");
        } else {
            for (String log : logs) sb.append("- ").append(log).append("\n\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setOpaque(false);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Arial", Font.PLAIN, 15));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 130));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        panel.add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }
}