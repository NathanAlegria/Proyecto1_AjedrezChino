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
public class VentanaEliminarCuenta extends JFrame {

    public VentanaEliminarCuenta(Jugador jugador, GestorDatosImpl gestor,
            MiCuenta miCuenta, MenuPrincipal menuPrincipal) {
        setTitle("Eliminar Cuenta");
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

        JLabel titulo = new JLabel("ELIMINAR CUENTA", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(255, 80, 80));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(titulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        JPanel caja = new JPanel(new GridLayout(3, 1, 0, 16)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        caja.setOpaque(false);
        caja.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lbl = new JLabel("Ingresa tu password para confirmar:");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));

        JPasswordField txtPass = new JPasswordField();
        txtPass.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton btnEliminar = BotonesEstilo.crearBoton(
                "  ELIMINAR MI CUENTA  ", new Color(200, 60, 60));
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 16));

        caja.add(lbl);
        caja.add(txtPass);
        caja.add(btnEliminar);

        JPanel centroCaja = new JPanel(new GridBagLayout());
        centroCaja.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 100, 0, 100);
        caja.setPreferredSize(new Dimension(560, 200));
        centroCaja.add(caja, gbc);
        panel.add(centroCaja, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(new Color(255, 120, 120));
        lblError.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(lblError);
        panel.add(sur, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> {
            String pass = new String(txtPass.getPassword());

            if (!pass.equals(jugador.getPassword())) {
                lblError.setText("Password incorrecto.");
                return;
            }

            jugador.setActivo(false);
            jugador.setPassword("-----");

            JFrame menuInicio = menuPrincipal.getMenuInicio();

            dispose();
            miCuenta.dispose();
            menuPrincipal.dispose();

            if (menuInicio != null) {
                menuInicio.setVisible(true);
            }
        });

        setVisible(true);
    }
}
