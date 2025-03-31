package es.ticketmaster.entrega1.service.exceptions;

public class ArtistNotFoundException extends RuntimeException {
    
    public ArtistNotFoundException(long id){
        super("It does not exist an artist with ID " + id);
    }

}
