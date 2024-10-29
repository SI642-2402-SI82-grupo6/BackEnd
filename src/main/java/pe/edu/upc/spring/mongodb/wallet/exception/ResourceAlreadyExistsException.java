// src/main/java/pe/edu/upc/spring/mongodb/wallet/exception/ResourceAlreadyExistsException.java
package pe.edu.upc.spring.mongodb.wallet.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}

