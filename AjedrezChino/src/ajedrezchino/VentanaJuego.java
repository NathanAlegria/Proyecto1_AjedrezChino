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
    private JLabel lblError;
    private JPanel panelInfoJugadores;

    private PanelMuertos panelMuertosNegro;
    private PanelMuertos panelMuertosBlanco;

    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;
    private ArrayList<int[]> movimientosValidos = new ArrayList<>();
    private HashMap<String, ImageIcon> imagenes;
    private HashMap<String, ImageIcon> imagenesSmall;

    private static final int TAM_CELDA = 70;
    private static final int ALTO_RIO = 22;
    private static final int TAM_MUERTO = 36;

    public VentanaJuego(Partida partida, GestorDatosImpl gestor, Jugador loggeado, Jugador oponente, MenuPrincipal menuPrincipal) {
        this.partida = partida;
        this.gestor = gestor;
        this.jugadorLoggeado = loggeado;
        this.oponente = oponente;
        this.menuPrincipal = menuPrincipal;

        cargarImagenes();
        setTitle("Xiangqi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(new Color(25, 25, 25));

        // info jugadores + barra de mensajes 
        JPanel panelNorteCompleto = new JPanel(new BorderLayout());
        panelNorteCompleto.setBackground(new Color(20, 20, 20));

        panelInfoJugadores = new JPanel(new GridLayout(1, 2));
        panelInfoJugadores.setPreferredSize(new Dimension(0, 65));
        construirPanelJugadores();
        panelNorteCompleto.add(panelInfoJugadores, BorderLayout.CENTER);

        lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setFont(new Font("Arial", Font.BOLD, 13));
        lblError.setOpaque(true);
        lblError.setBackground(new Color(30, 30, 30));
        lblError.setForeground(new Color(255, 120, 120));
        lblError.setPreferredSize(new Dimension(0, 28));
        lblError.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
                new Color(80, 80, 80)));
        panelNorteCompleto.add(lblError, BorderLayout.SOUTH);
        add(panelNorteCompleto, BorderLayout.NORTH);

        // tablero
        panelTablero = new PanelTablero();
        panelTablero.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClick(e.getX(), e.getY());
            }
        });
        add(panelTablero, BorderLayout.CENTER);

        //cajas de muerte
        JPanel panelEste = new JPanel(new GridLayout(2, 1, 0, 4));
        panelEste.setBackground(new Color(25, 25, 25));
        panelEste.setPreferredSize(new Dimension(180, 0));
        panelEste.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 6));
        panelMuertosNegro = new PanelMuertos("Capturadas — NEGRO", new Color(30, 70, 130));
        panelMuertosBlanco = new PanelMuertos("Capturadas — BLANCO", new Color(100, 75, 10));
        panelEste.add(panelMuertosNegro);
        panelEste.add(panelMuertosBlanco);
        add(panelEste, BorderLayout.EAST);

        // turno + retiro
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(20, 20, 20));
        panelSur.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        lblTurno = new JLabel("", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 15));
        actualizarTurnoLabel();
        panelSur.add(lblTurno, BorderLayout.CENTER);

        JButton btnRetirar = BotonesEstilo.crearBoton("  RETIRAR", new Color(200, 60, 60));
        btnRetirar.addActionListener(e -> confirmarRetiro());
        panelSur.add(btnRetirar, BorderLayout.EAST);
        add(panelSur, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Panel cajas de muerte
    private class PanelMuertos extends JPanel {

        private final String titulo;
        private final boolean esBlancos;

        PanelMuertos(String titulo, Color fondo) {
            this.titulo = titulo;
            this.esBlancos = titulo.contains("BLANCO");
            setBackground(fondo);
            setBorder(BorderFactory.createLineBorder(new Color(180, 160, 80), 1));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            g2.setColor(new Color(220, 200, 120));
            g2.drawString(titulo, 6, 16);

            ArrayList<Pieza> lista = esBlancos
                    ? partida.getMuertosBlancos()
                    : partida.getMuertosNegros();

            int x = 4, y = 22;
            for (Pieza p : lista) {
                ImageIcon icon = imagenesSmall.get(getNombreImagen(p) + "_s");
                if (icon != null) {
                    g2.drawImage(icon.getImage(), x, y, null);
                } else {
                    g2.setColor(p.isEsRojo()
                            ? new Color(240, 220, 150) : new Color(100, 150, 220));
                    g2.fillOval(x + 2, y + 2, TAM_MUERTO - 6, TAM_MUERTO - 6);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 8));
                    g2.drawString(p.getNombre(), x + 8, y + TAM_MUERTO / 2 + 3);
                }
                x += TAM_MUERTO + 3;
                if (x + TAM_MUERTO > getWidth() - 4) {
                    x = 4;
                    y += TAM_MUERTO + 3;
                }
            }
            g2.dispose();
        }
    }

    private void refrescarCementerios() {
        panelMuertosBlanco.repaint();
        panelMuertosNegro.repaint();
    }

    // Panel jugadores 
    private void construirPanelJugadores() {
        panelInfoJugadores.removeAll();

        int valorNegro = TipoPieza.calcularValorEjercito(
                false, partida.getTablero().getCasillas());
        int valorBlanco = TipoPieza.calcularValorEjercito(
                true, partida.getTablero().getCasillas());

        // ── Panel NEGRO ──
        JPanel pNeg = new JPanel(new GridLayout(2, 1));
        pNeg.setBackground(!partida.isTurnoRojo()
                ? new Color(30, 80, 140) : new Color(40, 40, 40));
        pNeg.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 2, new Color(100, 100, 100)));

        JLabel lN1 = new JLabel("    NEGRO  —  " + oponente.getUsername());
        lN1.setFont(new Font("Arial", Font.BOLD, 15));
        lN1.setForeground(new Color(150, 210, 255));

        JLabel lN2 = new JLabel(
                "  Puntos: " + oponente.getPuntos()
                + "   |   Ejército: " + valorNegro
                + (!partida.isTurnoRojo() ? "       SU TURNO" : ""));
        lN2.setFont(new Font("Arial", Font.BOLD, 12));
        lN2.setForeground(!partida.isTurnoRojo()
                ? new Color(255, 220, 80) : Color.LIGHT_GRAY);

        pNeg.add(lN1);
        pNeg.add(lN2);

        // ── Panel BLANCO ──
        JPanel pBl = new JPanel(new GridLayout(2, 1));
        pBl.setBackground(partida.isTurnoRojo()
                ? new Color(90, 65, 15) : new Color(40, 40, 40));
        pBl.setBorder(BorderFactory.createMatteBorder(
                0, 2, 0, 0, new Color(100, 100, 100)));

        JLabel lB1 = new JLabel("    BLANCO  —  " + jugadorLoggeado.getUsername());
        lB1.setFont(new Font("Arial", Font.BOLD, 15));
        lB1.setForeground(new Color(255, 230, 150));

        JLabel lB2 = new JLabel(
                "  Puntos: " + jugadorLoggeado.getPuntos()
                + "   |   Ejército: " + valorBlanco
                + (partida.isTurnoRojo() ? "       SU TURNO" : ""));
        lB2.setFont(new Font("Arial", Font.BOLD, 12));
        lB2.setForeground(partida.isTurnoRojo()
                ? new Color(255, 220, 80) : Color.LIGHT_GRAY);

        pBl.add(lB1);
        pBl.add(lB2);

        panelInfoJugadores.add(pNeg);
        panelInfoJugadores.add(pBl);
        panelInfoJugadores.revalidate();
        panelInfoJugadores.repaint();
    }

    private void cargarImagenes() {
        imagenes = new HashMap<>();
        imagenesSmall = new HashMap<>();
        String[] piezas = {"General", "Elefante", "Caballo", "CarrodeGuerra",
            "Soldado", "Canon", "Oficial"};
        String[] sufijos = {"B", "N"};
        for (String pieza : piezas) {
            for (String suf : sufijos) {
                String nombre = pieza + suf;
                try {
                    URL url = getClass().getResource("/Imagenes/" + nombre + ".png");
                    if (url != null) {
                        Image img = new ImageIcon(url).getImage()
                                .getScaledInstance(TAM_CELDA - 6, TAM_CELDA - 6,
                                        Image.SCALE_SMOOTH);
                        Image imgS = new ImageIcon(url).getImage()
                                .getScaledInstance(TAM_MUERTO, TAM_MUERTO,
                                        Image.SCALE_SMOOTH);
                        imagenes.put(nombre, new ImageIcon(img));
                        imagenesSmall.put(nombre + "_s", new ImageIcon(imgS));
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    private String getNombreImagen(Pieza p) {
        TipoPieza tipo = TipoPieza.desdePieza(p);
        if (tipo != null) {
            return tipo.getNombreImagen(p.isEsRojo());
        }
        return "";
    }

    private void calcularMovimientosValidos(int fila, int col) {
        movimientosValidos.clear();
        for (int f = 0; f < 10; f++) {
            for (int c = 0; c < 9; c++) {
                if (partida.getTablero().esMovimientoLegal(fila, col, f, c)) {
                    movimientosValidos.add(new int[]{f, c});
                }
            }
        }
    }

    private class PanelTablero extends JPanel {

        final int OFFSET_X = 28;
        final int OFFSET_Y = 6;

        int filaAY(int fila) {
            if (fila <= 4) {
                return OFFSET_Y + fila * TAM_CELDA;
            }
            return OFFSET_Y + fila * TAM_CELDA + ALTO_RIO;
        }

        PanelTablero() {
            setPreferredSize(new Dimension(
                    TAM_CELDA * 9 + OFFSET_X + 30,
                    TAM_CELDA * 10 + ALTO_RIO + OFFSET_Y + 26));
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
            dibujarRio(g2);
            dibujarPalacios(g2);
            dibujarEtiquetas(g2);
            dibujarPiezas(g2);
        }

        private void dibujarCasillas(Graphics2D g) {
            Color verde = new Color(74, 120, 74);
            Color crema = new Color(240, 230, 190);
            for (int f = 0; f < 10; f++) {
                for (int c = 0; c < 9; c++) {
                    g.setColor((f + c) % 2 == 0 ? verde : crema);
                    g.fillRect(OFFSET_X + c * TAM_CELDA,
                            filaAY(f), TAM_CELDA, TAM_CELDA);
                }
            }
        }

        private void dibujarRio(Graphics2D g) {
            int y = OFFSET_Y + 5 * TAM_CELDA;
            int x = OFFSET_X;
            int ancho = 9 * TAM_CELDA;
            g.setColor(new Color(80, 140, 200, 160));
            g.fillRect(x, y, ancho, ALTO_RIO);
            g.setColor(new Color(50, 100, 180));
            g.setStroke(new BasicStroke(2));
            g.drawLine(x, y, x + ancho, y);
            g.drawLine(x, y + ALTO_RIO, x + ancho, y + ALTO_RIO);
            g.setColor(new Color(160, 210, 255, 220));
            g.setFont(new Font("Arial", Font.BOLD, 11));
            g.drawString("~ ~ ~ ~ ~ ~ R Í O ~ ~ ~ ~ ~ ~",
                    x + ancho / 2 - 80, y + ALTO_RIO / 2 + 4);
        }

        private void dibujarPalacios(Graphics2D g) {
            g.setColor(new Color(255, 215, 0, 200));
            g.setStroke(new BasicStroke(2));
            int px = OFFSET_X + 3 * TAM_CELDA;
            int pw = 3 * TAM_CELDA;

            // Palacio NEGRO filas 0-2
            int py1 = filaAY(0);
            int ph1 = filaAY(2) + TAM_CELDA - py1;
            g.drawRect(px, py1, pw, ph1);
            g.drawLine(px, py1, px + pw, py1 + ph1);
            g.drawLine(px + pw, py1, px, py1 + ph1);

            // Palacio BLANCO filas 7-9
            int py2 = filaAY(7);
            int ph2 = filaAY(9) + TAM_CELDA - py2;
            g.drawRect(px, py2, pw, ph2);
            g.drawLine(px, py2, px + pw, py2 + ph2);
            g.drawLine(px + pw, py2, px, py2 + ph2);
        }

        private void dibujarMovimientosValidos(Graphics2D g) {
            for (int[] mv : movimientosValidos) {
                int x = OFFSET_X + mv[1] * TAM_CELDA;
                int y = filaAY(mv[0]);
                Pieza dest = partida.getTablero().getPieza(mv[0], mv[1]);
                if (dest != null) {
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
            if (filaSeleccionada == -1) {
                return;
            }
            int x = OFFSET_X + colSeleccionada * TAM_CELDA;
            int y = filaAY(filaSeleccionada);
            g.setColor(new Color(255, 255, 0, 160));
            g.fillRect(x, y, TAM_CELDA, TAM_CELDA);
            g.setColor(Color.YELLOW);
            g.setStroke(new BasicStroke(3));
            g.drawRect(x, y, TAM_CELDA, TAM_CELDA);
        }

        private void dibujarEtiquetas(Graphics2D g) {
            g.setFont(new Font("Arial", Font.PLAIN, 12));
            String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
            int yLetras = filaAY(9) + TAM_CELDA + 16;
            for (int c = 0; c < 9; c++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(letras[c],
                        OFFSET_X + c * TAM_CELDA + TAM_CELDA / 2 - 4, yLetras);
            }
            for (int f = 0; f < 10; f++) {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString(String.valueOf(10 - f),
                        OFFSET_X + 9 * TAM_CELDA + 6,
                        filaAY(f) + TAM_CELDA / 2 + 5);
            }
        }

        private void dibujarPiezas(Graphics2D g) {
            Pieza[][] cas = partida.getTablero().getCasillas();
            for (int f = 0; f < 10; f++) {
                for (int c = 0; c < 9; c++) {
                    Pieza p = cas[f][c];
                    if (p == null) {
                        continue;
                    }
                    int x = OFFSET_X + c * TAM_CELDA + 3;
                    int y = filaAY(f) + 3;
                    ImageIcon icon = imagenes.get(getNombreImagen(p));
                    if (icon != null) {
                        g.drawImage(icon.getImage(), x, y, null);
                    } else {
                        g.setColor(p.isEsRojo() ? Color.WHITE : Color.DARK_GRAY);
                        g.fillOval(x + 4, y + 4, TAM_CELDA - 14, TAM_CELDA - 14);
                        g.setColor(p.isEsRojo() ? Color.BLACK : Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 11));
                        g.drawString(p.getNombre(), x + 14, y + TAM_CELDA / 2 + 4);
                    }
                }
            }
        }
    }

    private void manejarClick(int x, int y) {
        if (!partida.isActiva()) {
            return;
        }

        int col = (x - panelTablero.OFFSET_X) / TAM_CELDA;
        int fila = pixelAFila(y);
        if (fila < 0 || fila >= 10 || col < 0 || col >= 9) {
            return;
        }

        if (filaSeleccionada == -1) {
            Pieza p = partida.getTablero().getPieza(fila, col);
            if (p == null) {
                return;
            }
            if (p.isEsRojo() != partida.isTurnoRojo()) {
                mostrarError("No es tu turno.", new Color(255, 180, 80));
                return;
            }
            filaSeleccionada = fila;
            colSeleccionada = col;
            calcularMovimientosValidos(fila, col);
            panelTablero.repaint();
        } else {
            Pieza p = partida.getTablero().getPieza(fila, col);
            // Reasignar si clic en pieza propia
            if (p != null && p.isEsRojo() == partida.isTurnoRojo()) {
                filaSeleccionada = fila;
                colSeleccionada = col;
                calcularMovimientosValidos(fila, col);
                panelTablero.repaint();
                return;
            }

            boolean movido = partida.intentarMover(
                    filaSeleccionada, colSeleccionada, fila, col);

            if (!movido) {
                mostrarError(mensajeError(partida.getUltimoError()),
                        new Color(255, 120, 120));
            } else {
                lblError.setText("");
                refrescarCementerios();
                verificarFinJuego();
                actualizarTurnoLabel();
                construirPanelJugadores();
            }
            filaSeleccionada = -1;
            colSeleccionada = -1;
            movimientosValidos.clear();
            panelTablero.repaint();
        }
    }

    private int pixelAFila(int y) {
        int yRel = y - panelTablero.OFFSET_Y;
        if (yRel < 0) {
            return -1;
        }
        if (yRel < 5 * TAM_CELDA) {
            return yRel / TAM_CELDA;
        }
        if (yRel < 5 * TAM_CELDA + ALTO_RIO) {
            return -1;
        }
        int yPost = yRel - 5 * TAM_CELDA - ALTO_RIO;
        int fila = 5 + yPost / TAM_CELDA;
        return fila < 10 ? fila : -1;
    }

    private void actualizarTurnoLabel() {
        if (partida.isTurnoRojo()) {
            lblTurno.setForeground(new Color(255, 220, 80));
            lblTurno.setText("  Turno de BLANCO — " + jugadorLoggeado.getUsername());
        } else {
            lblTurno.setForeground(new Color(120, 190, 255));
            lblTurno.setText("  Turno de NEGRO — " + oponente.getUsername());
        }
    }

    private void mostrarError(String msg, Color color) {
        lblError.setForeground(color);
        lblError.setBackground(new Color(30, 30, 30));
        lblError.setText("  ⚠  " + msg);
        Timer t = new Timer(3000, e -> lblError.setText(""));
        t.setRepeats(false);
        t.start();
    }

    private String mensajeError(int codigo) {
        switch (codigo) {
            case Tablero.ERR_VACIO:
                return "No hay pieza en esa casilla.";
            case Tablero.ERR_NO_TU_TURNO:
                return "No es tu turno.";
            case Tablero.ERR_MOVIMIENTO:
                return "Movimiento inválido para esta pieza.";
            case Tablero.ERR_PIEZA_PROPIA:
                return "No puedes capturar tu propia pieza.";
            case Tablero.ERR_GENERALES:
                return "Ese movimiento enfrenta a los Generales ilegalmente.";
            case Tablero.ERR_FUERA_PALACIO:
                return "Esta pieza no puede salir del palacio.";
            default:
                return "Movimiento inválido.";
        }
    }

    //Fin de juego
    private void terminarJuego(Jugador ganador, Jugador perdedor, boolean porRetiro) {
        ganador.agregarPuntos(3);
        String msgGanador = porRetiro
                ? perdedor.getUsername() + " SE HA RETIRADO — TU GANASTE 3 PUNTOS"
                : "VENCISTE A " + perdedor.getUsername() + " — HAS GANADO 3 PUNTOS";
        String msgPerdedor = porRetiro
                ? "TE RETIRASTE — " + ganador.getUsername() + " GANO LA PARTIDA"
                : perdedor.getUsername() + " FUE DERROTADO POR " + ganador.getUsername();
        gestor.guardarLog(ganador.getUsername(), msgGanador);
        gestor.guardarLog(perdedor.getUsername(), msgPerdedor);
        String pantalla = porRetiro
                ? perdedor.getUsername() + " SE HA RETIRADO\nFELICIDADES "
                + ganador.getUsername() + ", HAS GANADO 3 PUNTOS"
                : ganador.getUsername() + " VENCIO A " + perdedor.getUsername()
                + "\nFELICIDADES " + ganador.getUsername() + ", HAS GANADO 3 PUNTOS";
        mostrarMensajeFinal(pantalla);
    }

    private void mostrarMensajeFinal(String mensaje) {
        JDialog d = new JDialog((Frame) null, "FIN DEL JUEGO", true);
        d.setSize(480, 260);
        d.setLocationRelativeTo(null);
        d.setAlwaysOnTop(true);
        d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        PanelFondo fondo = new PanelFondo();
        fondo.setLayout(new BorderLayout());
        d.setContentPane(fondo);

        JPanel caja = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 175));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
            }
        };
        caja.setOpaque(false);
        caja.setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel lbl = new JLabel(
                "<html><center>" + mensaje.replace("\n", "<br>") + "</center></html>",
                SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 17));
        lbl.setForeground(Color.WHITE);
        caja.add(lbl, BorderLayout.CENTER);

        JPanel wrap = new JPanel(new GridBagLayout());
        wrap.setOpaque(false);
        wrap.setBorder(BorderFactory.createEmptyBorder(12, 22, 0, 22));
        wrap.add(caja, new GridBagConstraints());
        fondo.add(wrap, BorderLayout.CENTER);

        JButton ok = BotonesEstilo.crearBoton("  ACEPTAR  ", new Color(100, 200, 100));
        ok.setFont(new Font("Arial", Font.BOLD, 14));
        ok.addActionListener(e -> d.dispose());
        JPanel sur = new JPanel();
        sur.setOpaque(false);
        sur.add(ok);
        fondo.add(sur, BorderLayout.SOUTH);

        d.setVisible(true);
    }

    private void verificarFinJuego() {
        if (!partida.isActiva()) {
            Jugador ganador = partida.getGanador(false);
            if (ganador == null) {
                return;
            }
            Jugador perdedor = (ganador == jugadorLoggeado) ? oponente : jugadorLoggeado;
            terminarJuego(ganador, perdedor, false);
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
                    Jugador ganador = seRetiroRojo ? oponente : jugadorLoggeado;
                    Jugador perdedor = seRetiroRojo ? jugadorLoggeado : oponente;
                    terminarJuego(ganador, perdedor, true);
                    dispose();
                    menuPrincipal.volverAqui();
                });
    }
}
