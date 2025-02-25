package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.repository.ArtistRepository;

@Component
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;

    /**
     * 
     * @param id the artist id that is searched
     * @return the artist or null (if its not found)
     */
    public Optional<Artist> getArtist(long id){
        return artistRepository.findById(id);
    }

    /** Searches the concerts taken at a specific place
     * @return the list of the concerts of the top 10 most famous artist
     */
    public List<Artist> getArtistDisplayByPopularity(){
        
        return artistRepository.findTop10ByOrderByPopularityIndexDesc();
    }

    /** Searches the concerts taken at a specific place
     * @return the list of the concerts of the top 10 last created artist
     */
    public List<Artist> getArtistDisplayBySession(){
        return artistRepository.findTop10ByOrderBySessionCreatedDesc();
    }

    /**
     * Makes the artist search by artist name making use of the repository
     * @param search string to be searched
     * @return List of artists who match with search
     */
    public List<Artist> getSearchBy(String search){
        
        return artistRepository.findByNameContainingIgnoreCase(search);
    }


    public void registerNewArtist(Artist artist, MultipartFile photo) throws IOException{
        
        if(!photo.isEmpty()){
            artist.setPhoto(BlobProxy.generateProxy(photo.getInputStream(), photo.getSize()));
        }

        artistRepository.save(artist);

    }
}
