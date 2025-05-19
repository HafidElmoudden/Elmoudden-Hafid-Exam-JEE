package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.Credit;
import ma.hafidelmoudden.backend.enums.Statut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByClientId(Long clientId);
    List<Credit> findByStatut(Statut statut);
} 