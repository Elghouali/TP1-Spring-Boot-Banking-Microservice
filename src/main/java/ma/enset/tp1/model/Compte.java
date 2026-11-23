package ma.enset.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "comptes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDate dateCreation;

    @NotNull
    @DecimalMin(value = "0.0", message = "Le solde doit etre positif ou nul")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal solde;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private TypeCompte typeCompte;

    @NotNull
    @Column(nullable = false, length = 3)
    private String devise;

    @Version
    private Long version;
}
