package tech.devinhouse.dipm3projeto1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.dipm3projeto1.model.Confirmacao;

public interface ConfirmacaoRepository extends JpaRepository<Confirmacao, String> {
    boolean existsByAssento(String assento);
}
