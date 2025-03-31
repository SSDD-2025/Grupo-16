package es.ticketmaster.entrega1.service.exceptions;

/**
 * This exception was created to provide a specific exception for cases where a user already exists in the database. 
 * Since it's not generic, it provides better information for error handling.
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}