package model;

public class Jogador {

    private String nome;
    private Sexo sexo;
    private String CPF;
    private String email;

    public Jogador(String nome, Sexo sexo, String CPF, String email) {
        this.nome = nome;
        this.sexo = sexo;
        this.CPF = CPF;
        this.email = email;
    }

    @Override
    public String toString(){
        return "Nome: " + nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

}
