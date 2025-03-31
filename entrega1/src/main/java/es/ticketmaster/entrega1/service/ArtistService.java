package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.dto.artist.ArtistMapper;
import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.repository.ArtistRepository;
import es.ticketmaster.entrega1.service.exceptions.ArtistAlreadyExistsException;
import es.ticketmaster.entrega1.service.exceptions.ArtistNotFoundException;

@Component
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArtistMapper artistMapper;

    /*METHODS DEALING WITH ARTIST SEARCHING AND GETTING*/

    /**
     * Searches for the artist that has an specific ID and returns its DTO. In case the artist is
     * not present at the DDBB, the pertinent exception is trown.
     *
     * @param id the artist id that is searched
     * @return The Artist's or Exception DTO if there is not any artist with that id.
     */
    public ArtistDTO getArtist(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){

            return artistMapper.toDTO(artist.get());

        } else {

            throw new ArtistNotFoundException(id);

        }

    }

    /**
     * Searches for every artist in the DDBB, returning a Collection with their DTO.
     * FUTURE IMPROVAL: PAGINATION
     *
     * @return
     */
    public Collection<ArtistDTO> getEveryArtist() {
        return artistMapper.toDTOs(artistRepository.findAll());
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
     * Method that checks if an specific artist name is already added to the
     * database so that unique names are mantained
     *
     * @param name name to search
     * @return if the name is used or not
     */
    public boolean checkIfExistsByName(String name) {

        return artistRepository.findFirstByName(name).isPresent();
    }

    /**
     * Method that checks if there is an artist on the database with a name
     * (ignoring cases)
     *
     * @param name the name to search
     * @return wheter it exists or not
     */
    public boolean artistExists(String name) {
        return artistRepository.findFirstByNameIgnoreCase(name).isPresent();
    }

    /**
     * Returns the artist (if found) with an specific name
     *
     * @param name name of the artist to be found
     * @return said artist on a Optional object (None if its not found)
     */
    public Optional<Artist> getByName(String name) {
        return artistRepository.findFirstByName(name);
    }

    /**
     * Returns the artist (if found) with an specific name
     *
     * @param name name of the artist to be found
     * @return said artist on a Optional object (None if its not found)
     */
    public Optional<Artist> getByNameIgnoreCase(String name) {
        return artistRepository.findFirstByNameIgnoreCase(name); 
    }

    /**
     * Method that returns the ArtistEntity (java class object) by its id.
     * 
     * @param id The ID of the Artist.
     * @return Artist object representing the desired Artist in the DDBB.
     */
    public Artist getArtistEntity(long id){

        Optional<Artist> artist = artistRepository.findById(id);
        
        if(artist.isEmpty()){
            return null;
        } else {
            return artist.get();
        }
    }

    /*METHODS DEALING WITH ARTIST CREATION AND MODIFICATION*/

    /**
     * Service method that builds an artist in order for it to be registered (saved in
     * the DDBB). As well, the register date is saved. As a confirmation, the Artist's
     * DTO is returned.
     *
     * @param artistDTO artist's input DTO
     * @param mainPhoto MultipartFile photo to stablish the artist photo
     * @param bestPhoto (PROV) MultipartFile photo to stablish the artist best
     * album photo
     * @param latestPhoto (PROV) MultipartFile photo to stablish the artist
     * latest album photo
     * @return Artist's DTO representing the registered artist that is saved in the DDBB.
     * @throws Exception 
     */
    public ArtistDTO registerNewArtist(ArtistDTO artistDTO, MultipartFile mainPhoto, MultipartFile bestPhoto, MultipartFile latestPhoto) throws Exception {

        /*We get the artist as an Entity object*/
        Artist artist = artistMapper.toDomain(artistDTO);

        if(artistExists(artist.getName())){
            throw new ArtistAlreadyExistsException(artist.getName());
        }

        /*The photo is setted (if there is any error, it is set to null) */
        artist.setPhoto(imageService.getBlobOf(mainPhoto));
        artist.setBestAlbumPhoto(imageService.getBlobOf(bestPhoto));
        /*PROVISIONAL - TO BE DELETED IN FUTURE HANDLES*/
        artist.setLatestAlbumPhoto(imageService.getBlobOf(latestPhoto));
        /*PROVISIONAL - TO BE DELETED IN FUTURE HANDLES*/

        artist.setSessionCreated(LocalDateTime.now());
        artist.setHasPage(true);

        artistRepository.save(artist);

        return artistMapper.toDTO(artist);

    }

    /**
     * Service method that modifies an existing artist with new attributes. Every attribute is modificable
     * excepto for artistName
     *
     * @param artistDTO artist's input DTO with the modified attributes
     * @param id id of that artist
     * @param mainPhoto (optional) new photo for the artist
     * @param bestPhoto (PROV - optional) new photo for the best album
     * @param latestPhoto ( PROV - optional) new photo for the latest album
     * @return Artist's DTO representing the modified artist that is saved in the DDBB.
     * @throws IOException
     */
    public ArtistDTO modifyArtistWithId(ArtistDTO artistDTO, long id, MultipartFile mainPhoto, MultipartFile bestPhoto, MultipartFile latestPhoto) throws IOException {

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if (oldArtist.isPresent()) {
            Artist artist = artistMapper.toDomain(artistDTO);
            artist.setId(id);
            artist.setName(oldArtist.get().getName());
            artist.setConcertList(oldArtist.get().getConcertList());
            artist.setHasPage(oldArtist.get().isHasPage());

            if (!mainPhoto.isEmpty()) {
                /*If a new photo has been uploaded*/
                artist.setPhoto(imageService.getBlobOf(mainPhoto));
            } else {
                /*If no new photo has been uploaded, it takes the older one*/
                artist.setPhoto(oldArtist.get().getPhoto());
            }

            /*TO BE REMOVED IN THE FUTURE - PROVISIONAL*/
            if (!bestPhoto.isEmpty()) {
                /*If a new photo has been uploaded*/
                artist.setBestAlbumPhoto(imageService.getBlobOf(bestPhoto));
            } else {
                /*If no new photo has been uploaded, it takes the older one*/
                artist.setBestAlbumPhoto(oldArtist.get().getBestAlbumPhoto());
            }

            /*TO BE REMOVED IN THE FUTURE - PROVISIONAL*/
            if (!latestPhoto.isEmpty()) {
                /*If a new photo has been uploaded*/
                artist.setLatestAlbumPhoto(imageService.getBlobOf(latestPhoto));
            } else {
                /*If no new photo has been uploaded, it takes the older one*/
                artist.setLatestAlbumPhoto(oldArtist.get().getLatestAlbumPhoto());
            }

            artist.setHasPage(true);
            artistRepository.save(artist);
            return artistMapper.toDTO(artist);
        } else {
            throw new ArtistNotFoundException(id);
            /*There was not an artist with such ID, it is thrown*/
        }
    }

    /*METHODS DEALING WITH ARTIST DELETION*/

    /**
     * Method that, provided with an ID, handles the deletion of an artist with
     * the specified id. For that, it is checked if the deletion has been
     * successful or not, searching if an artist with the given ID exists after
     * the deletion. Trying to delete a non-existant artist is also considered
     * an unsuccessful situation.
     * 
     * IMPORTANT NOTE: Since @Generation.type = AUTO, there are no IDs recycled, so that
     * is safe to check for a deleted id to find if it still exists, since it will not be
     * assigned to another artist.
     *
     * @param id the Artist's ID
     * @return the deleted Artist's DTO
     */
    public ArtistDTO deleteArtistWithId(long id) {

        Artist artist = artistRepository.findById(id).orElseThrow();
        artistRepository.deleteById(id); //We delete the artist with that ID
        return artistMapper.toDTO(artist);

    }

    /**
     * This method will create a new artist with only its name (it will not have
     * an artist's page)
     *
     * @param name the artist name
     * @return the new artist id
     */
    public long createNewArtist(String name) {
        Artist newArtist = new Artist(name);
        artistRepository.save(newArtist);
        return newArtist.getId();
    }
}