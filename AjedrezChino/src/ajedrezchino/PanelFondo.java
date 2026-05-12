/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
/**
 *
 * @author Nathan
 */
public class PanelFondo extends JPanel {
    private Image imagen;

    public PanelFondo() {
        try {
            URL url = getClass().getResource("/Imagenes/fondo.png");
            imagen = new ImageIcon(url).getImage();
        } catch (Exception e) {
            imagen = null;
        }
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
