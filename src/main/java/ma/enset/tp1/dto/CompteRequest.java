package ma.enset.tp1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import ma.enset.tp1.model.TypeCompte;
import java.math.BigDecimal;

public record CompteRequest(
    @NotNull @DecimalMin("0.0") BigDecimal solde,
    @NotNull TypeCompte typeCompte,
    @Pattern(regexp = "(?i)[A-Z]{3}", message = "Code devise ISO a trois lettres") String devise
) {}
