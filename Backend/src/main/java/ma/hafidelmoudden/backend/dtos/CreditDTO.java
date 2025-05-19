package ma.hafidelmoudden.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hafidelmoudden.backend.enums.Statut;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    private Long id;
    private Date dateDemande;
    private Statut statut;
    private Date dateAcception;
    private Double montant;
    private Integer dureeRemboursement;
    private Double tauxInteret;
    private Long clientId;
    private String clientNom;
    private String type; // Type of credit: PERSONNEL, IMMOBILIER, PROFESSIONNEL
    private List<RemboursementDTO> remboursements;
} 