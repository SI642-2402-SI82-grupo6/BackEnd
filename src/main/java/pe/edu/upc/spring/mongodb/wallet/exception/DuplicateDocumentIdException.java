package pe.edu.upc.spring.mongodb.wallet.exception;

public class DuplicateDocumentIdException extends RuntimeException {
    public DuplicateDocumentIdException(String message) {
        super(message);
    }
}
