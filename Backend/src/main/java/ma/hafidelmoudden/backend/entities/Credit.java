package ma.hafidelmoudden.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hafidelmoudden.backend.enums.Statut;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_CREDIT")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateDemande;
    
    @Enumerated(EnumType.STRING)
    private Statut statut;
    
    private Date dateAcception;
    private Double montant;
    private Integer dureeRemboursement;
    private Double tauxInteret;
    
    @ManyToOne
    private Client client;
    
    @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY)
    private List<Remboursement> remboursements;
} 