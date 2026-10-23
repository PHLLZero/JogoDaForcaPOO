package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mensageiro {

    private static final String emailRemetente = "mensageirojogodaforca@gmail.com";

    private static final String senhaAPP = "zlsn gzza ewxz lciu";

    // padrao
    private static Session conectarServer() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailRemetente, senhaAPP);
            }
        });
    }

    public static boolean enviarMensagem(String emailDestinatario, String assunto, String mensagemTexto) {
        if (emailDestinatario == null || emailDestinatario.trim().isEmpty()) {
            System.out.println("E-mail do destinatário está vazio ou inválido.");
            return false;
        }
        emailDestinatario = emailDestinatario.trim();

        try {
            Message message = new MimeMessage(conectarServer());

            message.setFrom(new InternetAddress(emailRemetente));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
            message.setSubject(assunto);

            message.setText(mensagemTexto);

            System.out.println("Enviando e-mail para " + emailDestinatario + "...");
            Transport.send(message);
            System.out.println("E-mail enviado com sucesso para: " + emailDestinatario);
            return true;

        } catch (MessagingException erroDeEnvio) {
            System.out.println("Erro ao tentar enviar o e-mail: " + erroDeEnvio.getMessage());
            return false;
        }
    }
}