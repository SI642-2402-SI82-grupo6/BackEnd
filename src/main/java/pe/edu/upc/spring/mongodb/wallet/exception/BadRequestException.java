package pe.edu.upc.spring.mongodb.wallet.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
