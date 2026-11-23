package ma.enset.tp1.service;

import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.dto.CompteResponse;
import ma.enset.tp1.exception.CompteNotFoundException;
import ma.enset.tp1.exception.OperationBancaireException;
import ma.enset.tp1.mapper.CompteMapper;
import ma.enset.tp1.model.Compte;
import ma.enset.tp1.repository.CompteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    private final CompteRepository repository;
    private final CompteMapper mapper;

    public CompteServiceImpl(CompteRepository repository, CompteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteResponse> tousLesComptes() {
        return repository.findAll(Sort.by("id").ascending()).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompteResponse trouverCompte(Long id) {
        return mapper.toResponse(find(id));
    }

    @Override
    public CompteResponse creerCompte(CompteRequest request) {
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    public CompteResponse modifierCompte(Long id, CompteRequest request) {
        Compte compte = find(id);
        mapper.updateEntity(compte, request);
        return mapper.toResponse(repository.save(compte));
    }

    @Override
    public CompteResponse deposer(Long id, BigDecimal montant) {
        validateMontant(montant);
        Compte compte = find(id);
        compte.setSolde(compte.getSolde().add(montant));
        return mapper.toResponse(repository.save(compte));
    }

    @Override
    public CompteResponse retirer(Long id, BigDecimal montant) {
        validateMontant(montant);
        Compte compte = find(id);
        if (compte.getSolde().compareTo(montant) < 0) {
            throw new OperationBancaireException("Solde insuffisant pour le retrait");
        }
        compte.setSolde(compte.getSolde().subtract(montant));
        return mapper.toResponse(repository.save(compte));
    }

    @Override
    public void supprimerCompte(Long id) {
        Compte compte = find(id);
        repository.delete(compte);
    }

    private Compte find(Long id) {
        return repository.findById(id).orElseThrow(() -> new CompteNotFoundException(id));
    }

    private void validateMontant(BigDecimal montant) {
        if (montant == null || montant.signum() <= 0) {
            throw new OperationBancaireException("Le montant doit etre strictement positif");
        }
    }
}
