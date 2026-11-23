package ma.enset.tp1.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "TP1 — API de gestion des comptes bancaires",
    version = "1.0.0", description = "API REST Spring Boot pour l'activite pratique N1 de Mohamed Youssfi"))
public class OpenApiConfig { }
