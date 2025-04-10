package es.ticketmaster.entrega1.service.exceptions;

/**
 * Exception thrown when a user with the given ID is not found.
 */
public class UserNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new UserNotFoundException with a detailed message.
     * @param id the ID of the not found user.
     */
    public UserNotFoundException() {
        super("User not found");
    }
}