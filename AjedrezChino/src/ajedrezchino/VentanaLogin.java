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
        this.gestor          = gestor;
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Iniciar Sesión");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> { ventanaAnterior.setVisible(true); dispose(); });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);

        JLabel titulo = new JLabel("INICIAR SESIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 36));
        titulo.setForeground(new Color(255, 220, 80));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        JPanel caja = new JPanel(new GridLayout(5, 1, 0, 14)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        caja.setOpaque(false);
        caja.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE); lblUser.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField txtUser = new JTextField();
        txtUser.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE); lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton btnEntrar = BotonesEstilo.crearBoton("  ENTRAR  ", new Color(255, 220, 80));
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));

        caja.add(lblUser); caja.add(txtUser);
        caja.add(lblPass); caja.add(txtPass);
        caja.add(btnEntrar);

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

        btnEntrar.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                lblError.setText("Completa todos los campos.");
                return;
            }
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
