package ma.enset.tp1.service;

import ma.enset.tp1.dto.CompteRequest;
import ma.enset.tp1.exception.OperationBancaireException;
import ma.enset.tp1.mapper.CompteMapper;
import ma.enset.tp1.model.TypeCompte;
import ma.enset.tp1.repository.CompteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CompteServiceTest {
    @Autowired CompteService service;

    @Test
    void creationDepotRetraitEtRefusDecouvert() {
        var compte = service.creerCompte(new CompteRequest(
            new BigDecimal("1000.00"), TypeCompte.COURANT, "MAD"));
        assertThat(compte.id()).isNotNull();
        var credited = service.deposer(compte.id(), new BigDecimal("500.00"));
        assertThat(credited.solde()).isEqualByComparingTo("1500.00");
        var debited = service.retirer(compte.id(), new BigDecimal("200.00"));
        assertThat(debited.solde()).isEqualByComparingTo("1300.00");
        assertThatThrownBy(() -> service.retirer(compte.id(), new BigDecimal("5000.00")))
            .isInstanceOf(OperationBancaireException.class);
    }
}
