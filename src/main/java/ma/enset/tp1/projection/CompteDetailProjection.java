package ma.enset.tp1.projection;

import ma.enset.tp1.model.Compte;
import ma.enset.tp1.model.TypeCompte;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;
import java.time.LocalDate;

@Projection(name = "detail", types = {Compte.class})
public interface CompteDetailProjection {
    Long getId();
    LocalDate getDateCreation();
    BigDecimal getSolde();
    TypeCompte getTypeCompte();
    String getDevise();
}
