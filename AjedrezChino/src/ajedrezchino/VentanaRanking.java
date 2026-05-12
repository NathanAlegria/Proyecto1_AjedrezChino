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
public class VentanaRanking extends JFrame {

    public VentanaRanking(GestorDatosImpl gestor, Reportes reportes) {
        setTitle("Ranking de Jugadores");
        setSize(450, 400);
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

        ArrayList<Jugador> ranking = gestor.getRankingJugadores();
        String[] columnas = {"Posición", "Username", "Puntos"};
        Object[][] datos = new Object[ranking.size()][3];
        for (int i = 0; i < ranking.size(); i++) {
            datos[i][0] = (i + 1);
            datos[i][1] = ranking.get(i).getUsername();
            datos[i][2] = ranking.get(i).getPuntos();
        }

        JTable tabla = new JTable(datos, columnas);
        tabla.setEnabled(false);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}