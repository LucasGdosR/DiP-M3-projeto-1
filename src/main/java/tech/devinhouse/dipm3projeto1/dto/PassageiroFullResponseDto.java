package tech.devinhouse.dipm3projeto1.dto;

import lombok.Getter;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;
import tech.devinhouse.dipm3projeto1.model.Passageiro;

import java.time.format.DateTimeFormatter;

@Getter
public class PassageiroFullResponseDto {
    private final String cpf;
    private final String nome;
    private final String dataNascimento;
    private final String classificacao;
    private final int milhas;
    private String eticket;
    private String assento;
    private String dataHoraConfirmacao;

    public PassageiroFullResponseDto(Passageiro passageiro) {
        cpf = passageiro.getCpf();
        nome = passageiro.getNome();
        dataNascimento = passageiro.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        classificacao = passageiro.getClassificacao().toString();
        milhas = passageiro.getMilhas();

        Confirmacao confirmacao = passageiro.getConfirmacao();

        if (confirmacao != null) {
            eticket = passageiro.getConfirmacao().getEticket();
            assento = passageiro.getConfirmacao().getAssento();
            dataHoraConfirmacao = passageiro.getConfirmacao().getDataHoraConfirmacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        }
    }
}
