package panes;

import dao.CentralDeInformacoes;
import dao.Persistencia;
import enums.Sexo;
import model.Jogador;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroJogador extends JFrame {

    private final Runnable aoConcluir;

    public TelaCadastroJogador(CentralDeInformacoes central) {
        this(central, null);
    }

    public TelaCadastroJogador(CentralDeInformacoes central, Runnable aoConcluir) {
        this.aoConcluir = aoConcluir;
        setTitle("Cadastro do Jogador");
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

        JLabel titulo = new JLabel("CADASTRO DE JOGADOR");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        cartaoCadastro.add(titulo, gbc);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelNome, gbc);

        JTextField campoNome = new JTextField();
        campoNome.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(campoNome, gbc);

        JLabel labelEmail = new JLabel("E-mail");
        labelEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelEmail, gbc);

        JTextField campoEmail = new JTextField();
        campoEmail.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(campoEmail, gbc);

        JLabel labelCPF = new JLabel("CPF");
        labelCPF.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelCPF, gbc);

        JTextField campoCPF = new JTextField();
        campoCPF.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(campoCPF, gbc);

        JLabel labelSexo = new JLabel("Sexo");
        labelSexo.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelSexo, gbc);

        JComboBox<Sexo> campoSexo = new JComboBox<>(Sexo.values());
        campoSexo.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(campoSexo, gbc);

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(labelSenha, gbc);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(0, 40));
        cartaoCadastro.add(campoSenha, gbc);

        JButton botaoCadastrar = new JButton("Cadastrar jogador");
        botaoCadastrar.setFont(new Font("SansSerif", Font.BOLD, 12));
        cartaoCadastro.add(botaoCadastrar, gbc);

        botaoCadastrar.addActionListener(evento -> {
            String nome = campoNome.getText().trim();
            String email = campoEmail.getText().trim();
            String cpf = campoCPF.getText().trim();
            String senha = new String(campoSenha.getPassword());

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.",
                        "Dados incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Jogador jogador = new Jogador(
                    nome, (Sexo) campoSexo.getSelectedItem(), cpf, email, senha, 0);
            if (!central.adicionarJogador(jogador)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um jogador cadastrado com esse CPF ou e-mail.",
                        "Cadastro não realizado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Persistencia persistencia = new Persistencia();
            if (!persistencia.salvarCentral(central, Persistencia.ARQUIVO_CENTRAL)) {
                central.removerJogador(jogador);
                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível salvar o cadastro do jogador.",
                        "Erro ao salvar",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso.");
            dispose();
            if (aoConcluir != null) {
                aoConcluir.run();
            }
        });
    }
}
