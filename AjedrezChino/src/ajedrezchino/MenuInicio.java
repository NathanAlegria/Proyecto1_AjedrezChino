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
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        // Título
        JPanel panelTitulo = new JPanel(new GridLayout(2, 1));
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JLabel titulo = new JLabel("XIANGQI", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 42));
        titulo.setForeground(new Color(255, 220, 80));

        JLabel subtitulo = new JLabel("AJEDREZ CHINO", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.BOLD, 16));
        subtitulo.setForeground(new Color(200, 180, 120));

        panelTitulo.add(titulo);
        panelTitulo.add(subtitulo);
        panel.add(panelTitulo, BorderLayout.NORTH);

        // Botones
        JPanel centro = new JPanel(new GridLayout(3, 1, 0, 14));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 100, 40, 100));

        JButton btnLogin = BotonesEstilo.crearBoton("  INICIAR SESIÓN  ", new Color(255, 220, 80));
        JButton btnCrear = BotonesEstilo.crearBoton("  CREAR JUGADOR  ", new Color(100, 210, 100));
        JButton btnSalir = BotonesEstilo.crearBoton("  SALIR  ",          new Color(200, 80, 80));

        btnLogin.setFont(new Font("Arial", Font.BOLD, 15));
        btnCrear.setFont(new Font("Arial", Font.BOLD, 15));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 15));

        btnLogin.addActionListener(e -> {
            new VentanaLogin(gestor, this);
            setVisible(false);
        });
        btnCrear.addActionListener(e -> {
            new VentanaCrearJugador(gestor, this);
            setVisible(false);
        });
        btnSalir.addActionListener(e -> System.exit(0));

        centro.add(btnLogin);
        centro.add(btnCrear);
        centro.add(btnSalir);
        panel.add(centro, BorderLayout.CENTER);

        setVisible(true);
    }
}