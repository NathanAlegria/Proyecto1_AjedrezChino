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
public class MenuInicio extends JFrame {
    private GestorDatosImpl gestor;

    public MenuInicio() {
        gestor = new GestorDatosImpl();
        setTitle("Xiangqi - Ajedrez Chino");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JLabel titulo = new JLabel("XIANGQI - AJEDREZ CHINO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(3, 1, 10, 10));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JButton btnLogin = new JButton("1. LOG IN");
        JButton btnCrear = new JButton("2. CREAR JUGADOR");
        JButton btnSalir = new JButton("3. SALIR");

        btnLogin.addActionListener(e -> mostrarLogin());
        btnCrear.addActionListener(e -> mostrarCrearJugador());
        btnSalir.addActionListener(e -> System.exit(0));

        centro.add(btnLogin);
        centro.add(btnCrear);
        centro.add(btnSalir);
        panel.add(centro, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarLogin() {
        VentanaLogin ventana = new VentanaLogin(gestor, this);
        ventana.setVisible(true);
        setVisible(false);
    }

    private void mostrarCrearJugador() {
        VentanaCrearJugador ventana = new VentanaCrearJugador(gestor, this);
        ventana.setVisible(true);
        setVisible(false);
    }
}
