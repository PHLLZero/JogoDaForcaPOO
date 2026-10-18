package panes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

public class TelaInicio extends JFrame {
    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private final Runnable aoIniciar;
    private boolean iniciada;
    private boolean dispatcherRegistrado;
    private final KeyEventDispatcher keyEventDispatcher = evento -> {
        if (isActive() && evento.getID() == KeyEvent.KEY_PRESSED) {
            iniciar();
            return true;
        }
        return false;
    };

    public TelaInicio() {
        this(null);
    }

    public TelaInicio(Runnable aoIniciar) {
        this.aoIniciar = aoIniciar;
        setTitle("Organizador de Campeonato de Forca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        painelPrincipal.setBackground(Color.DARK_GRAY);

        JPanel painelInicial = new JPanel(new BorderLayout());
        painelInicial.setBackground(Color.DARK_GRAY);

        JLabel mensagem = new JLabel("Aperte qualquer botão para iniciar", SwingConstants.CENTER);
        mensagem.setForeground(Color.WHITE);
        mensagem.setFont(new Font("SansSerif", Font.BOLD, 24));
        painelInicial.add(mensagem, BorderLayout.CENTER);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evento) {
                iniciar();
            }
        };
        painelInicial.addMouseListener(mouseAdapter);
        mensagem.addMouseListener(mouseAdapter);

        painelPrincipal.add(painelInicial, "inicio");
        add(painelPrincipal);
        mostrarTela("inicio");

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(keyEventDispatcher);
        dispatcherRegistrado = true;
    }

    public void mostrarTela(String nomeDaTela) {
        cardLayout.show(painelPrincipal, nomeDaTela);
    }

    private void iniciar() {
        if (iniciada) {
            return;
        }

        iniciada = true;
        dispose();
        if (aoIniciar != null) {
            aoIniciar.run();
        }
    }

    @Override
    public void dispose() {
        if (dispatcherRegistrado) {
            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .removeKeyEventDispatcher(keyEventDispatcher);
            dispatcherRegistrado = false;
        }
        super.dispose();
    }
}