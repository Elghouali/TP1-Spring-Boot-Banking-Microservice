package ma.enset.tp1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(CompteNotFoundException.class)
    ResponseEntity<ProblemDetail> notFound(CompteNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OperationBancaireException.class)
    ResponseEntity<ProblemDetail> businessError(OperationBancaireException ex) {
        return error(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream().findFirst()
            .map(e -> e.getField() + " : " + e.getDefaultMessage()).orElse("Requete invalide");
        return error(HttpStatus.BAD_REQUEST, msg);
    }

    private ResponseEntity<ProblemDetail> error(HttpStatus status, String msg) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, msg);
        pd.setType(URI.create("about:blank"));
        pd.setTitle(status.getReasonPhrase());
        return ResponseEntity.status(status).body(pd);
    }
}
