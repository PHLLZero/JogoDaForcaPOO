package model;

import java.time.LocalDate;

public class Palavra {
    private String palavra;
    private String dica;
    private LocalDate dataCadastro;
    private Dificuldade nivelDificuldade;

    public Palavra(String palavra, Dificuldade nivelDificuldade, String dica) {
        this.palavra = palavra;
        this.dica = dica;
        this.nivelDificuldade = nivelDificuldade;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Dificuldade getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(Dificuldade nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    public boolean equals(Palavra p){
        if (getPalavra().equals(p.getPalavra())){
            return true;
        }
        return false;
    }

    public String toString(){
        return "model.Palavra: " + this.palavra + "\nDica: " + this.dica;
    }

}