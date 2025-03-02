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
     * Returns a list of the 10 most famous artists
     * 
     * @return the list of the concerts of the top 10 most famous artist
     */
    public List<Artist> getArtistDisplayByPopularity() {

        return artistRepository.findTop10ByOrderByPopularityIndexDesc();
    }

    /**
     * Returns a list of the 10 newer artists added with a page to show
     *
     * @return the list of the concerts of the top 10 last created artist
     */
    public List<Artist> getArtistDisplayBySession() {
        return artistRepository.findTop10ByHasPageByOrderBySessionCreatedDesc(true);
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
     * @param mainPhoto MultipartFile photo to stablish the artist photo
     * @param bestPhoto (PROV) MultipartFile photo to stablish the artist best album photo
     * @param latestPhoto (PROV) MultipartFile photo to stablish the artist latest album photo
     * @throws IOException
     */
    public void registerNewArtist(Artist artist, MultipartFile mainPhoto, MultipartFile bestPhoto, MultipartFile latestPhoto) throws IOException {

        /*The photo is setted (if there is any error, it is set to null) */
        artist.setPhoto(imageService.getBlobOf(mainPhoto));
        artist.setBestAlbumPhoto(imageService.getBlobOf(bestPhoto)); /*PROVISIONAL - TO BE DELETED IN FUTURE HANDLES*/
        artist.setLatestAlbumPhoto(imageService.getBlobOf(latestPhoto)); /*PROVISIONAL - TO BE DELETED IN FUTURE HANDLES*/

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

    /**
     * Returns the artist (if found) with an specific name
     * @param name name of the artist to be found
     * @return said artist on a Optional object (None if its not found)
     */
    public Optional<Artist> getByName(String name){
        return artistRepository.findFirstByName(name);
    }

    /**
     * * Service method that modifies an existing artist with new attributes
     *
     * @param artist artist containing the new attributes that have been modified
     * @param id id of that artist
     * @param mainPhoto (optional) new photo for the artist
     * @param bestPhoto (PROV - optional) new photo for the best album
     * @param latestPhoto ( PROV - optional) new photo for the latest album
     * @return
     * @throws IOException
     */
    public boolean modifyArtistWithId(Artist artist, long id, MultipartFile mainPhoto, MultipartFile bestPhoto, MultipartFile latestPhoto) throws IOException {

        artist.setId(id);

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if(!oldArtist.isEmpty()){
            artist.setConcertList(oldArtist.get().getConcertList());
            artist.setHasPage(oldArtist.get().isHasPage());
            if(!mainPhoto.isEmpty()){ /*If a new photo has been uploaded*/
                artist.setPhoto(imageService.getBlobOf(mainPhoto));
            } else { /*If no new photo has been uploaded, it takes the older one*/
                artist.setPhoto(oldArtist.get().getPhoto());
            }
            /*TO BE REMOVED IN THE FUTURE - PROVISIONAL*/
            if(!bestPhoto.isEmpty()){ /*If a new photo has been uploaded*/
                artist.setBestAlbumPhoto(imageService.getBlobOf(bestPhoto));
            } else { /*If no new photo has been uploaded, it takes the older one*/
                artist.setBestAlbumPhoto(oldArtist.get().getBestAlbumPhoto());
            }
            /*TO BE REMOVED IN THE FUTURE - PROVISIONAL*/
            if(!latestPhoto.isEmpty()){ /*If a new photo has been uploaded*/
                artist.setLatestAlbumPhoto(imageService.getBlobOf(latestPhoto));
            } else { /*If no new photo has been uploaded, it takes the older one*/
                artist.setLatestAlbumPhoto(oldArtist.get().getLatestAlbumPhoto());
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

    /**
     * Method that checks if an specific artist name is already added to the database
     * so that unique names are mantained
     * @param name name to search
     * @return if the name is used or not
     */
    public boolean checkIfExistsByName(String name){

        return artistRepository.findFirstByName(name).isPresent();
    }

    /**
     * This method will create a new artist with only its name (it will not have an artist's page)
     * @param name the artist name
     * @return the new artist id
     */
    public long createNewArtist(String name){
        Artist newArtist = new Artist(name);
        artistRepository.save(newArtist);
        return newArtist.getId();
    }
}
