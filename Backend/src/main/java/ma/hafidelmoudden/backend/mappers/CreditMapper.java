package ma.hafidelmoudden.backend.mappers;

import ma.hafidelmoudden.backend.dtos.*;
import ma.hafidelmoudden.backend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CreditMapper {
    
    private final RemboursementMapper remboursementMapper;
    
    public CreditMapper(RemboursementMapper remboursementMapper) {
        this.remboursementMapper = remboursementMapper;
    }
    
    public CreditDTO fromCredit(Credit credit) {
        CreditDTO creditDTO;
        
        if (credit instanceof CreditPersonnel) {
            CreditPersonnelDTO dto = new CreditPersonnelDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setMotif(((CreditPersonnel) credit).getMotif());
            dto.setType("PERSONNEL");
            creditDTO = dto;
        } else if (credit instanceof CreditImmobilier) {
            CreditImmobilierDTO dto = new CreditImmobilierDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setTypeBien(((CreditImmobilier) credit).getTypeBien());
            dto.setType("IMMOBILIER");
            creditDTO = dto;
        } else if (credit instanceof CreditProfessionnel) {
            CreditProfessionnelDTO dto = new CreditProfessionnelDTO();
            BeanUtils.copyProperties(credit, dto);
            dto.setMotif(((CreditProfessionnel) credit).getMotif());
            dto.setRaisonSocialeEntreprise(((CreditProfessionnel) credit).getRaisonSocialeEntreprise());
            dto.setType("PROFESSIONNEL");
            creditDTO = dto;
        } else {
            creditDTO = new CreditDTO();
            BeanUtils.copyProperties(credit, creditDTO);
        }
        
        if (credit.getClient() != null) {
            creditDTO.setClientId(credit.getClient().getId());
            creditDTO.setClientNom(credit.getClient().getNom());
        }
        
        if (credit.getRemboursements() != null) {
            creditDTO.setRemboursements(credit.getRemboursements().stream()
                    .map(remboursementMapper::fromRemboursement).toList());
        }
        
        return creditDTO;
    }
    
    public Credit fromCreditDTO(CreditDTO creditDTO, Client client) {
        Credit credit;
        
        if (creditDTO instanceof CreditPersonnelDTO) {
            CreditPersonnel creditPersonnel = new CreditPersonnel();
            BeanUtils.copyProperties(creditDTO, creditPersonnel);
            creditPersonnel.setMotif(((CreditPersonnelDTO) creditDTO).getMotif());
            credit = creditPersonnel;
        } else if (creditDTO instanceof CreditImmobilierDTO) {
            CreditImmobilier creditImmobilier = new CreditImmobilier();
            BeanUtils.copyProperties(creditDTO, creditImmobilier);
            creditImmobilier.setTypeBien(((CreditImmobilierDTO) creditDTO).getTypeBien());
            credit = creditImmobilier;
        } else if (creditDTO instanceof CreditProfessionnelDTO) {
            CreditProfessionnel creditProfessionnel = new CreditProfessionnel();
            BeanUtils.copyProperties(creditDTO, creditProfessionnel);
            creditProfessionnel.setMotif(((CreditProfessionnelDTO) creditDTO).getMotif());
            creditProfessionnel.setRaisonSocialeEntreprise(((CreditProfessionnelDTO) creditDTO).getRaisonSocialeEntreprise());
            credit = creditProfessionnel;
        } else {
            credit = new Credit();
            BeanUtils.copyProperties(creditDTO, credit);
        }
        
        credit.setClient(client);
        return credit;
    }
} 