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
public class VentanaConfirmar extends JFrame {

    public static void mostrar(JFrame padre, String mensaje, String titulo, Runnable alConfirmar) {
        new VentanaConfirmar(padre, mensaje, titulo, alConfirmar);
    }

    private VentanaConfirmar(JFrame padre, String mensaje, String titulo, Runnable alConfirmar) {
        setTitle(titulo);
        setSize(380, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(padre);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JLabel lbl = new JLabel("<html><center>" + mensaje + "</center></html>", SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(lbl, BorderLayout.CENTER);

        JPanel sur = new JPanel();
        sur.setOpaque(false);

        JButton btnSi = new JButton("SÍ, RETIRARME");
        btnSi.setBackground(new Color(180, 30, 30));
        btnSi.setForeground(Color.WHITE);

        JButton btnNo = new JButton("CANCELAR");

        btnSi.addActionListener(e -> {
            dispose();
            alConfirmar.run();
        });
        btnNo.addActionListener(e -> dispose());

        sur.add(btnSi);
        sur.add(btnNo);
        panel.add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }
}
