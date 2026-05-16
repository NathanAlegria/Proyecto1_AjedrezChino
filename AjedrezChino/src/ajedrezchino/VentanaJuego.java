/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ajedrezchino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Nathan
 */
public class VentanaJuego extends JFrame {
    private Partida partida;
    private GestorDatosImpl gestor;
    private Jugador jugadorLoggeado;
    private Jugador oponente;
    private MenuPrincipal menuPrincipal;
    private PanelTablero panelTablero;
    private JLabel lblTurno;
    private JPanel panelInfoJugadores;

    // Cajas de muerte
    private ArrayList<Pieza> muertosBlancos = new ArrayList<>();
    private ArrayList<Pieza> muertosNegros  = new ArrayList<>();
    private PanelMuertos panelMuertosNegro;   // fichas negras capturadas (arriba)
    private PanelMuertos panelMuertosBlanco;  // fichas blancas capturadas (abajo)

    private int filaSeleccionada = -1;
    private int colSeleccionada  = -1;
    private ArrayList<int[]> movimientosValidos = new ArrayList<>();
    private HashMap<String, ImageIcon> imagenes;
    private HashMap<String, ImageIcon> imagenesSmall;
    private static final int TAM_CELDA   = 55;
    private static final int TAM_MUERTO  = 32;

    public VentanaJuego(Partida partida, GestorDatosImpl gestor,
                        Jugador loggeado, Jugador oponente, MenuPrincipal menuPrincipal) {
        this.partida         = partida;
        this.gestor          = gestor;
        this.jugadorLoggeado = loggeado;
        this.oponente        = oponente;
        this.menuPrincipal   = menuPrincipal;

        cargarImagenes();

        setTitle("Xiangqi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(25, 25, 25));

        // ── Norte: info jugadores ──
        panelInfoJugadores = new JPanel(new GridLayout(1, 2));
        panelInfoJugadores.setPreferredSize(new Dimension(0, 60));
        construirPanelJugadores();
        add(panelInfoJugadores, BorderLayout.NORTH);

        // ── Centro: tablero ──
        panelTablero = new PanelTablero();
        panelTablero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { manejarClick(e.getX(), e.getY()); }
        });
        add(panelTablero, BorderLayout.CENTER);

