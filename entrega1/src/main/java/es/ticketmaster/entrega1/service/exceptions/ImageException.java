package es.ticketmaster.entrega1.service.exceptions;

public class ImageException extends RuntimeException {
    
    public ImageException(String motive){
        super("There was an error with images: " + motive);
    }
    
}
