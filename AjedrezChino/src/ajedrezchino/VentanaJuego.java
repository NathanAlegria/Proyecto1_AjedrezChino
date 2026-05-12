/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Nathan
 */
public class VentanaJuego extends JFrame {
    private Partida partida;
    private GestorDatosImpl gestor;
    private Jugador jugadorLoggeado;
    private Jugador oponente;
    private JPanel panelTablero;
    private JLabel lblTurno;
    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;
    private static final int TAM_CELDA = 60;

    public VentanaJuego(Partida partida, GestorDatosImpl gestor, Jugador loggeado, Jugador oponente) {
        this.partida = partida;
        this.gestor = gestor;
        this.jugadorLoggeado = loggeado;
        this.oponente = oponente;

        setTitle("Xiangqi - " + loggeado.getUsername() + " (Rojo) vs " + oponente.getUsername() + " (Negro)");
        setSize(TAM_CELDA * 9 + 80, TAM_CELDA * 10 + 150);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lblTurno = new JLabel("Turno: ROJO (" + loggeado.getUsername() + ")", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 14));
        lblTurno.setForeground(Color.RED);
        add(lblTurno, BorderLayout.NORTH);

        panelTablero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarTablero(g);
            }
        };
        panelTablero.setPreferredSize(new Dimension(TAM_CELDA * 9, TAM_CELDA * 10));
        panelTablero.setBackground(new Color(210, 180, 100));
        panelTablero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClick(e.getX(), e.getY());
            }
        });
        add(panelTablero, BorderLayout.CENTER);

        JButton btnRetirar = new JButton("RETIRAR");
        btnRetirar.setBackground(Color.RED);
        btnRetirar.setForeground(Color.WHITE);
        btnRetirar.addActionListener(e -> confirmarRetiro());
        JPanel panelSur = new JPanel();
        panelSur.add(btnRetirar);
        add(panelSur, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void dibujarTablero(Graphics g) {
        Pieza[][] casillas = partida.getTablero().getCasillas();

        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                int x = c * TAM_CELDA + 5;
                int y = f * TAM_CELDA + 5;

                // Color de celda
                if (f == filaSeleccionada && c == colSeleccionada) {
                    g.setColor(new Color(255, 255, 100));
                } else if (f == 4 || f == 5) {
                    g.setColor(new Color(150, 220, 255)); // Río
                } else {
                    g.setColor(new Color(222, 184, 135));
                }
                g.fillRect(x, y, TAM_CELDA, TAM_CELDA);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, TAM_CELDA, TAM_CELDA);

                // Dibujar pieza
                Pieza p = casillas[f][c];
                if (p != null) {
                    if (p.isEsRojo()) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                    g.setFont(new Font("Arial", Font.BOLD, 13));
                    String texto = p.getNombre() + "(" + (p.isEsRojo() ? "R" : "N") + ")";
                    g.drawString(texto, x + 4, y + TAM_CELDA / 2 + 5);
                }
            }
        }

        // Resaltar palacio con líneas
        g.setColor(Color.BLUE);
        // Palacio negro (filas 0-2, cols 3-5)
        g.drawLine(3 * TAM_CELDA + 5, 5, 5 * TAM_CELDA + 5, 2 * TAM_CELDA + 5);
        g.drawLine(5 * TAM_CELDA + 5, 5, 3 * TAM_CELDA + 5, 2 * TAM_CELDA + 5);
        // Palacio rojo (filas 7-9, cols 3-5)
        g.drawLine(3 * TAM_CELDA + 5, 7 * TAM_CELDA + 5, 5 * TAM_CELDA + 5, 9 * TAM_CELDA + 5);
        g.drawLine(5 * TAM_CELDA + 5, 7 * TAM_CELDA + 5, 3 * TAM_CELDA + 5, 9 * TAM_CELDA + 5);
    }

    private void manejarClick(int x, int y) {
        if (!partida.isActiva()) return;

        int col = (x - 5) / TAM_CELDA;
        int fila = (y - 5) / TAM_CELDA;

        if (fila < 0 || fila >= 10 || col < 0 || col >= 9) return;

        if (filaSeleccionada == -1) {
            // Primera selección: elegir pieza
            Pieza p = partida.getTablero().getPieza(fila, col);
            if (p != null && p.isEsRojo() == partida.isTurnoRojo()) {
                filaSeleccionada = fila;
                colSeleccionada = col;
                panelTablero.repaint();
            }
        } else {
            // Segunda selección: mover
            boolean movido = partida.intentarMover(filaSeleccionada, colSeleccionada, fila, col);
            filaSeleccionada = -1;
            colSeleccionada = -1;
            panelTablero.repaint();

            if (movido) {
                verificarFinJuego();
                actualizarTurnoLabel();
            }
        }
    }

    private void actualizarTurnoLabel() {
        if (partida.isTurnoRojo()) {
            lblTurno.setText("Turno: ROJO (" + jugadorLoggeado.getUsername() + ")");
            lblTurno.setForeground(Color.RED);
        } else {
            lblTurno.setText("Turno: NEGRO (" + oponente.getUsername() + ")");
            lblTurno.setForeground(Color.BLACK);
        }
    }

    private void verificarFinJuego() {
        if (!partida.isActiva()) {
            Jugador ganador = partida.getGanador(false);
            if (ganador != null) {
                Jugador perdedor = ganador == jugadorLoggeado ? oponente : jugadorLoggeado;
                String msg = ganador.getUsername() + " VENCIO A " + perdedor.getUsername() + ", FELICIDADES HAS GANADO 3 PUNTOS";
                ganador.agregarPuntos(3);
                gestor.guardarLog(jugadorLoggeado.getUsername(), msg);
                gestor.guardarLog(oponente.getUsername(), msg);
                JOptionPane.showMessageDialog(this, msg, "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }

    private void confirmarRetiro() {
        String jugadorQueRetira = partida.isTurnoRojo() ? jugadorLoggeado.getUsername() : oponente.getUsername();
        int conf = JOptionPane.showConfirmDialog(this,
                jugadorQueRetira + ", ¿seguro que deseas retirarte?", "Confirmar retiro", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            boolean seRetiroRojo = partida.isTurnoRojo();
            partida.retirar(seRetiroRojo);
            Jugador ganador = seRetiroRojo ? oponente : jugadorLoggeado;
            Jugador perdedor = seRetiroRojo ? jugadorLoggeado : oponente;
            String msg = perdedor.getUsername() + " SE HA RETIRADO, FELICIDADES " + ganador.getUsername() + ", HAS GANADO 3 PUNTOS";
            ganador.agregarPuntos(3);
            gestor.guardarLog(jugadorLoggeado.getUsername(), msg);
            gestor.guardarLog(oponente.getUsername(), msg);
            JOptionPane.showMessageDialog(this, msg, "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
}
