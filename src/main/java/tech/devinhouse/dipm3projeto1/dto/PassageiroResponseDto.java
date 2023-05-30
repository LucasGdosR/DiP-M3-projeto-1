package tech.devinhouse.dipm3projeto1.dto;

import lombok.Getter;
import tech.devinhouse.dipm3projeto1.model.Passageiro;

import java.time.format.DateTimeFormatter;

@Getter
public class PassageiroResponseDto {
    private final String cpf;
    private final String nome;
    private final String dataNascimento;
    private final String classificacao;
    private final int milhas;

    public PassageiroResponseDto(Passageiro passageiro) {
        cpf = passageiro.getCpf();
        nome = passageiro.getNome();
        dataNascimento = passageiro.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        classificacao = passageiro.getClassificacao().toString();
        milhas = passageiro.getMilhas();
    }
}
