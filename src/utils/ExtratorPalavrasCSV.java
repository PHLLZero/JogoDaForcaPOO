package utils;

import enums.Dificuldade;
import model.Palavra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ExtratorPalavrasCSV {

    public static ArrayList<Palavra> extrairPalavras(String nomeArquivo){
        ArrayList<Palavra> listaPalavras = new ArrayList<>();

        File arquivo = new File(nomeArquivo);

        if (!arquivo.exists() && !nomeArquivo.toLowerCase().endsWith(".csv")) {
            arquivo = new File(nomeArquivo + ".csv");
        }

        if (!arquivo.exists()) {
            File arquivoSrc = new File("src", nomeArquivo);
            if (!arquivoSrc.exists() && !nomeArquivo.toLowerCase().endsWith(".csv")) {
                arquivoSrc = new File("src", nomeArquivo + ".csv");
            }
            if (arquivoSrc.exists()) {
                arquivo = arquivoSrc;
            }
        }

        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
            String linha;
            int numeroLinha = 0;

            while ((linha = br.readLine()) != null){
                numeroLinha++;
                if (linha.trim().isEmpty()){
                    continue;
                }

                String[] partes = linha.contains(";") ? linha.split(";") : linha.split(",");

                if (partes.length != 3){
                    System.out.println("Erro na linha " + numeroLinha + ": formato inválido. Esperado 3 campos, encontrado " + partes.length);
                    return null;
                }

                String termo = partes[0].trim();

                int codigoDificuldade;
                try {
                    codigoDificuldade = Integer.parseInt(partes[1].trim());
                } catch (NumberFormatException e){
                    System.out.println("Erro na linha " + numeroLinha + ": código de dificuldade deve ser um número inteiro.");
                    return null;
                }

                String dica = partes[2].trim();

                Dificuldade dif = Dificuldade.deCodigo(codigoDificuldade);

                if (dif == null){
                    System.out.println("Erro na linha " + numeroLinha + ": código de dificuldade " + codigoDificuldade + " é inválido (use 0, 1 ou 2).");
                    return null;
                }

                Palavra p = new Palavra(termo, dif, dica);
                listaPalavras.add(p);

            }
        } catch (Exception e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return null;
        }
        return listaPalavras;
    }

}
