package dao;

import model.ADM;
import model.Jogador;
import model.Palavra;

import java.util.ArrayList;

public class CentralDeInformacoes {

    private ArrayList<Jogador> todosOsJogadores = new ArrayList<Jogador>();
    private ArrayList<Palavra> todasAsPalavras = new ArrayList<Palavra>();
    private ADM adm;

    public boolean setADM(ADM adm) {
        if (this.adm != null || adm == null) {
            return false;
        }
        this.adm = adm;
        return true;
    }

    public boolean removerADM(ADM adm) {
        if (this.adm != adm) {
            return false;
        }
        this.adm = null;
        return true;
    }


    public ADM getADM() {
        return adm;
    }


    public boolean adicionarJogador(Jogador jogador){
        if(recuperarJogadorPorCPF(jogador.getCPF()) != null || recuperarJogadorPeloEmail(jogador.getEmail()) != null){
            System.out.println("model.Jogador já cadastrado!");
            return false;

        }
        todosOsJogadores.add(jogador);
        System.out.println("model.Jogador adicionado com sucesso!");
        return true;
    }

    public boolean removerJogador(Jogador jogador) {
        return todosOsJogadores.remove(jogador);
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
