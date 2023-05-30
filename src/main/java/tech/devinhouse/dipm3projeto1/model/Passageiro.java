package tech.devinhouse.dipm3projeto1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Passageiro {
    @Id
    private String cpf;
}
