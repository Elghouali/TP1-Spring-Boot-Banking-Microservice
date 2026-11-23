package ma.enset.tp1.mapper;

import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.dto.CompteResponse;
import ma.enset.tp1.model.Compte;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Locale;

@Component
public class CompteMapper {
    public Compte toEntity(CompteRequest request) {
        return Compte.builder()
            .dateCreation(LocalDate.now())
            .solde(request.solde())
            .typeCompte(request.typeCompte())
            .devise(normalizeDevise(request.devise()))
            .build();
    }

    public CompteResponse toResponse(Compte compte) {
        return new CompteResponse(compte.getId(), compte.getDateCreation(), compte.getSolde(),
            compte.getTypeCompte(), compte.getDevise());
    }

    public void updateEntity(Compte compte, CompteRequest request) {
        compte.setSolde(request.solde());
        compte.setTypeCompte(request.typeCompte());
        compte.setDevise(normalizeDevise(request.devise()));
    }

    private String normalizeDevise(String devise) {
        return devise == null || devise.isBlank() ? "MAD" : devise.toUpperCase(Locale.ROOT);
    }
}
