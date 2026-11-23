package ma.enset.tp1.service;

import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.dto.CompteResponse;
import java.math.BigDecimal;
import java.util.List;

public interface CompteService {
    List<CompteResponse> tousLesComptes();
    CompteResponse trouverCompte(Long id);
    CompteResponse creerCompte(CompteRequest request);
    CompteResponse modifierCompte(Long id, CompteRequest request);
    CompteResponse deposer(Long id, BigDecimal montant);
    CompteResponse retirer(Long id, BigDecimal montant);
    void supprimerCompte(Long id);
}
