package es.ticketmaster.entrega1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.repository.ArtistRepository;

@Component
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getArtistDisplay(){
        /** Searches the concerts taken at a specific place
         * @param place is the country/ city which the search is based
         * @return the list of the concerts taking place at the country/ city specified
         */
        return artistRepository.findTop10ByOrderByPopularityIndexDesc();
    }

    /**
     * Makes the artist search by artist name making use of the repository
     * @param search string to be searched
     * @return List of artists who match with search
     */
    public List<Artist> getSearchBy(String search){
        
        return artistRepository.findByNameContainingIgnoreCase(search);
    }
}
