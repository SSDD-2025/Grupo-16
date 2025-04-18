package es.ticketmaster.entrega1.service.exceptions;

public class TicketListEmptyException extends RuntimeException{
    public TicketListEmptyException(){
		super("This user does not have tickets (ticket's list is empty)");
	}
}
