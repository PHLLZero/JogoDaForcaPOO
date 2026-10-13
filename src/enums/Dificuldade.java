package enums;

public enum Dificuldade {
    FACIL(0), // (X) setando codigo pra depois usar no Extrator de csv
    MEDIO(1),
    DIFICIL(2);

    private final int codigo;

    Dificuldade(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo(){
        return codigo;
    }

    public static Dificuldade deCodigo(int codigo){
        for (Dificuldade d: Dificuldade.values()){
            if (d.getCodigo() == codigo){       // Comparação de numero do csv e codigo da dificuldade
                return d;
            }
        }
        return null;
    }
}
