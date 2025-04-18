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
    
    /**
     * Handler of ArtistAlreadyExistsException, executed when, inside any of the controllers, an exception of
     * class ArtistAlreadyExistsExcpetion is thrown to show a JSON ResponseEntity with the error information.
     * @param ex catched exception
     * @param request the request that caused the exception
     * @return ResponseEntity to return in order to show the exception/error
     */
    @ExceptionHandler(ArtistAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerArtistAlreadyExists(ArtistAlreadyExistsException ex, WebRequest request){
        
        Map<String, Object> body = new HashMap<>();
        
        body.put("message", ex.getMessage());
        body.put("error", "ArtistAlreadyExists");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler of ArtistNotFoundException, executed when, inside any of the controllers, an exception of
     * class ArtistNotFoundExcpetion is thrown to show a JSON ResponseEntity with the error information.
     * @param ex catched exception
     * @param request the request that caused the exception
     * @return ResponseEntity to return in order to show the exception/error
     */
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerArtistNotFound(ArtistNotFoundException ex){
        
        Map<String, Object> body = new HashMap<>();
        
        body.put("message", ex.getMessage());
        body.put("error", "ArtistNotFound");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handler of ImageException, executed when, inside any of the controllers, an exception of
     * class ImageExistsExcpetion is thrown to show a JSON ResponseEntity with the error information.
     * @param ex catched exception
     * @param request the request that caused the exception
     * @return ResponseEntity to return in order to show the exception/error
     */
    @ExceptionHandler(ImageException.class)
    public ResponseEntity<Map<String, Object>> handlerImageException(ImageException ex, WebRequest request){
        
        Map<String, Object> body = new HashMap<>();
        
        body.put("message", ex.getMessage());
        body.put("error", "ImageException");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler of ImageException, executed when, inside any of the controllers, an exception of
     * class ImageExistsExcpetion is thrown to show a JSON ResponseEntity with the error information.
     * @param ex
     * @return
     */
    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<Map<String, Object>> handlerNotAllowed(NotAllowedException ex){
        
        Map<String, Object> body = new HashMap<>();
        
        body.put("message", ex.getMessage());
        body.put("error", "NotAllowed");

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
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

    /**
     * Handles TicketNotFoundException and returns a structured error response.
     * @param exception is the exception thrown when a ticket is not found.
     * @return a ResponseEntity containing an error message and a NOT_FOUND status.
     */
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerTicketNotFound(TicketNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", "TicketNotFound");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles TicketListEmptyException and returns a structured error response.
     * @param exception is the exception thrown when the ticketList is empty
     * @return a ResponseEntity containing an error message and a BAD_REQUEST status.
     */
    @ExceptionHandler(TicketListEmptyException.class)
    public ResponseEntity<Map<String, Object>> handlerTicketListEmpty(TicketListEmptyException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", "TicketListEmpty");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ConcertNotFoundException and returns a structured error response.
     * @param exception is the exception thrown when a ticket is not found.
     * @return a ResponseEntity containing an error message and a NOT_FOUND status.
     */
    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerConcertNotFound(ConcertNotFoundException exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        body.put("error", "ConcertNotFound");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}