package dao;

import model.Jogador;

import java.util.ArrayList;

public class CentralDeInformacoes {

    private ArrayList<Jogador> todosOsJogadores = new ArrayList<Jogador>();
    private ArrayList<Palavra> todasAsPalavras = new ArrayList<Palavra>();

    public boolean adicionarJogador(Jogador jogador){
        if(recuperarJogadorPorCPF(jogador.getCPF()) != null || recuperarJogadorPeloEmail(jogador.getEmail()) != null){
            System.out.println("model.Jogador já cadastrado!");
            return false;

        }
        todosOsJogadores.add(jogador);
        System.out.println("model.Jogador adicionado com sucesso!");
        return true;
    }

    public Jogador recuperarJogadorPorCPF(String CPF){
        for(Jogador j: todosOsJogadores){
            if(j.getCPF().equals(CPF)){
                return j;
            }
        }
        return null;
    }

    public Jogador recuperarJogadorPeloEmail(String email){
        for(Jogador j: todosOsJogadores){
            if(j.getEmail().equals(email)){
                return j;
            }
        }
        return null;
    }

    public ArrayList<Jogador> getTodosOsJogadores() {
        return todosOsJogadores;
    }

    public void setTodosOsJogadores(ArrayList<Jogador> todosOsJogadores) {
        this.todosOsJogadores = todosOsJogadores;
    }

    public boolean adicionarPalavra(Palavra palavra){
        if(recuperarPalavraPelaPalavra(palavra.getPalavra()) != null){
            System.out.println("model.Palavra já cadastrada!");
            return false;
        }
        todasAsPalavras.add(palavra);
        System.out.println("model.Palavra adicionada com sucesso!");
        return true;
    }

    public Palavra recuperarPalavraPelaPalavra(String palavra){
            for(Palavra p: todasAsPalavras){
                if(p.getPalavra().equals(palavra)){
                    return p;
                }
            }
            return null;
    }

    public ArrayList<Palavra> getTodasAsPalavras() {
        return todasAsPalavras;
    }

}
