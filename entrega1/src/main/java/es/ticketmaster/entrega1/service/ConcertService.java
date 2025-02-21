package es.ticketmaster.entrega1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.ConcertRepository;

/**
 * Concert service that gets access to the ConcertRepository to make specific and simple querys.
 * It is thought to be used by controllers to modularizate and simplify the logic inside them.
 */
@Component
public class ConcertService {
    
    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    private UserEntity activeUser;
    
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

        searchedConcerts.addAll(concertRepository.findByArtistNameContainingIgnoreCase(search));
        
        return searchedConcerts;
    }
    
    /** Searches the concerts taken at a specific place
         * @param place is the country/ city which the search is based
         * @return the list of the concerts taking place at the country/ city specified
         */
    public List<Concert> getConcertDisplay(boolean userLogged){
        if (userLogged){ //is logged, the display will be of concerts at the same country as the user
            List<Concert> concertList = concertRepository.getConcertByPlace(activeUser.getCountry());
            if (!(concertList.isEmpty())){
                return concertList;
            }
        }
        //whether the user is not logged or the concertListByCountry is empty, it will return a list of concerts order by artist's fame
        return concertRepository.findAll(); //provisional until the query is fixed
    }
}
