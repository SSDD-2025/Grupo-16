package es.ticketmaster.entrega1.service.exceptions;

public class NotAllowedException extends RuntimeException {
        
        public NotAllowedException(String message){
                super("You are not allowed to: " + message);
        }
}
