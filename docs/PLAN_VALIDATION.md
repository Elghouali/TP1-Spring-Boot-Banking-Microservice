# Checklist de validation avant remise

- [ ] Dans IntelliJ, le rechargement Maven réussit (`pom.xml`).
- [ ] `CompteServiceApplication` démarre sans erreur, port 8081.
- [ ] H2 Console affiche la table `COMPTES`.
- [ ] `CompteRepositoryTest` réussit (couche DAO).
- [ ] `CompteServiceTest` réussit (dépôt/retrait et insuffisance de solde).
- [ ] `CompteRestControllerTest` réussit (CRUD et validation).
- [ ] `CompteGraphQLTest` réussit (query/mutation).
- [ ] Postman collection : création puis dépôt 500/retrait 200 -> solde 1300.
- [ ] Swagger UI s'ouvre sur http://localhost:8081/swagger-ui.html.
- [ ] Data REST `?projection=resume` et `?projection=detail` renvoient les champs attendus.
- [ ] GraphiQL permet une query et mutation.
- [ ] Captures d'écran insérées dans `docs/RAPPORT_TP1.md`.
- [ ] Le GitHub contient le code complet et le rapport.
- [ ] L'URL du dépôt a été remise dans Classroom.

**Ne pas confondre** présence du code, couverture fonctionnelle théorique et vérification d'exécution réelle.
