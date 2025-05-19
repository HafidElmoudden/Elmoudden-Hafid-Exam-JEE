package ma.hafidelmoudden.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.hafidelmoudden.backend.enums.TypeBien;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditImmobilierDTO extends CreditDTO {
    private TypeBien typeBien;
} 