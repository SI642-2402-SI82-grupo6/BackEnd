package pe.edu.upc.spring.mongodb.wallet.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import pe.edu.upc.spring.mongodb.security.payload.response.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<MessageResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MessageResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getMostSpecificCause();
        String errorMessage;
        if (cause instanceof InvalidFormatException ife) {
            String targetType = ife.getTargetType().getSimpleName();
            String value = ife.getValue().toString();
            errorMessage = String.format("Invalid value '%s' for type '%s'", value, targetType);
        } else if (cause instanceof JsonParseException jpe) {
            errorMessage = "Invalid input: " + jpe.getOriginalMessage();
        } else {
            errorMessage = "Invalid input: " + ex.getMostSpecificCause().getMessage();
        }
        return new ResponseEntity<>(new MessageResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }
}