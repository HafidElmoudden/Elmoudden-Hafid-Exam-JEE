package ma.hafidelmoudden.backend.mappers;

import ma.hafidelmoudden.backend.dtos.RemboursementDTO;
import ma.hafidelmoudden.backend.entities.Credit;
import ma.hafidelmoudden.backend.entities.Remboursement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RemboursementMapper {
    
    public RemboursementDTO fromRemboursement(Remboursement remboursement) {
        RemboursementDTO remboursementDTO = new RemboursementDTO();
        BeanUtils.copyProperties(remboursement, remboursementDTO);
        if (remboursement.getCredit() != null) {
            remboursementDTO.setCreditId(remboursement.getCredit().getId());
        }
        return remboursementDTO;
    }
    
    public Remboursement fromRemboursementDTO(RemboursementDTO remboursementDTO, Credit credit) {
        Remboursement remboursement = new Remboursement();
        BeanUtils.copyProperties(remboursementDTO, remboursement);
        remboursement.setCredit(credit);
        return remboursement;
    }
} 