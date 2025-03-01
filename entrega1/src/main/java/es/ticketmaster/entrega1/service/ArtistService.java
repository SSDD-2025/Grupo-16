package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.repository.ArtistRepository;

@Component
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ImageService imageService;

    /**
     * Searches for the artist that has an specific ID. In case the artist is
     * not present at the DDBB, null is returned
     *
     * @param id the artist id that is searched
     * @return the artist or null (if it is not found)
     */
    public Artist getArtist(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isEmpty()) {
            return null;
        } else {
            return artist.get();
        }
    }

    /**
     * Searches the concerts taken at a specific place
     *
     * @return the list of the concerts of the top 10 most famous artist
     */
    public List<Artist> getArtistDisplayByPopularity() {

        return artistRepository.findTop10ByOrderByPopularityIndexDesc();
    }

    /**
     * Searches the concerts taken at a specific place
     *
     * @return the list of the concerts of the top 10 last created artist
     */
    public List<Artist> getArtistDisplayBySession() {
        return artistRepository.findTop10ByOrderBySessionCreatedDesc();
    }

    /**
     * Makes the artist search by artist name making use of the repository
     *
     * @param search string to be searched
     * @return List of artists who match with search
     */
    public List<Artist> getSearchBy(String search) {

        return artistRepository.findByNameContainingIgnoreCase(search);
    }

    /**
     * Service method that builds an artist in order to be registered (saved in
     * the DDBB). As well, the register date is saved.
     *
     * @param artist artist to be saved
     * @param photo (optional) MultipartFile photo to stablish the artist
     * profile photo
     * @throws IOException
     */
    public void registerNewArtist(Artist artist, MultipartFile photo) throws IOException {

        /*The photo is setted (if there is any error, it is set to null) */
        artist.setPhoto(imageService.getBlobOf(photo));

        artist.setSessionCreated(LocalDateTime.now());
        artist.setHasPage(true);

        artistRepository.save(artist);

    }

    /**
     * Method that searches for each and every artist FUTURE IMPROVAL: Using
     * pages just in case there are lots of artists
     *
     * @return
     */
    public List<Artist> getEveryArtist() {

        return artistRepository.findAll();
    }

    public Optional<Artist> getByName(String name){
        return artistRepository.findFirstByName(name);
    }

    /**
     * Service method that modifies an existing artist with possible new photo
     *
     * @param artist artist containing the new attributes that have been
     * modified
     * @param id id of that artist
     * @param photo (optional) new photo for the artist
     * @throws IOException
     */
    public boolean modifyArtistWithId(Artist artist, long id, MultipartFile photo) throws IOException {

        artist.setId(id);

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if(!oldArtist.isEmpty()){
            artist.setConcertList(oldArtist.get().getConcertList());
            artist.setHasPage(oldArtist.get().isHasPage());
            if(!photo.isEmpty()){ /*If a new photo has been uploaded*/
                artist.setPhoto(imageService.getBlobOf(photo));
            } else { /*If no new photo has been uploaded, it takes the older one*/
                artist.setPhoto(oldArtist.get().getPhoto());
            }
            artistRepository.save(artist);
            return true;
        } else {
            return false; /*There was not an artist with such ID*/
        }
    }

    /**
     * Method that, provided with an ID, handles the deletion of an artist with
     * the specified id. For that, it is checked if the deletion has been
     * successful or not, searching if an artist with the given ID exists after
     * the deletion. Trying to delete a non-existant artist is also considered
     * an unsuccessful situation.
     *
     * @param id the Artist's ID
     * @return if the deletion has been completed successfully
     */
    public boolean deleteArtistWithId(long id) {

        if (!artistRepository.existsById(id)) { //If an artist with such ID does not exist
            return false;
        } else {
            artistRepository.deleteById(id); //We delete the artist with that ID
            return !artistRepository.existsById(id); //We return true if the artist has been correctly deleted
        }
    }

    public long createNewArtist(String name){
        Artist newArtist = new Artist(name);
        artistRepository.save(newArtist);
        return newArtist.getId();
    }
}
