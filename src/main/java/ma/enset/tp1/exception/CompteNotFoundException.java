package ma.enset.tp1.exception;

public class CompteNotFoundException extends RuntimeException {
    public CompteNotFoundException(Long id) {
        super("Compte introuvable avec identifiant : " + id);
    }
}
