package tech.devinhouse.dipm3projeto1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Confirmacao {
    @Id
    private String eticket;
    private String assento;
    private LocalDateTime dataHoraConfirmacao;

    public Confirmacao(String assento) {
        dataHoraConfirmacao = LocalDateTime.now();
        eticket = UUID.randomUUID().toString();
        this.assento = assento;
    }
}
