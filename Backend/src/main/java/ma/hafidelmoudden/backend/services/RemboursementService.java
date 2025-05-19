package ma.hafidelmoudden.backend.services;

import ma.hafidelmoudden.backend.dtos.RemboursementDTO;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;

import java.util.List;

public interface RemboursementService {
    RemboursementDTO saveRemboursement(RemboursementDTO remboursementDTO);
    RemboursementDTO getRemboursementById(Long id);
    List<RemboursementDTO> getAllRemboursements();
    RemboursementDTO updateRemboursement(RemboursementDTO remboursementDTO);
    void deleteRemboursement(Long id);
    List<RemboursementDTO> getRemboursementsByCreditId(Long creditId);
    List<RemboursementDTO> getRemboursementsByType(TypeRemboursement type);
    Double getTotalRemboursementsByCreditId(Long creditId);
    Double getRemainingAmountForCredit(Long creditId);
} 