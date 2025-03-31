package es.ticketmaster.entrega1.service.exceptions;

public class ArtistAlreadyExistsException extends RuntimeException {
    
    public ArtistAlreadyExistsException(String name){
        super("Artist with name " + name + " already exists.");
    }
}
