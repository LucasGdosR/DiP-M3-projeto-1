package tech.devinhouse.dipm3projeto1.model;

public enum Classificacao {
    VIP(100),
    OURO(80),
    PRATA(50),
    BRONZE(30),
    ASSOCIADO(10);

    public final int milhas;

    Classificacao(int milhas) {
        this.milhas = milhas;
    }
}
