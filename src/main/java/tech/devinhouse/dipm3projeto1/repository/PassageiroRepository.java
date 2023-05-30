package tech.devinhouse.dipm3projeto1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.devinhouse.dipm3projeto1.model.Passageiro;

@Repository
public interface PassageiroRepository extends JpaRepository<Passageiro, String> {
}
