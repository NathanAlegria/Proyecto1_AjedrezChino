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
public class MiCuenta extends JFrame {
    private Jugador jugador;
    private GestorDatosImpl gestor;
    private MenuPrincipal menuPrincipal;

    public MiCuenta(Jugador jugador, GestorDatosImpl gestor, MenuPrincipal menuPrincipal) {
        this.jugador       = jugador;
        this.gestor        = gestor;
        this.menuPrincipal = menuPrincipal;

        setTitle("Mi Cuenta");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelFondo panel = new PanelFondo();
        panel.setLayout(new BorderLayout());
        setContentPane(panel);

        // Atrás
        JButton btnAtras = BotonesEstilo.crearBotonAtras();
        btnAtras.addActionListener(e -> { menuPrincipal.volverAqui(); dispose(); });
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.setOpaque(false);
        panelTop.add(btnAtras);
        panel.add(panelTop, BorderLayout.NORTH);

        JPanel cajaInfo = new JPanel(new GridLayout(3, 1, 0, 16)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 140));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        cajaInfo.setOpaque(false);
        cajaInfo.setBorder(BorderFactory.createEmptyBorder(24, 40, 24, 40));

        String[] datos = {
            "  Usuario:       " + jugador.getUsername(),
            "  Puntos:        " + jugador.getPuntos(),
            "  Registro:      " + jugador.getFechaIngreso()
        };
        for (String d : datos) {
            JLabel lbl = new JLabel(d);
            lbl.setFont(new Font("Arial", Font.PLAIN, 18));
            lbl.setForeground(Color.WHITE);
            cajaInfo.add(lbl);
        }

        JPanel centroCaja = new JPanel(new GridBagLayout());
        centroCaja.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 120, 0, 120);
        cajaInfo.setPreferredSize(new Dimension(560, 180));
        centroCaja.add(cajaInfo, gbc);
        panel.add(centroCaja, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 16));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 200, 50, 200));

        JButton btnCambiar  = BotonesEstilo.crearBoton("  Cambiar Password",   new Color(100, 180, 255));
        JButton btnEliminar = BotonesEstilo.crearBoton("  Eliminar Mi Cuenta",  new Color(200, 70, 70));
        btnCambiar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 16));

        btnCambiar.addActionListener(e -> {
            new VentanaCambiarPassword(jugador, this);
            setVisible(false);
        });
        btnEliminar.addActionListener(e -> {
            new VentanaEliminarCuenta(jugador, gestor, this, menuPrincipal);
            setVisible(false);
        });

        panelBotones.add(btnCambiar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void volverAqui() { setVisible(true); }
}