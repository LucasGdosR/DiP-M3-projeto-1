package tech.devinhouse.dipm3projeto1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmacaoRequestDto {
    @NotBlank
    private String cpf;
    @NotBlank
    private String assento;
    @NotNull
    private Boolean malasDespachadas;
}
