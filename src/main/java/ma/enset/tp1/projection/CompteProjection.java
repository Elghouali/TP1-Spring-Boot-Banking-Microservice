package ma.enset.tp1.projection;

import ma.enset.tp1.model.Compte;
import ma.enset.tp1.model.TypeCompte;
import org.springframework.data.rest.core.config.Projection;
import java.math.BigDecimal;

@Projection(name = "resume", types = {Compte.class})
public interface CompteProjection {
    Long getId();
    BigDecimal getSolde();
    TypeCompte getTypeCompte();
}
