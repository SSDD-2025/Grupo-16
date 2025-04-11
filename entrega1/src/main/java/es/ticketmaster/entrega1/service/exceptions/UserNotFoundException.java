package es.ticketmaster.entrega1.service.exceptions;

/**
 * Exception thrown when a user with the given ID is not found.
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException() {
        super("User not found");
    }
}