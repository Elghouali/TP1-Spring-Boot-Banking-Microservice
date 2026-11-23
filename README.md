# TP1 — Implémentation d'un microservice bancaire avec Spring Boot

**Module :** Systèmes Distribués et DevOps 2026–2027 — II-BDCC3 / GLSID3 / SDIA2  
**Enseignant :** Mohamed YOUSSFI  
**Activité pratique n°1 — 11 étapes du sujet Google Classroom**

Cette application gère des **comptes bancaires** dans une base H2 avec **Java 21, Spring Boot 3.4.5, Spring Data JPA, Lombok, REST, Swagger/OpenAPI, Spring Data REST, projections, DTOs/mappers, couche service et GraphQL**. Il s'agit d'une reconstitution complète à partir des 11 consignes écrites ; les vidéos n'ont pas pu être consultées image par image.

## Prérequis

- **JDK 21** (ou JDK 17 si vous adaptez `java.version` du POM)
- **IntelliJ IDEA** avec Maven intégré, ou Maven 3.9+
- Postman (facultatif : on peut utiliser Swagger UI)

## Lancement dans IntelliJ (sans commandes shell)

1. `File → Open...` puis sélectionnez `pom.xml` du projet.
2. Dans IntelliJ, vérifiez **Project SDK = JDK 21** et rechargez Maven.
3. Activez **Annotation processing** si IntelliJ signale des getters/setters Lombok manquants.
4. Exécutez `CompteServiceApplication`.
5. Ouvrez :
   - **REST** : http://localhost:8081/api/comptes
   - **Swagger UI** : http://localhost:8081/swagger-ui.html
   - **Swagger JSON** : http://localhost:8081/v3/api-docs
   - **Spring Data REST** : http://localhost:8081/data-rest/comptes
   - **Projections** : http://localhost:8081/data-rest/comptes?projection=resume et `?projection=detail`
   - **GraphiQL** : http://localhost:8081/graphiql
   - **GraphQL endpoint** : POST http://localhost:8081/graphql
   - **H2 Console** : http://localhost:8081/h2-console (JDBC `jdbc:h2:mem:comptesdb`, username `sa`, blank password)
6. Pour des comptes de démonstration : exécuter avec le profil Spring **`demo`**. Sinon, la base démarre vide.

## Correspondance exacte avec les 11 consignes écrites

| # | Demande du professeur | Fichiers de preuve |
|---|---|---|
| 1 | Projet Spring Boot Web + JPA + H2 + Lombok | `pom.xml`, `application.yml` |
| 2 | Entité JPA `Compte` | `model/Compte.java`, `model/TypeCompte.java` |
| 3 | Interface `CompteRepository` | `repository/CompteRepository.java` |
| 4 | Tester la couche DAO | `CompteRepositoryTest.java` |
| 5 | API RESTful de gestion des comptes | `api/CompteRestController.java` |
| 6 | Tests avec Postman | `postman/TP1_Comptes.postman_collection.json` |
| 7 | Swagger/OpenAPI | `api/OpenApiConfig.java`, `/swagger-ui.html` |
| 8 | Spring Data REST et projections | `repository/CompteRepository.java`, `projection/*` |
| 9 | DTOs et Mappers | `dto/*`, `mapper/CompteMapper.java` |
| 10 | Couche Service métier | `service/CompteService.java`, `CompteServiceImpl.java` |
| 11 | GraphQL | `graphql/CompteGraphQL.java`, `resources/graphql/schema.graphqls`, `graphql/exemples.graphql` |

## Architecture du projet

```text
HTTP REST /api/comptes  ─┐
                          ├─> CompteService ─> CompteRepository ─> H2
GraphQL /graphql ────────┘          ^
                                   |
                              CompteMapper
Spring Data REST /data-rest/comptes ─> CompteRepository ─> H2
```

Note : **Spring Data REST expose directement le repository** et contourne donc la couche service : c'est volontaire pour montrer le mécanisme du TP. Les règles métier (`deposer`, `retirer`) passent exclusivement par les contrôleurs REST/GraphQL et le service. Une application bancaire de production exigerait sécurité, audit, restrictions d'écriture, transactions vérifiées et stockage persistant.

## Scénario de démonstration

Avec Postman (`postman/TP1_Comptes.postman_collection.json`), lancer dans l'ordre :

1. `POST /api/comptes` avec `{"solde":1000,"typeCompte":"COURANT","devise":"MAD"}` → 201, garder `id`.
2. `POST /api/comptes/{id}/depot` avec `{"montant":500}` → solde **1500**.
3. `POST /api/comptes/{id}/retrait` avec `{"montant":200}` → solde **1300**.
4. `POST /api/comptes/{id}/retrait` avec `{"montant":900000}` → **409** (solde insuffisant).
5. Tester `/swagger-ui.html`, `/data-rest/comptes?projection=resume`, `/graphiql`.

Tous les résultats ci-dessus sont **attendus**, pas encore confirmés par une exécution locale dans cet environnement.

## Requêtes GraphQL

```graphql
query { allComptes { id dateCreation solde typeCompte devise } }
```

```graphql
mutation {
  createCompte(input:{solde:1200.0,typeCompte:EPARGNE,devise:"MAD"}) {
    id solde typeCompte
  }
}
```

## Exécuter les tests

Dans IntelliJ : cliquer droit sur `src/test/java` puis **Run All Tests**.
Ou dans Maven : `mvn clean verify` (si Maven est installé).

Le script Python `scripts/smoke_test.py` teste REST, règles métier, Swagger, projections et GraphQL **après** démarrage de Spring Boot.

## Construction Docker facultative

`docker compose up --build`, puis ouvrez http://localhost:8081/swagger-ui.html.

## Livrables

- Projet Spring Boot complet (`src/main` + tests `src/test`)
- Collection Postman et exemples GraphQL
- `docs/RAPPORT_TP1.md` pour compléter les captures d'écran et résultats réels
- `docs/PLAN_VALIDATION.md` (liste des preuves nécessaires)

## Ressources indiquées par le professeur

- Vidéo 1 — microservice : https://www.youtube.com/watch?v=2-qloZcvhAw
- Vidéo 2 — GraphQL : https://www.youtube.com/watch?v=FsdRO9jlqaE
- Documentation Spring : https://docs.spring.io/spring-data/rest/reference/
- Documentation Spring GraphQL : https://docs.spring.io/spring-graphql/reference/
- Documentation springdoc : https://springdoc.org/v2/
