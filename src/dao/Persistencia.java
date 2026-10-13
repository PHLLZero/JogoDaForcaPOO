package dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.Jogador;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

public class Persistencia {

    private XStream configurarXStream(){
        XStream xstream = new XStream(new DomDriver());

        // Ainda vou alterar pra não deixar tudo
        xstream.addPermission(com.thoughtworks.xstream.security.AnyTypePermission.ANY);

        xstream.alias("jogador", Jogador.class);
        xstream.alias("central", CentralDeInformacoes.class);

        return xstream;
    }

    public void salvarCentral(CentralDeInformacoes central, String nomeArquivo) {
        XStream xstream = configurarXStream();

        String xml = xstream.toXML(central);

        try (PrintWriter writer = new PrintWriter(new File(nomeArquivo))) {
            writer.println(xml);
            System.out.println("Central salva com sucesso em " + nomeArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao salva o arquivo XML: " + e.getMessage());
        }
    }

    public CentralDeInformacoes recuperarCentral(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado. Nova central vazia será criada...");
            return new CentralDeInformacoes();
        }

        try (FileInputStream fis = new FileInputStream(arquivo)) {
            XStream xstream = configurarXStream();

            return (CentralDeInformacoes) xstream.fromXML(fis);
        } catch (Exception e) {
            System.out.println("Erro ao recuperar o arquivo XML. Retornando uma central vazia. \nErro: " + e.getMessage());
            return new CentralDeInformacoes();
        }
    }
}


