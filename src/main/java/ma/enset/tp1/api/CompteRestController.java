package ma.enset.tp1.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.dto.CompteResponse;
import ma.enset.tp1.dto.MontantRequest;
import ma.enset.tp1.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comptes")
@Tag(name = "Comptes bancaires", description = "API REST de gestion des comptes")
public class CompteRestController {
    private final CompteService service;
    public CompteRestController(CompteService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Lister les comptes")
    public List<CompteResponse> all() { return service.tousLesComptes(); }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un compte")
    public CompteResponse one(@PathVariable Long id) { return service.trouverCompte(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creer un compte")
    public CompteResponse create(@Valid @RequestBody CompteRequest request) {
        return service.creerCompte(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un compte")
    public CompteResponse update(@PathVariable Long id, @Valid @RequestBody CompteRequest request) {
        return service.modifierCompte(id, request);
    }

    @PostMapping("/{id}/depot")
    @Operation(summary = "Deposer de l'argent")
    public CompteResponse deposit(@PathVariable Long id, @Valid @RequestBody MontantRequest request) {
        return service.deposer(id, request.montant());
    }

    @PostMapping("/{id}/retrait")
    @Operation(summary = "Retirer de l'argent")
    public CompteResponse withdraw(@PathVariable Long id, @Valid @RequestBody MontantRequest request) {
        return service.retirer(id, request.montant());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un compte")
    public void delete(@PathVariable Long id) { service.supprimerCompte(id); }
}
