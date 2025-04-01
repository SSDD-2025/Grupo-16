package es.ticketmaster.entrega1.service.exceptions;

/**
 * Exception thrown when a user with the given ID is not found.
 */
public class TicketNotFoundException extends RuntimeException {
    
    /**
     * Constructs a new TicketNotFoundException with a detailed message.
     * @param id the ID of the not found ticket.
     */
    public TicketNotFoundException(Long id) {
        super("Ticket with ID " + id + " not found");
    }
}