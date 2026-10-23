package panes;

import dao.CentralDeInformacoes;
import dao.Persistencia;
import model.ADM;
import model.Jogador;
import model.Usuario;
import utils.Mensageiro;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TelaLogin extends JFrame {

    private static final SecureRandom GERADOR_CODIGO = new SecureRandom();
    private static final long VALIDADE_CODIGO_MS = 10 * 60 * 1000;

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
        botaoRecuperarSenha.addActionListener(evento -> iniciarRecuperacaoSenha(botaoRecuperarSenha));
    }

    private void iniciarRecuperacaoSenha(JButton botaoRecuperarSenha) {
        String email = JOptionPane.showInputDialog(
                this,
                "Informe o e-mail cadastrado:",
                "Recuperar senha",
                JOptionPane.QUESTION_MESSAGE);
        if (email == null) {
            return;
        }
        email = email.trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe um e-mail.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = central.recuperarUsuarioPeloEmail(email);
        if (usuario == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não há uma conta cadastrada com esse e-mail.",
                    "Conta não encontrada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = String.format(Locale.ROOT, "%06d", GERADOR_CODIGO.nextInt(1_000_000));
        long instanteEnvio = System.currentTimeMillis();
        JDialog carregando = new JDialog(this, "Recuperar senha", true);
        carregando.add(new JLabel("Enviando o código para o seu e-mail..."), BorderLayout.CENTER);
        carregando.setSize(320, 90);
        carregando.setLocationRelativeTo(this);
        carregando.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        SwingWorker<Boolean, Void> envio = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return Mensageiro.enviarMensagem(
                        usuario.getEmail(),
                        "Código para recuperar sua senha",
                        "Seu código de recuperação de senha é: " + codigo
                                + "\n\nEle é válido por 10 minutos.");
            }

            protected void done() {
                carregando.dispose();
                try {
                    if (get()) {
                        confirmarCodigoEAlterarSenha(usuario, codigo, instanteEnvio);
                    } else {
                        JOptionPane.showMessageDialog(
                                TelaLogin.this,
                                "Não foi possível enviar o código. Tente novamente mais tarde.",
                                "Falha no envio",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (InterruptedException erro) {
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(
                            TelaLogin.this,
                            "O envio do código foi interrompido.",
                            "Falha no envio",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ExecutionException erro) {
                    JOptionPane.showMessageDialog(
                            TelaLogin.this,
                            "Não foi possível enviar o código: " + erro.getCause().getMessage(),
                            "Falha no envio",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    botaoRecuperarSenha.setEnabled(true);
                }
            }
        };

        botaoRecuperarSenha.setEnabled(false);
        envio.execute();
        carregando.setVisible(true);
    }

    private void confirmarCodigoEAlterarSenha(Usuario usuario, String codigo, long instanteEnvio) {
        boolean codigoValido = false;
        for (int tentativa = 1; tentativa <= 5; tentativa++) {
            String codigoInformado = JOptionPane.showInputDialog(
                    this,
                    "Informe o código numérico enviado para " + usuario.getEmail() + ":",
                    "Confirmar identidade",
                    JOptionPane.QUESTION_MESSAGE);
            if (codigoInformado == null) {
                return;
            }
            if (System.currentTimeMillis() - instanteEnvio > VALIDADE_CODIGO_MS) {
                JOptionPane.showMessageDialog(
                        this,
                        "O código expirou. Solicite um novo código.",
                        "Código expirado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (codigo.equals(codigoInformado.trim())) {
                codigoValido = true;
                break;
            }
            JOptionPane.showMessageDialog(
                    this,
                    tentativa == 5
                            ? "Limite de tentativas atingido. Solicite um novo código."
                            : "Código incorreto. Tente novamente.",
                    "Código inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (!codigoValido) {
            return;
        }

        String novaSenha = solicitarNovaSenha();
        if (novaSenha == null) {
            return;
        }

        String senhaAnterior = usuario.getSenha();
        usuario.setSenha(novaSenha);
        if (!new Persistencia().salvarCentral(central, Persistencia.ARQUIVO_CENTRAL)) {
            usuario.setSenha(senhaAnterior);
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível salvar a nova senha.",
                    "Erro ao salvar",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Senha alterada com sucesso.",
                "Recuperar senha",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String solicitarNovaSenha() {
        while (true) {
            JPasswordField campoNovaSenha = new JPasswordField(16);
            JPasswordField campoConfirmacao = new JPasswordField(16);
            JPanel formulario = new JPanel(new GridLayout(0, 1, 0, 5));
            formulario.add(new JLabel("Nova senha:"));
            formulario.add(campoNovaSenha);
            formulario.add(new JLabel("Confirme a nova senha:"));
            formulario.add(campoConfirmacao);

            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    formulario,
                    "Cadastrar nova senha",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if (opcao != JOptionPane.OK_OPTION) {
                return null;
            }

            String senha = new String(campoNovaSenha.getPassword());
            String confirmacao = new String(campoConfirmacao.getPassword());
            if (senha.isEmpty() || confirmacao.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha os dois campos de senha.",
                        "Dados incompletos",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!senha.equals(confirmacao)) {
                JOptionPane.showMessageDialog(
                        this,
                        "As senhas informadas não coincidem.",
                        "Senhas diferentes",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                return senha;
            }
        }
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
