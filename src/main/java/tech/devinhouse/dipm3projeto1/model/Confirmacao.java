package tech.devinhouse.dipm3projeto1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Confirmacao {
    @Id
    private String eticket = UUID.randomUUID().toString();
}
