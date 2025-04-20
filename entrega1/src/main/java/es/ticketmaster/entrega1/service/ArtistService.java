package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import es.ticketmaster.entrega1.service.exceptions.NotAllowedException;

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
     * Searches for the artist that has an specific ID and returns its DTO. In
     * case the artist is not present at the DDBB, the pertinent exception is
     * thrown.
     *
     * @param id the artist id that is searched
     * @return The Artist's or Exception DTO if there is not any artist with
     * that id.
     */
    public ArtistDTO getArtist(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {

            return artistMapper.toDTO(artist.get());

        } else {

            throw new ArtistNotFoundException(id);

        }

    }

    /**
     * Searches for every artist in the DDBB, returning a Page with all the
     * artists that fit inside its dimensions.
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
     * Makes the artist search by artist name making use of the repository. A
     * Page of artist is returned, meaning that this method makes use of
     * pagination.
     *
     * @param search string to be searched
     * @param pageable the desired pagination configuration
     * @return List of artists who match with search
     */
    public Page<Artist> getSearchBy(String search, Pageable pageable) {

        return artistRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    /**
     * Makes the artist search by artist name making use of the repository and
     * returning a Page of ArtistDTO. As the returning type is a Page, this
     * method makes use of pagination.
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
    public Artist getArtistEntity(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isEmpty()) {
            return null;
        } else {
            return artist.get();
        }
    }

    /**
     * Service method that returns the main photo of an specified-by-id artist.
     * In case the artist does not exist, an ArtistNotFoundException is thrown.
     * In case the artist does not have main photo, an ImageException with a
     * notification is thrown.
     *
     * @param id identifier of the artist
     * @return The main photo of the artist
     * @throws SQLException
     */
    public Resource getPhoto(long id) throws SQLException {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {
            if (artist.get().getPhotoLink() != null) {
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
     * Service method that builds an artist in order for it to be registered
     * (saved in the DDBB). As well, the register date is saved. As a
     * confirmation, the Artist's DTO is returned.
     *
     * @param artistDTO artist's input DTO
     * @return Artist's DTO representing the registered artist that is saved in
     * the DDBB.
     * @throws Exception
     */
    public ArtistDTO registerNewArtist(ArtistDTO artistDTO) throws Exception {

        /*We get the artist as an Entity object*/
        Artist artist = artistMapper.toDomain(artistDTO);

        /*Checks compulsory conditions in order to create a new artist: Not repeated name*/
        if (artist.getName() == null) {
            throw new NotAllowedException("Create an artist without name");
        }
        if (artistExists(artist.getName())) {
            /*Verifies the Artist name is not repeated*/
            throw new ArtistAlreadyExistsException(artist.getName());
        }

        /*Checks if the artist can(not) have its own page*/
        if (artist.canHavePage()) {
            artist.setHasPage(true);
        } else {
            artist.setHasPage(false);
        }

        /*Attributes automatically stablished by the service*/
        artist.setPhotoLink(null); //There is no posibility any photo is provided when an artist is registered
        artist.setSessionCreated(LocalDateTime.now());

        artistRepository.save(artist);

        return artistMapper.toDTO(artist);
    }

    /**
     * Service method that creates the main-photo of an artist, specified by id.
     * In case no artist with such id is found, it is returned an
     * ArtistNotFoundException.
     *
     * @param id artist identifier
     * @param image image to stablish as main photo
     */
    public void createPhotoImage(long id, MultipartFile image) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {
            if (artist.get().getPhotoLink() != null) {
                throw new ImageException("Artist already has a main photo");
            }
            try {
                artist.get().setPhoto(imageService.getBlobOf(image));
                artist.get().setPhotoLink("/api/artists/"+ artist.get().getId() + "/photo");
                artistRepository.save(artist.get());
            } catch (IOException e) {
                throw new ImageException("Error proccessing the Artist Image: " + e.toString());
            }
        } else {
            throw new ArtistNotFoundException(id);
        }
    }

    /**
     * Service method that modifies an existing artist with new attributes.
     * Every attribute is modificable except for artistName. As well, in case
     * the artist has all the attributes needed to be considered an Artist with
     * page, its hasPage attribute is turned to true. Otherwise, it is set to
     * false.
     *
     * @param artistDTO artist's input DTO with the modified attributes
     * @param id id of that artist
     * @return Artist's DTO representing the modified artist that is saved in
     * the DDBB.
     * @throws IOException
     */
    public ArtistDTO modifyArtistWithId(ArtistDTO artistDTO, long id) throws IOException {

        Optional<Artist> oldArtist = artistRepository.findById(id);

        if (oldArtist.isPresent()) {
            Artist artist = artistMapper.toDomain(artistDTO);
            /*Required attributes that the modified artist inherit from the old artist*/
            artist.setId(id);
            artist.setName(oldArtist.get().getName());
            artist.setConcertList(oldArtist.get().getConcertList());
            artist.setPhoto(oldArtist.get().getPhoto());
            artist.setPhotoLink(oldArtist.get().getPhotoLink());

            /*In case the required attributes are provided, the artist can have page*/
            if (artist.canHavePage()) {
                artist.setHasPage(true);
            } else {
                artist.setHasPage(false);
            }

            artistRepository.save(artist);
            return artistMapper.toDTO(artist);
        } else {
            throw new ArtistNotFoundException(id);
            /*There was not an artist with such ID, it is thrown*/
        }
    }

    /**
     * Service method that replaces the main-photo of an artist. If the artist
     * does not exist, an ArtistNotFoundException is thrown. If there is any
     * problem as far as the image format or content is concerned, an
     * ImageException is returned.
     *
     * NOTE: If the artist does not have any photo, the image is also changed.
     *
     * @param id the artist identifier
     * @param image the image that will be turned into the artist's main-photo
     * @throws IOException
     */
    public void replacePhotoImage(long id, MultipartFile image) throws IOException {

        Optional<Artist> artist = artistRepository.findById(id);

        if (image == null || image.isEmpty()) {
            throw new ImageException("Not a valid image.");
        }

        /*We get the content type to check if the image format is corect*/
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ImageException("Not a valid image format.");
        }

        // We check that the image fulfill the allowed types
        List<String> allowedContentTypes = Arrays.asList("image/jpeg", "image/jpg", "image/png");
        /*CAUTION WITH THE IMAGE NAME*/
        if (!allowedContentTypes.contains(contentType)) {
            throw new ImageException("Only images with JPEG, PNG and JPG format are allowed.");
        }

        if (artist.isPresent()) {
            try {
                artist.get().setPhoto(imageService.getBlobOf(image));
                artistRepository.save(artist.get());
            } catch (IOException e) {
                throw new ImageException("Error proccessing the Artist Image: " + e.toString());
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
     * IMPORTANT NOTE: Since @Generation.type = AUTO, there are no IDs recycled,
     * so that is safe to check for a deleted id to find if it still exists,
     * since it will not be assigned to another artist.
     *
     * @param id the Artist's ID
     * @return the deleted Artist's DTO
     */
    public ArtistDTO deleteArtistWithId(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {
            artistRepository.deleteById(id); //We delete the artist with that ID
            return artistMapper.toDTO(artist.get());
        } else {
            throw new ArtistNotFoundException(id);
        }

    }

    /**
     * Method that handles the deletion of an Artist's main-photo by its ID. In
     * case the artist does not exist, an ArtistNotFoundException is thrown. In
     * case the photo does not exist, an ImageException is thrown.
     *
     * @param id identifier of the artist which main photo will be deleted
     */
    public void deletePhotoImage(long id) {

        Optional<Artist> artist = artistRepository.findById(id);

        if (artist.isPresent()) {
            if (artist.get().getPhotoLink() != null) {
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
}
