/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Nathan
 */
public class VentanaGestionPartidas extends JFrame {

    public interface AccionPartida {
        void ejecutar(String idSeleccionado);
    }

    public VentanaGestionPartidas(String titulo, Color colorBoton,
                                   String textoBoton, ArrayList<String> ids,
                                   AccionPartida accion, JFrame padre) {
        setTitle(titulo);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(padre);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        // Atrás
        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> { padre.setVisible(true); dispose(); });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(255, 220, 80));

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(panelTop, BorderLayout.NORTH);
        norte.add(lblTitulo, BorderLayout.CENTER);
        panel.add(norte, BorderLayout.NORTH);

        // Lista de partidas con fondo semitransparente
        DefaultListModel<String> modelo = new DefaultListModel<>();
        if (ids.isEmpty()) {
            modelo.addElement("— No hay partidas guardadas —");
        } else {
            for (String id : ids) modelo.addElement(id);
        }

        JList<String> lista = new JList<>(modelo);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setFont(new Font("Arial", Font.PLAIN, 15));
        lista.setForeground(Color.WHITE);
        lista.setBackground(new Color(0, 0, 0, 0));
        lista.setOpaque(false);
        lista.setFixedCellHeight(40);

        lista.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Arial", Font.PLAIN, 15));
                lbl.setBorder(BorderFactory.createEmptyBorder(4, 16, 4, 16));
                if (isSelected) {
                    lbl.setBackground(new Color(255, 200, 50, 120));
                    lbl.setOpaque(true);
                } else {
                    lbl.setBackground(new Color(0, 0, 0, 80));
                    lbl.setOpaque(true);
                }
                return lbl;
            }
        });

        if (!ids.isEmpty()) lista.setSelectedIndex(0);

        JScrollPane scroll = new JScrollPane(lista) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder(8, 30, 8, 30));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón acción
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JButton btnAccion = BotonesEstilo.crearBoton(textoBoton, colorBoton);
        btnAccion.setFont(new Font("Arial", Font.BOLD, 16));
        btnAccion.addActionListener(e -> {
            if (ids.isEmpty()) return;
            String sel = lista.getSelectedValue();
            if (sel == null || sel.startsWith("—")) return;
            accion.ejecutar(sel);
            padre.setVisible(true);
            dispose();
        });

        sur.add(btnAccion);
        panel.add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }
}