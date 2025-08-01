package vti.dtn.auth_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public ValidationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
