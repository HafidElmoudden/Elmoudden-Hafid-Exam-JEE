package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.CreditPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditPersonnelRepository extends JpaRepository<CreditPersonnel, Long> {
    List<CreditPersonnel> findByMotif(String motif);
} 