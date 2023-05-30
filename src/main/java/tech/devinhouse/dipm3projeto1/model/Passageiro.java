package tech.devinhouse.dipm3projeto1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
public class Passageiro {
    @Id
    @CPF
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
}
