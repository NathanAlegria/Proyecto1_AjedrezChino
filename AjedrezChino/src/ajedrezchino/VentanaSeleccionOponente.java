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
public class VentanaSeleccionOponente extends JFrame {

    public VentanaSeleccionOponente(Jugador jugadorActual, ArrayList<String> opciones,
            GestorDatosImpl gestor, MenuPrincipal menuPrincipal) {
        setTitle("Seleccionar Oponente");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        JButton btnAtras = crearBotonAtras();
        btnAtras.addActionListener(e -> {
            menuPrincipal.volverAqui();
            dispose();
        });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        JLabel lbl = new JLabel("Selecciona tu oponente:", SwingConstants.CENTER);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (String op : opciones) {
            modelo.addElement(op);
        }
        JList<String> lista = new JList<>(modelo);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setSelectedIndex(0);
        lista.setBackground(new Color(30, 30, 30, 180));
        lista.setForeground(Color.WHITE);
        lista.setFont(new Font("Arial", Font.PLAIN, 13));

        JButton btnJugar = crearBotonEstilo("  JUGAR  ", new Color(80, 200, 80));
        btnJugar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                Jugador oponente = gestor.buscarJugador(seleccionado);
                Partida partida = new Partida(jugadorActual, oponente);
                dispose();
                new VentanaJuego(partida, gestor, jugadorActual, oponente, menuPrincipal);
            }
        });

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 40));
        centro.add(lbl, BorderLayout.NORTH);
        centro.add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(btnJugar);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(sur, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JButton crearBotonAtras() {
        return crearBotonEstilo("← Atrás", Color.WHITE);
    }

    private JButton crearBotonEstilo(String texto, Color colorBorde) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(new Color(colorBorde.getRed(),
                            colorBorde.getGreen(), colorBorde.getBlue(), 80));
                } else {
                    g2.setColor(new Color(colorBorde.getRed(),
                            colorBorde.getGreen(), colorBorde.getBlue(), 30));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(colorBorde);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setForeground(colorBorde);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }
}
