package dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.Jogador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Persistencia {
    public static final String ARQUIVO_CENTRAL = "central.xml";
    private static final String ARQUIVO_CENTRAL_ANTIGO = "nomeArquivo.xml";

    private XStream configurarXStream(){
        XStream xstream = new XStream(new DomDriver());

        // Ainda vou alterar pra não deixar tudo
        xstream.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);

        xstream.alias("jogador", Jogador.class);
        xstream.alias("central", CentralDeInformacoes.class);

        return xstream;
    }

    public boolean salvarCentral(CentralDeInformacoes central, String nomeArquivo) {
        XStream xstream = configurarXStream();

        String xml = xstream.toXML(central);
        File arquivo = resolverArquivo(nomeArquivo);

        try (PrintWriter writer = new PrintWriter(arquivo)) {
            writer.println(xml);
            if (writer.checkError()) {
                throw new IOException("Não foi possível gravar o arquivo XML.");
            }
            System.out.println("Central salva com sucesso em " + arquivo.getPath());
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao salva o arquivo XML: " + e.getMessage());
            return false;
        }
    }

    public CentralDeInformacoes recuperarCentral(String nomeArquivo) {
        File arquivo = resolverArquivo(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado. Nova central vazia será criada...");
            return new CentralDeInformacoes();
        }

        try (FileInputStream fis = new FileInputStream(arquivo)) {
            XStream xstream = configurarXStream();
            Object dados = xstream.fromXML(fis);
            if (!(dados instanceof CentralDeInformacoes)) {
                throw new IOException("O arquivo não contém uma central válida.");
            }
            return (CentralDeInformacoes) dados;
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erro ao recuperar o arquivo XML '" + nomeArquivo + "'.", e);
        }
    }

    private File resolverArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (ARQUIVO_CENTRAL.equals(nomeArquivo)
                && !arquivo.exists()
                && new File(ARQUIVO_CENTRAL_ANTIGO).exists()) {
            return new File(ARQUIVO_CENTRAL_ANTIGO);
        }
        return arquivo;
    }
}
