package es.ticketmaster.entrega1.service.exceptions;

public class ConcertNotFoundException extends RuntimeException{
	public ConcertNotFoundException(String name, String place){
		super("It does not exists a concert named " + name + " taking place in " + place);
	}

	public ConcertNotFoundException(long id){
		super("It does not exists a concert with the id " + id);
	}
}