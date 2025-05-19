package ma.hafidelmoudden.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hafidelmoudden.backend.enums.TypeRemboursement;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemboursementDTO {
    private Long id;
    private Date date;
    private Double montant;
    private TypeRemboursement type;
    private Long creditId;
} 