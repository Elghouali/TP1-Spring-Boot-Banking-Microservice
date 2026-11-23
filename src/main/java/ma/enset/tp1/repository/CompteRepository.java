package ma.enset.tp1.repository;

import ma.enset.tp1.model.Compte;
import ma.enset.tp1.model.TypeCompte;
import ma.enset.tp1.projection.CompteProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import java.util.List;

@RepositoryRestResource(path = "comptes", collectionResourceRel = "comptes",
    excerptProjection = CompteProjection.class)
public interface CompteRepository extends JpaRepository<Compte, Long> {
    @RestResource(path = "byType", rel = "byType")
    List<Compte> findByTypeCompte(@Param("type") TypeCompte typeCompte);
}
