package tech.devinhouse.dipm3projeto1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Passageiro {
    @Id
    @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
    private String cpf;
    @NotBlank @NotNull
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Classificacao classificacao;
    @NotNull
    private int milhas;
    @OneToOne
    private Confirmacao confirmacao;
}
