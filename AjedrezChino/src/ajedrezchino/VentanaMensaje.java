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
public class VentanaMensaje extends JFrame {

    public static void mostrar(JFrame padre, String mensaje, String titulo) {
        VentanaMensaje v = new VentanaMensaje(padre, mensaje, titulo);
        v.setVisible(true);
    }

    private VentanaMensaje(JFrame padre, String mensaje, String titulo) {
        setTitle(titulo);
        setSize(380, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(padre);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JLabel lbl = new JLabel("<html><center>" + mensaje + "</center></html>", SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, BorderLayout.CENTER);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> dispose());
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(btnOk);
        panel.add(sur, BorderLayout.SOUTH);
    }
}
