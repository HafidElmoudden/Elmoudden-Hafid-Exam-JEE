package ma.hafidelmoudden.backend.exceptions;

public class CreditNotFoundException extends RuntimeException {
    public CreditNotFoundException(String message) {
        super(message);
    }
} 