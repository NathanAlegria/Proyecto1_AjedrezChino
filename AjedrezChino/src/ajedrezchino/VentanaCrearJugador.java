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
public class VentanaCrearJugador extends JFrame {
    private GestorDatosImpl gestor;
    private JFrame ventanaAnterior;

    public VentanaCrearJugador(GestorDatosImpl gestor, JFrame ventanaAnterior) {
        this.gestor = gestor;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Crear Jugador");
        setSize(400, 340);
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

        JLabel lblUser = new JLabel("Username (único):");
        lblUser.setForeground(Color.WHITE);
        JTextField txtUser = new JTextField();

        JLabel lblPass = new JLabel("Password (exactamente 5 caracteres):");
        lblPass.setForeground(Color.WHITE);
        JPasswordField txtPass = new JPasswordField();

        JButton btnCrear = new JButton("CREAR");

        form.add(lblUser);
        form.add(txtUser);
        form.add(lblPass);
        form.add(txtPass);
        form.add(btnCrear);
        panel.add(form, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.YELLOW);
        panel.add(lblError, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty()) {
                lblError.setText("El username no puede estar vacío.");
                return;
            }
            if (gestor.usernameExiste(user)) {
                lblError.setText("Ese username ya existe.");
                return;
            }
            if (pass.length() != 5) {
                lblError.setText("El password debe tener exactamente 5 caracteres.");
                return;
            }

            Jugador nuevo = new Jugador(user, pass);
            gestor.crearJugador(nuevo);
            dispose();
            new MenuPrincipal(nuevo, gestor, ventanaAnterior);
        });

        setVisible(true);
    }
}
