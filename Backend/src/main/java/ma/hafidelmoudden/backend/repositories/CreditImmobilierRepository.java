package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.CreditImmobilier;
import ma.hafidelmoudden.backend.enums.TypeBien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditImmobilierRepository extends JpaRepository<CreditImmobilier, Long> {
    List<CreditImmobilier> findByTypeBien(TypeBien typeBien);
} 