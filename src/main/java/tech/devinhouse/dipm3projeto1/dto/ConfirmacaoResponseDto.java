package tech.devinhouse.dipm3projeto1.dto;

import tech.devinhouse.dipm3projeto1.model.Confirmacao;

public class ConfirmacaoResponseDto {
    private final String eticket;
    private final String dataHoraConfirmacao;

    public ConfirmacaoResponseDto(Confirmacao confirmacao) {
        eticket = confirmacao.getEticket();
        dataHoraConfirmacao = confirmacao.getDataHoraConfirmacao().toString();
    }
}
