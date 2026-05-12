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
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = new JButton("← Atrás");
        btnAtras.setOpaque(false);
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFont(new Font("Arial", Font.BOLD, 13));
        btnAtras.addActionListener(e -> {
            miCuenta.volverAqui();
            dispose();
        });

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 1, 8, 8));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JLabel l1 = new JLabel("Password actual:");
        l1.setForeground(Color.WHITE);
        JPasswordField txtActual = new JPasswordField();

        JLabel l2 = new JLabel("Nuevo password (5 caracteres):");
        l2.setForeground(Color.WHITE);
        JPasswordField txtNuevo = new JPasswordField();

        JButton btnGuardar = new JButton("GUARDAR");

        form.add(l1); form.add(txtActual);
        form.add(l2); form.add(txtNuevo);
        form.add(btnGuardar);
        panel.add(form, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.YELLOW);
        panel.add(lblError, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> {
            String actual = new String(txtActual.getPassword());
            String nuevo = new String(txtNuevo.getPassword());
            if (!actual.equals(jugador.getPassword())) {
                lblError.setText("Password actual incorrecto.");
                return;
            }
            if (nuevo.length() != 5) {
                lblError.setText("El nuevo password debe tener exactamente 5 caracteres.");
                return;
            }
            jugador.setPassword(nuevo);
            miCuenta.volverAqui();
            dispose();
        });

        setVisible(true);
    }
}
