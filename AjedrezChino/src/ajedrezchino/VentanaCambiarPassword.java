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
public class VentanaCambiarPassword extends JFrame {

    public VentanaCambiarPassword(Jugador jugador, MiCuenta miCuenta) {
        setTitle("Cambiar Password");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> {
            miCuenta.volverAqui();
            dispose();
        });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);

        JLabel titulo = new JLabel("CAMBIAR PASSWORD", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(255, 220, 80));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Caja
        JPanel caja = new JPanel(new GridLayout(5, 1, 0, 14)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        caja.setOpaque(false);
        caja.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel l1 = new JLabel("Password actual:");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField txtActual = new JPasswordField();
        txtActual.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel l2 = new JLabel("Nuevo password (5 caracteres alfanuméricos):");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField txtNuevo = new JPasswordField();
        txtNuevo.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton btnGuardar = BotonesEstilo.crearBoton("  GUARDAR  ", new Color(100, 180, 255));
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 16));

        caja.add(l1);
        caja.add(txtActual);
        caja.add(l2);
        caja.add(txtNuevo);
        caja.add(btnGuardar);

        JPanel centroCaja = new JPanel(new GridBagLayout());
        centroCaja.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 100, 0, 100);
        caja.setPreferredSize(new Dimension(560, 280));
        centroCaja.add(caja, gbc);
        panel.add(centroCaja, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(new Color(255, 120, 120));
        lblError.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(lblError);
        panel.add(sur, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            String actual = new String(txtActual.getPassword());
            String nuevo = new String(txtNuevo.getPassword());

            if (!actual.equals(jugador.getPassword())) {
                lblError.setText("Password actual incorrecto.");
                return;
            }
            String errorPass = validarPassword(nuevo);
            if (errorPass != null) {
                lblError.setText(errorPass);
                return;
            }
            jugador.setPassword(nuevo);
            lblError.setForeground(new Color(100, 255, 100));
            lblError.setText("Password cambiado con éxito.");
            Timer t = new Timer(1500, ev -> {
                miCuenta.volverAqui();
                dispose();
            });
            t.setRepeats(false);
            t.start();
        });

        setVisible(true);
    }

    private String validarPassword(String pass) {
        if (pass.length() != 5) {
            return "El password debe tener exactamente 5 caracteres.";
        }
        if (!pass.matches("[a-zA-Z0-9]+")) {
            return "Solo puede contener letras y números (alfanumérico).";
        }
        if (!pass.matches(".*[a-zA-Z].*")) {
            return "Debe contener al menos una letra.";
        }
        if (!pass.matches(".*[0-9].*")) {
            return "Debe contener al menos un número.";
        }
        return null;
    }
}
