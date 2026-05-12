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
        setSize(400, 280);
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

        JPanel form = new JPanel(new GridLayout(3, 1, 8, 8));
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));

        JLabel lbl = new JLabel("Ingresa tu password para confirmar:");
        lbl.setForeground(Color.WHITE);
        JPasswordField txtPass = new JPasswordField();
        JButton btnEliminar = new JButton("ELIMINAR MI CUENTA");
        btnEliminar.setBackground(new Color(200, 50, 50));
        btnEliminar.setForeground(Color.WHITE);

        form.add(lbl); form.add(txtPass); form.add(btnEliminar);
        panel.add(form, BorderLayout.CENTER);

        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.YELLOW);
        panel.add(lblError, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> {
            String pass = new String(txtPass.getPassword());
            if (!pass.equals(jugador.getPassword())) {
                lblError.setText("Password incorrecto.");
                return;
            }
            gestor.eliminarJugador(jugador.getUsername());
            menuPrincipal.dispose();
            miCuenta.dispose();
            dispose();
        });

        setVisible(true);
    }
}