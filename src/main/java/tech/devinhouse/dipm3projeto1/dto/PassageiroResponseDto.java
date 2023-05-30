package tech.devinhouse.dipm3projeto1.dto;

import tech.devinhouse.dipm3projeto1.model.Passageiro;

public class PassageiroResponseDto {
    private final String cpf;
    private final String nome;
    private final String dataNascimento;
    private final String classificacao;
    private final int milhas;

    public PassageiroResponseDto(Passageiro passageiro) {
        cpf = passageiro.getCpf();
        nome = passageiro.getNome();
        dataNascimento = passageiro.getDataNascimento().toString();
        classificacao = passageiro.getClassificacao().toString();
        milhas = passageiro.getMilhas();
    }
}
