package ma.hafidelmoudden.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private Double montant;
    
    @Enumerated(EnumType.STRING)
    private TypeRemboursement type;
    
    @ManyToOne
    private Credit credit;
} 