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
public class VentanaLogin extends JFrame {
    private GestorDatosImpl gestor;
    private JFrame ventanaAnterior;

    public VentanaLogin(GestorDatosImpl gestor, JFrame ventanaAnterior) {
        this.gestor = gestor;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Log In");
        setSize(400, 320);
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
            ventanaAnterior.setVisible(true);
            dispose();
        });

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        // Formulario
        JPanel form = new JPanel(new GridLayout(5, 1, 8, 8));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        JTextField txtUser = new JTextField();

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        JPasswordField txtPass = new JPasswordField();

        JButton btnEntrar = new JButton("ENTRAR");

        form.add(lblUser);
        form.add(txtUser);
        form.add(lblPass);
        form.add(txtPass);
        form.add(btnEntrar);
        panel.add(form, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.YELLOW);
        panel.add(lblError, BorderLayout.SOUTH);

        btnEntrar.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            Jugador j = gestor.buscarJugador(user);
            if (j != null && j.getPassword().equals(pass) && j.isActivo()) {
                dispose();
                new MenuPrincipal(j, gestor, ventanaAnterior);
            } else {
                lblError.setText("Usuario o contraseña incorrectos.");
            }
        });

        setVisible(true);
    }
}
