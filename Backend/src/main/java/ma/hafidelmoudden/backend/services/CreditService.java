package ma.hafidelmoudden.backend.services;

import ma.hafidelmoudden.backend.dtos.CreditDTO;
import ma.hafidelmoudden.backend.dtos.CreditImmobilierDTO;
import ma.hafidelmoudden.backend.dtos.CreditPersonnelDTO;
import ma.hafidelmoudden.backend.dtos.CreditProfessionnelDTO;
import ma.hafidelmoudden.backend.enums.Statut;

import java.util.List;

public interface CreditService {
    
    CreditDTO getCreditById(Long id);
    List<CreditDTO> getAllCredits();
    void deleteCredit(Long id);
    List<CreditDTO> getCreditsByClientId(Long clientId);
    List<CreditDTO> getCreditsByStatut(Statut statut);
    CreditDTO updateCreditStatut(Long creditId, Statut statut);
    
    
    CreditPersonnelDTO saveCreditPersonnel(CreditPersonnelDTO creditDTO);
    CreditPersonnelDTO updateCreditPersonnel(CreditPersonnelDTO creditDTO);
    List<CreditPersonnelDTO> getCreditPersonnelByMotif(String motif);
    
    CreditImmobilierDTO saveCreditImmobilier(CreditImmobilierDTO creditDTO);
    CreditImmobilierDTO updateCreditImmobilier(CreditImmobilierDTO creditDTO);
    
    CreditProfessionnelDTO saveCreditProfessionnel(CreditProfessionnelDTO creditDTO);
    CreditProfessionnelDTO updateCreditProfessionnel(CreditProfessionnelDTO creditDTO);
    

    Double getTotalCreditAmount();
    Long getCountByStatut(Statut statut);
    Double getAverageInterestRate();
} 