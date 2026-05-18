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

        JLabel titulo = new JLabel("RANKING DE JUGADORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 30));
        titulo.setForeground(new Color(255, 220, 80));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Tabla ranking
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
        tabla.setFont(new Font("Arial", Font.PLAIN, 16));
        tabla.setRowHeight(40);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tabla.getTableHeader().setBackground(new Color(40, 40, 40));
        tabla.getTableHeader().setForeground(new Color(255, 220, 80));
        tabla.setBackground(new Color(30, 30, 30));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(80, 80, 80));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setOpaque(false);
        scroll.getViewport().setBackground(new Color(30, 30, 30));
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 60, 40, 60));
        panel.add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}