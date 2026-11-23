package ma.enset.tp1.graphql;

import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.dto.CompteResponse;
import ma.enset.tp1.model.TypeCompte;
import ma.enset.tp1.service.CompteService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class CompteGraphQL {
    private final CompteService service;
    public CompteGraphQL(CompteService service) { this.service = service; }

    public record CompteGql(String id, String dateCreation, double solde,
                            TypeCompte typeCompte, String devise) {}

    public record CompteInput(Double solde, TypeCompte typeCompte, String devise) {}

    private CompteGql toGql(CompteResponse c) {
        return new CompteGql(c.id().toString(), c.dateCreation().toString(), c.solde().doubleValue(),
            c.typeCompte(), c.devise());
    }

    private CompteRequest toRequest(CompteInput input) {
        // The schema requires the fields; this check produces a clear message if called indirectly.
        if (input == null || input.solde() == null || input.typeCompte() == null) {
            throw new IllegalArgumentException("solde et typeCompte sont obligatoires");
        }
        return new CompteRequest(BigDecimal.valueOf(input.solde()), input.typeCompte(), input.devise());
    }

    @QueryMapping
    public List<CompteGql> allComptes() {
        return service.tousLesComptes().stream().map(this::toGql).toList();
    }

    @QueryMapping
    public CompteGql compteById(@Argument Long id) {
        return toGql(service.trouverCompte(id));
    }

    @MutationMapping
    public CompteGql createCompte(@Argument("input") CompteInput input) {
        return toGql(service.creerCompte(toRequest(input)));
    }

    @MutationMapping
    public CompteGql updateCompte(@Argument Long id, @Argument("input") CompteInput input) {
        return toGql(service.modifierCompte(id, toRequest(input)));
    }

    @MutationMapping
    public CompteGql deposer(@Argument Long id, @Argument Double montant) {
        return toGql(service.deposer(id, BigDecimal.valueOf(montant)));
    }

    @MutationMapping
    public CompteGql retirer(@Argument Long id, @Argument Double montant) {
        return toGql(service.retirer(id, BigDecimal.valueOf(montant)));
    }

    @MutationMapping
    public boolean deleteCompte(@Argument Long id) {
        service.supprimerCompte(id);
        return true;
    }
}
