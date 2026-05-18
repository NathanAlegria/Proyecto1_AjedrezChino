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
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);

        JLabel titulo = new JLabel("CREAR JUGADOR", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(255, 220, 80));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Caja formulario
        JPanel caja = new JPanel(new GridLayout(6, 1, 0, 14)) {
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

        JLabel lblUser = new JLabel("Username (único):");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 16));

        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel lblPass = new JLabel("Password (5 caracteres alfanuméricos: letras y números):");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 16));

        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton btnCrear = BotonesEstilo.crearBoton("  CREAR  ", new Color(100, 210, 100));
        btnCrear.setFont(new Font("Arial", Font.BOLD, 16));

        caja.add(lblUser);
        caja.add(txtUser);
        caja.add(lblPass);
        caja.add(txtPass);
        caja.add(btnCrear);

        JPanel centroCaja = new JPanel(new GridBagLayout());
        centroCaja.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 100, 0, 100);
        caja.setPreferredSize(new Dimension(560, 300));
        centroCaja.add(caja, gbc);
        panel.add(centroCaja, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(new Color(255, 120, 120));
        lblError.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(lblError);
        panel.add(sur, BorderLayout.SOUTH);

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
            String errorPass = validarPassword(pass);
            if (errorPass != null) {
                lblError.setText(errorPass);
                return;
            }

            Jugador nuevo = new Jugador(user, pass);
            gestor.crearJugador(nuevo);
            dispose();
            new MenuPrincipal(nuevo, gestor, ventanaAnterior);
        });

        setVisible(true);
    }

    // Valida: exactamente 5 chars, solo letras y números, debe tener al menos 1 letra y 1 número
    private String validarPassword(String pass) {
        if (pass.length() != 5) {
            return "El password debe tener exactamente 5 caracteres.";
        }
        if (!pass.matches("[a-zA-Z0-9]+")) {
            return "El password solo puede contener letras y números (alfanumérico).";
        }
        boolean tieneLetra = pass.matches(".*[a-zA-Z].*");
        boolean tieneNumero = pass.matches(".*[0-9].*");
        if (!tieneLetra) {
            return "El password debe contener al menos una letra.";
        }
        if (!tieneNumero) {
            return "El password debe contener al menos un número.";
        }
        return null; // válido
    }
}
