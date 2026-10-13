package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dao.CentralDeInformacoes;
import model.Jogador;

import java.io.FileOutputStream;
import java.io.IOException;

public class GeradorDeRelatorios {

    public static void gerarRelatorio(CentralDeInformacoes central) {
        Document document = new Document();
        String caminhoArquivo = "relatorio_Central_De_informacoes.pdf";

        try {

            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);     //Peguei as fontes direto do itext
            Font fonteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fonteTexto = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph titulo = new Paragraph("Relatório de jogadores e palavras\n\n", fonteTitulo);
            document.add(titulo);

            Paragraph pJogadores = new Paragraph("Lista de Jogadores:", fonteSubtitulo);
            document.add(pJogadores);

            if (central.getTodosOsJogadores().isEmpty()) {
                document.add(new Paragraph("Nenhum jogador cadastrado.\n\n", fonteTexto));
            } else {
                for (Jogador jogador : central.getTodosOsJogadores()) {
                    document.add(new Paragraph("-> " + jogador.toString(), fonteTexto));
                }
                document.add(new Paragraph("\n"));
            }

            Paragraph pPalavras = new Paragraph("Lista de Palavras:", fonteSubtitulo);
            document.add(pPalavras);

            if (central.getTodasAsPalavras().isEmpty()) {
                document.add(new Paragraph("Nenhuma palavra cadastrada.\n", fonteTexto));
            } else {
                for (Object palavra : central.getTodasAsPalavras()) {
                    document.add(new Paragraph("-> " + palavra.toString(), fonteTexto));
                }
            }

            System.out.println("Relatório PDF gerado com sucesso em: " + caminhoArquivo);

        } catch (DocumentException | IOException e) {       // <------- Alterar depois
            System.err.println("Erro ao gerar o PDF: " + e.getMessage());
        }

        document.close();

    }
}