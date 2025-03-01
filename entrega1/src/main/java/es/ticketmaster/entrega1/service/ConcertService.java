package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.ActiveUser;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.repository.ConcertRepository;
import es.ticketmaster.entrega1.repository.UserRepository;

/**
 * Concert service that gets access to the ConcertRepository to make specific and simple querys.
 * It is thought to be used by controllers to modularizate and simplify the logic inside them.
 */
@Component
public class ConcertService {
    
    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ActiveUser activeUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArtistService artistService;
    
    /**
     * Method that searches for concerts which Concert.name or Concert.artist.name matches with
     * a String and returns a list of concerts with this match.
     * 
     * @param search String to search by
     * @return List of Concert that could be empty when no concerts are found
     * @see Concert
     */
    public List<Concert> getSearchBy(String search){
        
        List<Concert> searchedConcerts = concertRepository.findByNameContainingIgnoreCase(search);

        if(search.length() > 2){ //To avoid repeated simple input answers
            searchedConcerts.addAll(concertRepository.findByArtistNameContainingIgnoreCase(search));
        }
        
        return searchedConcerts;
    }
    
    /** Searches the concerts taken at a specific place
         * @param place is the country/ city which the search is based
         * @return the list of the concerts taking place at the country/ city specified
         */
    public List<Concert> getConcertDisplay(boolean userLogged){
        if (userLogged){ //is logged, the display will be of concerts at the same country as the user
            List<Concert> concertList = concertRepository.getConcertByPlace(userRepository.findById(activeUser.getId()).getCountry());
            if (!(concertList.isEmpty())){
                return concertList;
            }
        }
        //whether the user is not logged or the concertListByCountry is empty, it will return a list of concerts order by artist's fame
        return concertRepository.findAll(); //provisional until the query is fixed
    }

    /**
     * Gets the all the concerts existing in the DDBB
     * @return List with all the concerts registered
     */
    public List<Concert> getAllConcerts(){
        return concertRepository.findAll();
    }

    /**
     * Finds an specific concert by its ID, searching it in the DDBB
     * @param id id of the concert to be searched
     * @return The concert (if it exists), null if there is no match
     */
    public Concert findConcertById(long id){
        
        Optional<Concert> concert = concertRepository.findById(id);
        
        if(!concert.isEmpty()){ /*If a concert has been found*/
            return concert.get();
        } else { /*If there is no concert match*/
            return null;
        }
    }

    /**
     * @param artistName the artist name whose concert list is being returned
     * @return said list
     */
    public List<Concert> getArtistConcerts(String artistName){
        return concertRepository.findByArtistNameIgnoreCase(artistName);
    }

    /**
     * This method will verify is there are available tickets for a specific type (section).
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets the user has purchased.
     * @param type is the type (section) of the ticket.
     * @return if the rows has been updated (1) true, otherwise, false.
     */
    public boolean verifyAvailability(long id, int number, String type) {
        int result = 0;
        if (type.equalsIgnoreCase("West Side")) {
            result = this.concertRepository.availableWestStandsTickets(id, number);
        }
        else if (type.equalsIgnoreCase("East Side")) {
            result = this.concertRepository.availableEastStandsTickets(id, number);
        }
        else if (type.equalsIgnoreCase("North Side")) {
            result = this.concertRepository.availableNorthStandsTickets(id, number);
        }
        else if (type.equalsIgnoreCase("General Admission")) {
            result = this.concertRepository.availableGeneralAdmissionTickets(id, number);
        }
        return result == 1;
    }

    public void saveConcert(Concert concert, MultipartFile poster, long artistId) throws IOException{
        if (poster != null){
            concert.setImage(imageService.getBlobOf(poster));
        }
        concertRepository.save(concert);

        //set the artist
        concert.setArtist(artistService.getArtist(artistId));
        concertRepository.save(concert);
    }

    public void modifyConcert(Concert concert, long id, MultipartFile poster, long artistId) throws IOException{
        // concert has the number of added tickets
        // so then modified concert is concert with the old concert available tickets added
        Concert oldConcert = concertRepository.findConcertById(id);
        concert.addTickets(oldConcert);
        if (poster != null){
            concert.setImage(imageService.getBlobOf(poster));
        }

        // set the old id to the modified concert
        concert.setId(id);

        //save the modified concert
        concertRepository.save(concert);

        //set the artist
        concert.setArtist(artistService.getArtist(artistId));
        concertRepository.save(concert);
    }
}