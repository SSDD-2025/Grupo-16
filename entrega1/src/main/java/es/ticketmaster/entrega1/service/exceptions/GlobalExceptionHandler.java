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
}
