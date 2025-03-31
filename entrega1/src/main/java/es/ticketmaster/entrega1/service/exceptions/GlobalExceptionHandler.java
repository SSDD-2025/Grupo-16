package es.ticketmaster.entrega1.service.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ArtistAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerArtistAlreadyExists(ArtistAlreadyExistsException ex, WebRequest request){
        
        Map<String, Object> body = new HashMap();
        
        body.put("message", ex.getMessage());
        body.put("error", "ArtistAlreadyExists");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerArtistNotFound(ArtistNotFoundException ex){
        
        Map<String, Object> body = new HashMap();
        
        body.put("message", ex.getMessage());
        body.put("error", "ArtistNotFound");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserNotFoundException and returns a structured error response.
     * @param exception is the exception thrown when a user is not found.
     * @return a ResponseEntity containing an error message and a NOT_FOUND status.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerUserNotFound(UserNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", "UserNotFound");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the exception thrown when a user already exists in the system.
     * 
     * @param exception the exception thrown when a user with the same username is already present.
     * @return a ResponseEntity containing:
     *         - 400 Bad Request status and a structured error message if the user already exists.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerUserAlreadyExists(UserAlreadyExistsException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", "UserAlreadyExists");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}