package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.dto.artist.ArtistMapper;
import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.repository.ArtistRepository;
import es.ticketmaster.entrega1.service.exceptions.ArtistAlreadyExistsException;
import es.ticketmaster.entrega1.service.exceptions.ArtistNotFoundException;
import es.ticketmaster.entrega1.service.exceptions.ImageException;

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
    public Page<ArtistDTO> getEveryArtist(Pageable pageable) {
        return artistRepository.findAll(pageable).map(artist -> artistMapper.toDTO(artist));
    }

    /**
     * Searches for every artist in the DDBB, returning a Collection with their DTO.
     * @return list of every artist in the DDBB
     */
    public List<ArtistDTO> getEveryArtist() {
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
     * Makes the artist search by artist name making use of the repository. A Page of artist is returned,
     * meaning that this method makes use of pagination.
     *
     * @param search string to be searched
     * @param pageable the desired pagination configuration
     * @return List of artists who match with search
     */
    public Page<Artist> getSearchBy(String search, Pageable pageable) {

        return artistRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    /**
     * Makes the artist search by artist name making use of the repository and returning a Page of
     * ArtistDTO. As the returning type is a Page, this method makes use of pagination.
     *
     * @param search string to be searched
     * @param pageable the desired pagination configuration
     * @return List of artists who match with search
     */
    public Page<ArtistDTO> getSearchDTOBy(String search, Pageable pageable) {

        return artistRepository.findByNameContainingIgnoreCase(search, pageable).map(element -> artistMapper.toDTO(element));
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
     * @return whether it exists or not
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

    /**
     * Service method that returns the main photo of an specified-by-id artist. In case the
     * artist does not exist, an ArtistNotFoundException is thrown. In case the artist does
     * not have main photo, an ImageException with a notification is thrown.
     * 
     * @param id identifier of the artist
     * @return The main photo of the artist
     * @throws SQLException
     */
    public Resource getPhoto(long id) throws SQLException{
        
        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){
            if(artist.get().getPhotoLink() != null){
                return new InputStreamResource(artist.get().getPhoto().getBinaryStream());
            } else {
                throw new ImageException("That image does not exist");
            }
        } else {
            throw new ArtistNotFoundException(id);
        }
    }

    /*METHODS DEALING WITH ARTIST CREATION AND MODIFICATION*/

    /**
     * Service method that builds an artist in order to be registered (saved in
     * the DDBB). As well, the register date is saved.
     *
     * @param artist artist to be saved
     * @param photo MultipartFile photo to stablish the artist photo
     * @throws IOException
     */
    public boolean registerNewArtist(Artist artist, MultipartFile photo) throws IOException {

        /*The photo is set (if there is any error, it is set to null) */
        Blob photoblob = imageService.getBlobOf(photo);
        if (!(photoblob == null)){
            artist.setPhoto(imageService.getBlobOf(photo));
            artist.setPhotoLink("/api/artists/" + artist.getId() + "/photo");
        }

        /*Checks compulsory attributes in order to create a new artist*/
        if(artist.getName() == null){
            return false;
        }
        if(artistExists(artist.getName())){ /*Verifies the Artist name is not repeated*/
            return false;
        }

        /*Attributes not needed to be registered, but required in order to have a page*/

        artist.setSessionCreated(LocalDateTime.now());
        artist.setHasPage(true);

        artistRepository.save(artist);
        return true;
    }

    /**
     * Service method that builds an artist in order for it to be registered (saved in
     * the DDBB). As well, the register date is saved. As a confirmation, the Artist's
     * DTO is returned.
     *
     * @param artistDTO artist's input DTO
     * @return Artist's DTO representing the registered artist that is saved in the DDBB.
     * @throws Exception 
     */
    public ArtistDTO registerNewArtist(ArtistDTO artistDTO) throws Exception {

        /*We get the artist as an Entity object*/
        Artist artist = artistMapper.toDomain(artistDTO);

        /*Checks compulsory attributes in order to create a new artist*/
        if(artist.getName() == null){
            /*return false;*/
        }
        if(artistExists(artist.getName())){ /*Verifies the Artist name is not repeated*/
            throw new ArtistAlreadyExistsException(artist.getName());
        }

        /*Attributes not needed to be registered, but required in order to have a page*/

        artist.setPhotoLink(null); //There is no posibility any photo is provided
        artist.setSessionCreated(LocalDateTime.now());
        artist.setHasPage(true);

        artistRepository.save(artist);

        return artistMapper.toDTO(artist);

    }

    /**
     * Service method that creates the main-photo of an artist, specified by id. In case no artist
     * with such id is found, it is returned an ArtistNotFoundException.
     * @param id artist identifier
     * @param image image to stablish as main photo
     */
    public void createPhotoImage(long id, MultipartFile image){
        
        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){
            if(artist.get().getPhotoLink() != null){
                throw new ImageException("Artist already has a main photo");
            }
            try {
                artist.get().setPhoto(imageService.getBlobOf(image));
                artistRepository.save(artist.get());
            } catch (IOException e) {
                throw new ImageException(e.toString());
            }
        } else {
            throw new ArtistNotFoundException(id);
        }
    }

    /**
     * * Service method that modifies an existing artist with new attributes
     *
     * @param artist artist containing the new attributes that have been
     * modified
     * @param id id of that artist
     * @param photo (optional) new photo for the artist
     * @return
     * @throws IOException
     */
    public boolean modifyArtistWithId(Artist artist, long id, MultipartFile photo) throws IOException {

        artist.setId(id);

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if (!oldArtist.isEmpty()) {
            artist.setConcertList(oldArtist.get().getConcertList());
            artist.setHasPage(oldArtist.get().isHasPage());
            if (!photo.isEmpty()) {
                /*If a new photo has been uploaded*/
                artist.setPhoto(imageService.getBlobOf(photo));
            } else {
                /*If no new photo has been uploaded, it takes the older one*/
                artist.setPhoto(oldArtist.get().getPhoto());
            }
            artist.setHasPage(true);
            artistRepository.save(artist);
            return true;
        } else {
            return false;
            /*There was not an artist with such ID*/
        }
    }

    /**
     * Service method that modifies an existing artist with new attributes. Every attribute is modificable
     * except for artistName
     *
     * @param artistDTO artist's input DTO with the modified attributes
     * @param id id of that artist
     * @param photo (optional) new photo for the artist
     * @return Artist's DTO representing the modified artist that is saved in the DDBB.
     * @throws IOException
     */
    public ArtistDTO modifyArtistWithId(ArtistDTO artistDTO, long id) throws IOException {

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if (oldArtist.isPresent()) {
            Artist artist = artistMapper.toDomain(artistDTO);
            artist.setId(id);
            artist.setName(oldArtist.get().getName());
            artist.setConcertList(oldArtist.get().getConcertList());

            //In case the required attributes are provided, the artist can have page
            artist.setHasPage(oldArtist.get().isHasPage());
            artist.setHasPage(true);

            artistRepository.save(artist);
            return artistMapper.toDTO(artist);
        } else {
            throw new ArtistNotFoundException(id);
            /*There was not an artist with such ID, it is thrown*/
        }
    }

    /**
     * Service method that replaces the main-photo of an artist. If the artist does not exist, an
     * ArtistNotFoundException is thrown. If there is any problem getting the image Blob, the exception
     * is returned
     *  
     * @param id the artist identifier
     * @param image the image that will be turned into the artist's main-photo
     * @throws IOException
     */
    public void replacePhotoImage(long id, MultipartFile image) throws IOException{

        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){
            if(artist.get().getPhotoLink() == null){
                throw new ImageException("Artist does not have a latest photo");
            }
            try {
                artist.get().setPhoto(imageService.getBlobOf(image));
                artistRepository.save(artist.get());
            } catch (IOException e) {
                throw new ImageException(e.toString());
            }
        } else {
            throw new ArtistNotFoundException(id);
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

        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){
            artistRepository.deleteById(id); //We delete the artist with that ID
            return artistMapper.toDTO(artist.get());
        } else {
            throw new ArtistNotFoundException(id);
        }

    }

    /**
     * Method that handles the deletion of an Artist's main-photo by its ID. In case the
     * artist does not exist, an ArtistNotFoundException is thrown. In case the photo does
     * not exist, an ImageException is thrown.
     * 
     * @param id identifier of the artist which main photo will be deleted
     */
    public void deletePhotoImage(long id){

        Optional<Artist> artist = artistRepository.findById(id);

        if(artist.isPresent()){
            if(artist.get().getPhotoLink() != null){
                artist.get().setPhoto(null);
                artist.get().setPhotoLink(null);
                artistRepository.save(artist.get());
            } else {
                throw new ImageException("That image does not exist");
            }
        } else {
            throw new ArtistNotFoundException(id);
        }
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