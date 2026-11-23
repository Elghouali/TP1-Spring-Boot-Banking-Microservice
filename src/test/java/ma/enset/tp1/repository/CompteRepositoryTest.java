package ma.enset.tp1.repository;

import ma.enset.tp1.model.Compte;
import ma.enset.tp1.model.TypeCompte;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CompteRepositoryTest {
    @Autowired CompteRepository repository;

    @Test
    void sauvegarderEtChercherParType() {
        Compte saved = repository.saveAndFlush(Compte.builder()
            .dateCreation(LocalDate.of(2026, 11, 1))
            .solde(new BigDecimal("1200.00"))
            .typeCompte(TypeCompte.EPARGNE).devise("MAD").build());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
        assertThat(repository.findByTypeCompte(TypeCompte.EPARGNE))
            .extracting(Compte::getId).contains(saved.getId());
    }
}
