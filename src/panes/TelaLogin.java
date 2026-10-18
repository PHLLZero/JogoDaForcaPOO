package panes;

import dao.CentralDeInformacoes;
import model.ADM;
import model.Jogador;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private final CentralDeInformacoes central;

    public TelaLogin(CentralDeInformacoes central) {
        this.central = central;

        setTitle("Login");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(new Color(30, 30, 30));
        add(painelPrincipal);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setPreferredSize(new Dimension(350, 300));
        painelPrincipal.add(formulario);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 20, 8, 20);

        JLabel titulo = new JLabel("ENTRAR", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        formulario.add(titulo, gbc);

        JLabel labelCPF = new JLabel("CPF");
        formulario.add(labelCPF, gbc);

        JTextField campoCPF = new JTextField();
        campoCPF.setPreferredSize(new Dimension(0, 40));
        formulario.add(campoCPF, gbc);

        JLabel labelSenha = new JLabel("Senha");
        formulario.add(labelSenha, gbc);

        JPasswordField campoSenha = new JPasswordField();
        campoSenha.setPreferredSize(new Dimension(0, 40));
        formulario.add(campoSenha, gbc);

        JButton botaoLogin = new JButton("Login");
        formulario.add(botaoLogin, gbc);

        JButton botaoCadastrar = new JButton("Cadastrar");
        formulario.add(botaoCadastrar, gbc);

        JButton botaoRecuperarSenha = new JButton("Recuperar senha");
        formulario.add(botaoRecuperarSenha, gbc);

        botaoLogin.addActionListener(evento ->
                autenticar(campoCPF.getText().trim(), new String(campoSenha.getPassword())));
        botaoCadastrar.addActionListener(evento -> abrirCadastro());
        botaoRecuperarSenha.addActionListener(evento ->
                JOptionPane.showMessageDialog(
                        this,
                        "Ainda vou fazer a tela de recuperação de senha!",
                        "Recuperar senha",
                        JOptionPane.INFORMATION_MESSAGE));
    }

    private void autenticar(String cpf, String senha) {
        if (cpf.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o CPF e a senha.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ADM admin = central.getADM();
        if (credenciaisConferem(admin, cpf, senha)) {
            exibirLoginBemSucedido(admin);
            return;
        }

        for (Jogador jogador : central.getTodosOsJogadores()) {
            if (credenciaisConferem(jogador, cpf, senha)) {
                exibirLoginBemSucedido(jogador);
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "CPF ou senha incorretos.",
                "Falha no login",
                JOptionPane.ERROR_MESSAGE);
    }

    private boolean credenciaisConferem(Usuario usuario, String cpf, String senha) {
        return usuario != null
                && cpf.equals(usuario.getCPF())
                && senha.equals(usuario.getSenha());
    }

    private void exibirLoginBemSucedido(Usuario usuario) {
        JOptionPane.showMessageDialog(
                this,
                "Login realizado com sucesso. Bem-vindo(a), " + usuario.getNome() + "!",
                "Login",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirCadastro() {
        setVisible(false);
        if (central.getADM() == null) {
            new TelaCadastroADM(central, () -> setVisible(true)).setVisible(true);
        } else {
            new TelaCadastroJogador(central, () -> setVisible(true)).setVisible(true);
        }
    }
}
