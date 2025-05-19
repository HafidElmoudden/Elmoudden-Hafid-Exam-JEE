package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.CreditProfessionnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditProfessionnelRepository extends JpaRepository<CreditProfessionnel, Long> {
    List<CreditProfessionnel> findByMotif(String motif);
    List<CreditProfessionnel> findByRaisonSocialeEntreprise(String raisonSocialeEntreprise);
} 