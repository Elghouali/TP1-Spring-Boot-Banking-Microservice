package ma.enset.tp1.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MontantRequest(@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal montant) {}
