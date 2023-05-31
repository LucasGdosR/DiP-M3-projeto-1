package tech.devinhouse.dipm3projeto1.dto;

import lombok.Getter;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;

import java.time.format.DateTimeFormatter;

@Getter
public class ConfirmacaoResponseDto {
    private final String eticket;
    private final String dataHoraConfirmacao;

    public ConfirmacaoResponseDto(Confirmacao confirmacao) {
        eticket = confirmacao.getEticket();
        dataHoraConfirmacao = confirmacao.getDataHoraConfirmacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
