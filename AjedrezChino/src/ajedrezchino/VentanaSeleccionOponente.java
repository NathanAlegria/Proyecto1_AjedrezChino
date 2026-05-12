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

        // Flecha de regreso
        JButton btnAtras = new JButton("← Atrás");
        btnAtras.setOpaque(false);
        btnAtras.setForeground(Color.WHITE);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFont(new Font("Arial", Font.BOLD, 13));
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
        for (String op : opciones) modelo.addElement(op);
        JList<String> lista = new JList<>(modelo);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setSelectedIndex(0);

        JButton btnJugar = new JButton("JUGAR");
        btnJugar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                Jugador oponente = gestor.buscarJugador(seleccionado);
                Partida partida = new Partida(jugadorActual, oponente);
                dispose();
                new VentanaJuego(partida, gestor, jugadorActual, oponente);
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
}