        // ── Este: cajas de muerte ──
        JPanel panelEste = new JPanel(new GridLayout(2, 1, 0, 4));
        panelEste.setBackground(new Color(25, 25, 25));
        panelEste.setPreferredSize(new Dimension(160, 0));
        panelEste.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 6));

        panelMuertosNegro  = new PanelMuertos("Capturadas — NEGRO",
                new Color(30, 70, 130), muertosNegros);
        panelMuertosBlanco = new PanelMuertos("Capturadas — BLANCO",
                new Color(100, 75, 10), muertosBlancos);

        panelEste.add(panelMuertosNegro);
        panelEste.add(panelMuertosBlanco);
        add(panelEste, BorderLayout.EAST);

        // ── Sur: turno + retiro ──
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(20, 20, 20));
        panelSur.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        lblTurno = new JLabel("", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 15));
        actualizarTurnoLabel();
        panelSur.add(lblTurno, BorderLayout.CENTER);

        JButton btnRetirar = BotonesEstilo.crearBoton("⚑  RETIRAR", new Color(200, 60, 60));
        btnRetirar.addActionListener(e -> confirmarRetiro());
        panelSur.add(btnRetirar, BorderLayout.EAST);

        add(panelSur, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ────────────────────────────────────────────
    // Panel cajas de muerte
    // ────────────────────────────────────────────
    private class PanelMuertos extends JPanel {
        private String titulo;
        private ArrayList<Pieza> lista;

        PanelMuertos(String titulo, Color colorFondo, ArrayList<Pieza> lista) {
            this.titulo = titulo;
            this.lista  = lista;
            setBackground(colorFondo);
            setBorder(BorderFactory.createLineBorder(new Color(180, 160, 80), 1));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Título
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.setColor(new Color(220, 200, 120));
            g2.drawString(titulo, 6, 14);

            // Fichas muertas
            int x = 4, y = 20;
            for (Pieza p : lista) {
                String key = getNombreImagen(p) + "_s";
                ImageIcon icon = imagenesSmall.get(key);
                if (icon != null) {
                    g2.drawImage(icon.getImage(), x, y, null);
                } else {
                    g2.setColor(p.isEsRojo() ? new Color(240, 220, 150) : new Color(100, 150, 220));
                    g2.fillOval(x + 2, y + 2, TAM_MUERTO - 6, TAM_MUERTO - 6);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 8));
                    g2.drawString(p.getNombre(), x + 8, y + TAM_MUERTO / 2 + 3);
                }
                x += TAM_MUERTO + 2;
                if (x + TAM_MUERTO > getWidth() - 4) {
                    x = 4;
                    y += TAM_MUERTO + 2;
                }
            }
            g2.dispose();
        }
    }

    // ────────────────────────────────────────────
    private void construirPanelJugadores() {
        panelInfoJugadores.removeAll();

        JPanel pNeg = new JPanel(new GridLayout(2, 1));
        pNeg.setBackground(partida.isTurnoRojo()
                ? new Color(40, 40, 40) : new Color(30, 80, 140));
        pNeg.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));

        JLabel lNeg1 = new JLabel("  ◆ NEGRO — " + oponente.getUsername());
        lNeg1.setFont(new Font("Arial", Font.BOLD, 13));
        lNeg1.setForeground(new Color(150, 200, 255));

        JLabel lNeg2 = new JLabel("  Puntos: " + oponente.getPuntos()
                + (!partida.isTurnoRojo() ? "  ◀ SU TURNO" : ""));
        lNeg2.setFont(new Font("Arial", Font.PLAIN, 11));
        lNeg2.setForeground(Color.LIGHT_GRAY);

        pNeg.add(lNeg1); pNeg.add(lNeg2);

        JPanel pBlanc = new JPanel(new GridLayout(2, 1));
        pBlanc.setBackground(partida.isTurnoRojo()
                ? new Color(80, 60, 20) : new Color(40, 40, 40));
        pBlanc.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.GRAY));

        JLabel lBlanc1 = new JLabel("  ◆ BLANCO — " + jugadorLoggeado.getUsername());
        lBlanc1.setFont(new Font("Arial", Font.BOLD, 13));
        lBlanc1.setForeground(new Color(255, 230, 150));

        JLabel lBlanc2 = new JLabel("  Puntos: " + jugadorLoggeado.getPuntos()
                + (partida.isTurnoRojo() ? "  ◀ SU TURNO" : ""));
        lBlanc2.setFont(new Font("Arial", Font.PLAIN, 11));
        lBlanc2.setForeground(Color.LIGHT_GRAY);

        pBlanc.add(lBlanc1); pBlanc.add(lBlanc2);

        panelInfoJugadores.add(pNeg);
        panelInfoJugadores.add(pBlanc);
        panelInfoJugadores.revalidate();
        panelInfoJugadores.repaint();
    }

    private void cargarImagenes() {
        imagenes      = new HashMap<>();
        imagenesSmall = new HashMap<>();
        String[] piezas  = {"General","Elefante","Caballo","CarrodeGuerra",
                            "Soldado","Canon","Oficial"};
        String[] sufijos = {"B","N"};
        for (String pieza : piezas) {
            for (String sufijo : sufijos) {
                String nombre = pieza + sufijo;
                try {
                    URL url = getClass().getResource("/Imagenes/" + nombre + ".png");
                    if (url != null) {
                        // Tamaño normal para el tablero
                        Image img = new ImageIcon(url).getImage()
                                .getScaledInstance(TAM_CELDA - 6, TAM_CELDA - 6, Image.SCALE_SMOOTH);
                        imagenes.put(nombre, new ImageIcon(img));
                        // Tamaño pequeño para la caja de muerte
                        Image imgS = new ImageIcon(url).getImage()
                                .getScaledInstance(TAM_MUERTO, TAM_MUERTO, Image.SCALE_SMOOTH);
                        imagenesSmall.put(nombre + "_s", new ImageIcon(imgS));
                    }
                } catch (Exception ignored) {}
            }
        }
    }

    private String getNombreImagen(Pieza p) {
        String s = p.isEsRojo() ? "B" : "N";
        if (p instanceof General)  return "General"       + s;
        if (p instanceof Elefante) return "Elefante"      + s;
        if (p instanceof Caballo)  return "Caballo"       + s;
        if (p instanceof Carro)    return "CarrodeGuerra" + s;
        if (p instanceof Soldado)  return "Soldado"       + s;
        if (p instanceof Canon)    return "Canon"         + s;
        if (p instanceof Oficial)  return "Oficial"       + s;
        return "";
    }

    private void calcularMovimientosValidos(int fila, int col) {
        movimientosValidos.clear();
        for (int f = 0; f < 10; f++)
            for (int c = 0; c < 9; c++)
                if (partida.getTablero().esMovimientoLegal(fila, col, f, c))
                    movimientosValidos.add(new int[]{f, c});
    }

    // ────────────────────────────────────────────
    // Panel tablero interno
    // ────────────────────────────────────────────
    private class PanelTablero extends JPanel {
        final int OFFSET_X = 25;
        final int OFFSET_Y = 5;

        PanelTablero() {
            setPreferredSize(new Dimension(
                TAM_CELDA * 9 + OFFSET_X + 22,
                TAM_CELDA * 10 + OFFSET_Y + 22));
            setBackground(new Color(30, 30, 30));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            dibujarCasillas(g2);
            dibujarMovimientosValidos(g2);
            dibujarSeleccion(g2);
            dibujarPalacios(g2);
            dibujarRio(g2);
            dibujarEtiquetas(g2);
            dibujarPiezas(g2);
        }

        private void dibujarCasillas(Graphics2D g) {
            Color verde = new Color(74, 120, 74);
            Color crema = new Color(240, 230, 190);
            for (int f = 0; f < 10; f++)
                for (int c = 0; c < 9; c++) {
                    g.setColor((f + c) % 2 == 0 ? verde : crema);
                    g.fillRect(OFFSET_X + c * TAM_CELDA,
                               OFFSET_Y + f * TAM_CELDA,
                               TAM_CELDA, TAM_CELDA);
                }
        }

        private void dibujarMovimientosValidos(Graphics2D g) {
            for (int[] mv : movimientosValidos) {
                int x = OFFSET_X + mv[1] * TAM_CELDA;
                int y = OFFSET_Y + mv[0] * TAM_CELDA;
                Pieza destino = partida.getTablero().getPieza(mv[0], mv[1]);
                if (destino != null) {
                    g.setColor(new Color(220, 50, 50, 170));
                    g.fillRect(x, y, TAM_CELDA, TAM_CELDA);
                    g.setColor(new Color(255, 80, 80));
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(x, y, TAM_CELDA, TAM_CELDA);
                } else {
                    g.setColor(new Color(100, 255, 100, 130));
                    int m = TAM_CELDA / 4;
                    g.fillOval(x + m, y + m, TAM_CELDA - m * 2, TAM_CELDA - m * 2);
                    g.setColor(new Color(60, 200, 60));
                    g.setStroke(new BasicStroke(2));
                    g.drawOval(x + m, y + m, TAM_CELDA - m * 2, TAM_CELDA - m * 2);
                }
            }
        }

        private void dibujarSeleccion(Graphics2D g) {
            if (filaSeleccionada == -1) return;
            int x = OFFSET_X + colSeleccionada * TAM_CELDA;
            int y = OFFSET_Y + filaSeleccionada * TAM_CELDA;
            g.setColor(new Color(255, 255, 0, 160));
            g.fillRect(x, y, TAM_CELDA, TAM_CELDA);
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(3));
            g.drawRect(x, y, TAM_CELDA, TAM_CELDA);
        }

        private void dibujarPalacios(Graphics2D g) {
            g.setColor(new Color(255, 215, 0, 180));
            g.setStroke(new BasicStroke(2));
            int x1 = OFFSET_X + 3 * TAM_CELDA, y1 = OFFSET_Y;
            g.drawRect(x1, y1, 2 * TAM_CELDA, 2 * TAM_CELDA);
            g.drawLine(x1, y1, x1 + 2 * TAM_CELDA, y1 + 2 * TAM_CELDA);
            g.drawLine(x1 + 2 * TAM_CELDA, y1, x1, y1 + 2 * TAM_CELDA);
            int x2 = OFFSET_X + 3 * TAM_CELDA, y2 = OFFSET_Y + 7 * TAM_CELDA;
            g.drawRect(x2, y2, 2 * TAM_CELDA, 2 * TAM_CELDA);
            g.drawLine(x2, y2, x2 + 2 * TAM_CELDA, y2 + 2 * TAM_CELDA);
            g.drawLine(x2 + 2 * TAM_CELDA, y2, x2, y2 + 2 * TAM_CELDA);
        }

        private void dibujarRio(Graphics2D g) {
            int x = OFFSET_X, y = OFFSET_Y + 4 * TAM_CELDA;
            int ancho = 9 * TAM_CELDA, alto = 2 * TAM_CELDA;
            g.setColor(new Color(100, 160, 220, 100));
            g.fillRect(x, y, ancho, alto);
            g.setColor(new Color(60, 120, 200));
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x + ancho, y);
            g.drawLine(x, y + alto, x + ancho, y + alto);
            g.setColor(new Color(100, 180, 255, 200));
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString("~ ~ ~ ~ ~ R I O ~ ~ ~ ~ ~",
                    x + ancho / 2 - 70, y + alto / 2 + 4);
        }

        private void dibujarEtiquetas(Graphics2D g) {
            g.setFont(new Font("Arial", Font.PLAIN, 11));
            String[] letras = {"a","b","c","d","e","f","g","h","i"};
            for (int c = 0; c < 9; c++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(letras[c],
                    OFFSET_X + c * TAM_CELDA + TAM_CELDA / 2 - 4,
                    OFFSET_Y + 10 * TAM_CELDA + 16);
            }
            for (int f = 0; f < 10; f++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(String.valueOf(10 - f),
                    OFFSET_X + 9 * TAM_CELDA + 5,
                    OFFSET_Y + f * TAM_CELDA + TAM_CELDA / 2 + 5);
            }
        }

        private void dibujarPiezas(Graphics2D g) {
            Pieza[][] casillas = partida.getTablero().getCasillas();
            for (int f = 0; f < 10; f++)
                for (int c = 0; c < 9; c++) {
                    Pieza p = casillas[f][c];
                    if (p == null) continue;
                    int x = OFFSET_X + c * TAM_CELDA + 3;
                    int y = OFFSET_Y + f * TAM_CELDA + 3;
                    ImageIcon icon = imagenes.get(getNombreImagen(p));
                    if (icon != null) {
                        g.drawImage(icon.getImage(), x, y, null);
                    } else {
                        g.setColor(p.isEsRojo() ? Color.WHITE : Color.DARK_GRAY);
                        g.fillOval(x + 4, y + 4, TAM_CELDA - 14, TAM_CELDA - 14);
                        g.setColor(p.isEsRojo() ? Color.BLACK : Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 10));
                        g.drawString(p.getNombre(), x + 12, y + TAM_CELDA / 2 + 3);
                    }
                }
        }
    }

    // ────────────────────────────────────────────
    private void manejarClick(int x, int y) {
        if (!partida.isActiva()) return;
        int col  = (x - panelTablero.OFFSET_X) / TAM_CELDA;
        int fila = (y - panelTablero.OFFSET_Y) / TAM_CELDA;
        if (fila < 0 || fila >= 10 || col < 0 || col >= 9) return;

        if (filaSeleccionada == -1) {
            Pieza p = partida.getTablero().getPieza(fila, col);
            if (p != null && p.isEsRojo() == partida.isTurnoRojo()) {
                filaSeleccionada = fila;
                colSeleccionada  = col;
                calcularMovimientosValidos(fila, col);
                panelTablero.repaint();
            }
        } else {
            Pieza p = partida.getTablero().getPieza(fila, col);
            if (p != null && p.isEsRojo() == partida.isTurnoRojo()) {
                filaSeleccionada = fila;
                colSeleccionada  = col;
                calcularMovimientosValidos(fila, col);
                panelTablero.repaint();
                return;
            }

            // Guardar pieza capturada antes de mover
            Pieza capturada = partida.getTablero().getPieza(fila, col);

            boolean movido = partida.intentarMover(
                    filaSeleccionada, colSeleccionada, fila, col);
            filaSeleccionada = -1;
            colSeleccionada  = -1;
            movimientosValidos.clear();

            if (movido) {
                // Agregar a caja de muerte si hubo captura
                if (capturada != null) {
                    if (capturada.isEsRojo()) {
                        muertosBlancos.add(capturada);
                        panelMuertosBlanco.repaint();
                    } else {
                        muertosNegros.add(capturada);
                        panelMuertosNegro.repaint();
                    }
                }
                panelTablero.repaint();
                verificarFinJuego();
                actualizarTurnoLabel();
                construirPanelJugadores();
            } else {
                panelTablero.repaint();
            }
        }
    }

    private void actualizarTurnoLabel() {
        if (partida.isTurnoRojo()) {
            lblTurno.setForeground(new Color(255, 220, 80));
            lblTurno.setText("▶  Turno de BLANCO — " + jugadorLoggeado.getUsername());
        } else {
            lblTurno.setForeground(new Color(120, 190, 255));
            lblTurno.setText("▶  Turno de NEGRO — " + oponente.getUsername());
        }
    }

    private void verificarFinJuego() {
        if (!partida.isActiva()) {
            Jugador ganador = partida.getGanador(false);
            if (ganador == null) return;
            Jugador perdedor = (ganador == jugadorLoggeado) ? oponente : jugadorLoggeado;
            String msg = ganador.getUsername() + " VENCIO A " + perdedor.getUsername()
                       + ", FELICIDADES HAS GANADO 3 PUNTOS";
            ganador.agregarPuntos(3);
            gestor.guardarLog(jugadorLoggeado.getUsername(), msg);
            gestor.guardarLog(oponente.getUsername(), msg);
            VentanaMensaje.mostrar(this, msg, "FIN DEL JUEGO");
            dispose();
            menuPrincipal.volverAqui();
        }
    }

    private void confirmarRetiro() {
        String queRetira = partida.isTurnoRojo()
                ? jugadorLoggeado.getUsername() : oponente.getUsername();
        VentanaConfirmar.mostrar(this,
            queRetira + ", ¿seguro que deseas retirarte?",
            "Confirmar retiro",
            () -> {
                boolean seRetiroRojo = partida.isTurnoRojo();
                partida.retirar(seRetiroRojo);
                Jugador ganador  = seRetiroRojo ? oponente : jugadorLoggeado;
                Jugador perdedor = seRetiroRojo ? jugadorLoggeado : oponente;
                String msg = perdedor.getUsername() + " SE HA RETIRADO, FELICIDADES "
                           + ganador.getUsername() + ", HAS GANADO 3 PUNTOS";
                ganador.agregarPuntos(3);
                gestor.guardarLog(jugadorLoggeado.getUsername(), msg);
                gestor.guardarLog(oponente.getUsername(), msg);
                VentanaMensaje.mostrar(null, msg, "FIN DEL JUEGO");
                dispose();
                menuPrincipal.volverAqui();
            });
    }
}