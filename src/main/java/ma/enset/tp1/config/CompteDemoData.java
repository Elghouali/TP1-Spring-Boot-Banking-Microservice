package ma.enset.tp1.config;

import ma.enset.tp1.model.Compte;
import ma.enset.tp1.model.TypeCompte;
import ma.enset.tp1.repository.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class CompteDemoData {
    @Bean
    @Profile("demo")
    CommandLineRunner seed(CompteRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.save(Compte.builder().dateCreation(LocalDate.now())
                .solde(new BigDecimal("15000.00")).typeCompte(TypeCompte.COURANT).devise("MAD").build());
            repository.save(Compte.builder().dateCreation(LocalDate.now())
                .solde(new BigDecimal("7800.00")).typeCompte(TypeCompte.EPARGNE).devise("MAD").build());
            repository.save(Compte.builder().dateCreation(LocalDate.now())
                .solde(new BigDecimal("3400.00")).typeCompte(TypeCompte.COURANT).devise("MAD").build());
        };
    }
}
