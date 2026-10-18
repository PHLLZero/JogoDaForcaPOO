package panes;

import dao.CentralDeInformacoes;
import dao.Persistencia;
import model.ADM;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroADM extends JFrame {

    private final CentralDeInformacoes central;
    private final Runnable aoConcluir;

    public TelaCadastroADM(CentralDeInformacoes central) {
        this(central, null);
    }

    public TelaCadastroADM(CentralDeInformacoes central, Runnable aoConcluir) {
        this.central = central;
        this.aoConcluir = aoConcluir;

        setTitle("Cadastro do Administrador");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel containerPrincipal = new JPanel(new GridBagLayout());
        containerPrincipal.setBackground(new Color(30, 30, 30));
        add(containerPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 30, 5, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JPanel cartaoCadastro = new JPanel(new GridBagLayout());
        cartaoCadastro.setPreferredSize(new Dimension(400, 580));
        containerPrincipal.add(cartaoCadastro);

        JLabel lblTitulo = new JLabel("CRIAR CONTA");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        cartaoCadastro.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Cadastre sua Conta de Administrador");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setForeground(new Color(160, 160, 160));
        cartaoCadastro.add(lblSubtitulo, gbc);

        JLabel labelNome = new JLabel("Primeiro Nome");
        labelNome.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelNome, gbc);

        JTextField textoNome = new JTextField();
        textoNome.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(textoNome, gbc);

        JLabel labelEmail = new JLabel("E-mail de Acesso");
        labelEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelEmail, gbc);

        JTextField textoEmail = new JTextField();
        textoEmail.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(textoEmail, gbc);

        JLabel labelCPF = new JLabel("CPF");
        labelCPF.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelCPF, gbc);

        JTextField textoCPF = new JTextField();
        textoCPF.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(textoCPF, gbc);

        JLabel labelSenha = new JLabel("Senha (Mínimo 8 dígitos)");
        labelSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelSenha, gbc);

        JPasswordField senha = new JPasswordField();
        senha.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(senha, gbc);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCadastrar.setBackground(new Color(30, 30, 30));
        btnCadastrar.setForeground(new Color(160, 160, 160));
        cartaoCadastro.add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> {
            String nome = textoNome.getText();
            String email = textoEmail.getText();
            String cpf = textoCPF.getText();
            String senhaCapturada = new String(senha.getPassword());

            ADM admin = new ADM(nome, cpf, email, senhaCapturada);
            if (!this.central.setADM(admin)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um administrador cadastrado.",
                        "Cadastro não realizado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Persistencia persistencia = new Persistencia();
            if (!persistencia.salvarCentral(this.central, Persistencia.ARQUIVO_CENTRAL)) {
                this.central.removerADM(admin);
                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível salvar o cadastro do administrador.",
                        "Erro ao salvar",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();
            if (aoConcluir != null) {
                aoConcluir.run();
            }
        });
    }
}
