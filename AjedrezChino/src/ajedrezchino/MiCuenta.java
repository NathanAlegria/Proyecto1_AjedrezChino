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
public class MiCuenta extends JFrame {
    private Jugador jugador;
    private GestorDatosImpl gestor;
    private MenuPrincipal menuPrincipal;

    public MiCuenta(Jugador jugador, GestorDatosImpl gestor, MenuPrincipal menuPrincipal) {
        this.jugador = jugador;
        this.gestor = gestor;
        this.menuPrincipal = menuPrincipal;

        setTitle("Mi Cuenta - " + jugador.getUsername());
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        // Flecha de regreso
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

        // Info
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 5, 5));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel l1 = new JLabel("Username: " + jugador.getUsername());
        JLabel l2 = new JLabel("Puntos: " + jugador.getPuntos());
        JLabel l3 = new JLabel("Fecha de ingreso: " + jugador.getFechaIngreso());
        JLabel l4 = new JLabel("Activo: " + jugador.isActivo());

        for (JLabel l : new JLabel[]{l1, l2, l3, l4}) {
            l.setForeground(Color.WHITE);
            l.setFont(new Font("Arial", Font.PLAIN, 13));
            panelInfo.add(l);
        }
        panel.add(panelInfo, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 60, 20, 60));

        JButton btnCambiarPass = new JButton("Cambiar Password");
        JButton btnEliminar = new JButton("Eliminar Mi Cuenta");
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setForeground(Color.WHITE);

        btnCambiarPass.addActionListener(e -> {
            new VentanaCambiarPassword(jugador, this);
            setVisible(false);
        });
        btnEliminar.addActionListener(e -> {
            new VentanaEliminarCuenta(jugador, gestor, this, menuPrincipal);
            setVisible(false);
        });

        panelBotones.add(btnCambiarPass);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void volverAqui() {
        setVisible(true);
    }
}