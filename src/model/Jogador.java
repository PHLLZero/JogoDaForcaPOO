package model;

import enums.Sexo;

public class Jogador extends Usuario {

    private Sexo sexo;

    private Integer pontuacao;

    public Jogador(String nome, Sexo sexo, String CPF, String email, String senha, Integer pontuacao) {
        super(nome, CPF, email, senha);
        this.sexo = sexo;
        this.pontuacao = 0;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }



}
