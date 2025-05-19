package ma.hafidelmoudden.backend.repositories;

import ma.hafidelmoudden.backend.entities.Remboursement;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RemboursementRepository extends JpaRepository<Remboursement, Long> {
    List<Remboursement> findByCreditId(Long creditId);
    List<Remboursement> findByType(TypeRemboursement type);
} 