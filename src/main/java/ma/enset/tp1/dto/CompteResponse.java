package ma.enset.tp1.dto;

import ma.enset.tp1.model.TypeCompte;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CompteResponse(
    Long id,
    LocalDate dateCreation,
    BigDecimal solde,
    TypeCompte typeCompte,
    String devise
) {}
