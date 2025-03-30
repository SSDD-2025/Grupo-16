package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.security.Principal;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.dto.concert.ConcertMapper;
import es.ticketmaster.entrega1.dto.user.ShowUserDTO;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.repository.ConcertRepository;

/**
 * Concert service that gets access to the ConcertRepository to make specific
 * and simple querys. It is thought to be used by controllers to modularizate
 * and simplify the logic inside them.
 */
@Component
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertMapper mapper;

    /**
     * Method that searches for concerts which Concert.name or
     * Concert.artist.name matches with a String and returns a list of concerts
     * with this match.
     *
     * @param search String to search by
     * @return List of Concert that could be empty when no concerts are found
     * @see Concert
     */
    public List<ConcertDTO> getSearchBy(String search) {

        List<ConcertDTO> searchedConcerts = mapper.toDTOs(concertRepository.findByNameContainingIgnoreCaseOrderByArtistPopularityIndexDesc(search));

        if (search.length() > 2) { //To avoid repeated simple input answers
            searchedConcerts.addAll(mapper.toDTOs(concertRepository.findByArtistNameContainingIgnoreCase(search)));
        }

        return searchedConcerts;
    }

    /**
     * Searches the concerts taken at a specific place
     *
     * @param place is the country/ city which the search is based
     * @param principal is the currently authenticated user, used to retrieve the active user.
     * @return the list of the concerts taking place at the country/ city
     * specified
     */
    public List<ConcertDTO> getConcertDisplay(Principal principal) {
        ShowUserDTO user = userService.getActiveUser(principal);
        if (user != null) { //is logged, the display will be of concerts at the same country as the user
            List<ConcertDTO> concertList = mapper.toDTOs(concertRepository.findByPlace(user.country()));
            if (!(concertList.isEmpty())) {
                return concertList;
            }
        }
        //whether the user is not logged or the concertListByCountry is empty, it will return a list of concerts order by artist's fame
        return mapper.toDTOs(concertRepository.findTop7ByOrderByDateAsc()); //provisional until the query is fixed
    }

    /**
     * Gets the all the concerts existing in the DDBB
     *
     * @return List with all the concerts registered
     */
    public List<ConcertDTO> getAllConcerts() {
        return mapper.toDTOs(concertRepository.findAll());
    }

    /**
     * Finds an specific concert by its ID, searching it in the DDBB
     *
     * @param id id of the concert to be searched
     * @return The concert (if it exists), null if there is no match
     */
    public ConcertDTO findConcertById(long id) {

        Optional<Concert> concert = concertRepository.findById(id);

        if (!concert.isEmpty()) {
            /*If a concert has been found*/
            return mapper.toDTO(concert.get());
        } else {
            /*If there is no concert match*/
            return null;
        }
    }

    /**
     * Check the existence of a concert.
     *
     * @param concertDTO the concert in question.
     * @return true if it exist, false otherwise.
     */
    public boolean existConcert(ConcertDTO concertDTO) {
        Concert concert = mapper.toDomain(concertDTO);
        return (concert != null);
    }

    /**
     * @param artistName the artist name whose concert list is being returned
     * @return said list
     */
    public List<ConcertDTO> getArtistConcerts(String artistName) {
        return mapper.toDTOs(concertRepository.findByArtistNameIgnoreCase(artistName));
    }

    /**
     * 
     * @param concertDTO concert that the user wants to buy tickets from
     * @param tickets number of tickets purchase from said concert
     * @return total price of the purchase
     */
    public float getTotalPrice(ConcertDTO concertDTO, int tickets){
        Concert concert = mapper.toDomain(concertDTO);
        return concert.getPrice() * tickets;
    }

    /**
     * 
     * @param id id from the concert whose photo is being returned
     * @return the blob of the photo
     */
    public Blob getConcertImage(long id){
        Concert concert = concertRepository.findConcertById(id);
        if (concert == null){
            return null;
        } else {
            return concert.getImage();
        }
    }

    /**
     * This method will verify is there are available tickets for a specific
     * type (section).
     *
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets the user has purchased.
     * @param type is the type (section) of the ticket.
     * @return if the rows has been updated (1) true, otherwise, false.
     */
    public boolean verifyAvailability(long id, int number, String type) {
        int result = 0;
        if (type.equalsIgnoreCase("West")) {
            result = this.concertRepository.availableWestStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("East")) {
            result = this.concertRepository.availableEastStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("North")) {
            result = this.concertRepository.availableNorthStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("General")) {
            result = this.concertRepository.availableGeneralAdmissionTickets(id, number);
        }
        return result == 1;
    }

    /**
     * This method will restore the selected tickets when the user cancel de
     * purchase.
     *
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets the user has purchased.
     * @param type is the type (section) of the ticket.
     */
    public void restauringTickets(long id, int number, String type) {
        if (type.equalsIgnoreCase("West")) {
            this.concertRepository.restoreWestStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("East")) {
            this.concertRepository.restoreEastStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("North")) {
            this.concertRepository.restoreNorthStandsTickets(id, number);
        } else if (type.equalsIgnoreCase("General")) {
            this.concertRepository.restoreGeneralAdmissionTickets(id, number);
        }
    }

    /**
     * This method will save a new concert on the database
     *
     * @param concert the concert that is being uploaded to the database
     * @param poster the image associated with the concert
     * @param artistId the id whose concert is being uploaded to the DDBB
     * @throws IOException if an error occurs during file handling
     */
    public void saveConcert(ConcertDTO concert, MultipartFile poster, long artistId) throws IOException {
        Concert newConcert = mapper.toDomain(concert);
        if ((poster != null) && (!poster.isEmpty())) {
            newConcert.setImage(imageService.getBlobOf(poster));
        }

        //set the artist
        newConcert.setArtist(artistService.getArtist(artistId));

        concertRepository.save(newConcert);
    }

    /**
     * This method will modify a concert, setting its attributes the correct way
     * and saving this changes onto the database
     *
     * @param concert it is the new concert that has to be uploaded (after
     * certain modifications are done)
     * @param id id of the concert that is being modificated
     * @param poster the image associated with the concert (can be a new image)
     * @param artistId the artist whose concert is being modified (it can be a
     * new artist)
     * @throws IOException if an error occurs during file handling
     */
    public void modifyConcert(ConcertDTO concert, long id, MultipartFile poster, long artistId) throws IOException {
        // concert has the number of added tickets
        // so then modified concert is concert with the old concert available tickets added
        Concert modifiedConcert = mapper.toDomain(concert);
        Concert oldConcert = concertRepository.findConcertById(id);
        modifiedConcert.addTickets(oldConcert);
        if (poster != null) {
            modifiedConcert.setImage(imageService.getBlobOf(poster));
        } else {
            modifiedConcert.setImage(oldConcert.getImage());
        }

        // set the old id to the modified concert
        modifiedConcert.setId(id);

        //set the artist
        modifiedConcert.setArtist(artistService.getArtist(artistId));

        concertRepository.save(modifiedConcert);
    }

    /**
     * This method will delete a concert from the datatbase
     *
     * @param id id of the concert that is being deleted
     */
    public void deleteConcert(long id) {
        concertRepository.deleteById(id);
    }

    /**
     * Method that searched by country and depending on the user login state
     *
     * @param userLogged if the user is logged or not.
     * @param principal is the currently authenticated user, used to retrieve the active user.
     * @return list (empty or with elements) of concerts near the activeUser
     */
    public List<ConcertDTO> getConcertsNearUser(Principal user) {
        if (user != null) { //is logged, the display will be of concerts at the same country as the user
            ShowUserDTO actualUser = userService.getActiveUser(user);
            if (actualUser != null) {
                String country = actualUser.country();
                return mapper.toDTOs(concertRepository.findByPlace(country));
            }
        }
        //whether the user is not logged or does not exist
        return null; //No list returned
    }

    /**
     * Method that restores one ticket availability in a given zone of an
     * specific concert
     *
     * @param concertDTO the concert to restore which ticket is about to be
     * restored
     * @param zone zone where the ticket is restored
     * @return true if the restoration happened correctly, false in other case
     */
    public boolean returnTicket(ConcertDTO concertDTO, String zone) {
        Concert concert = mapper.toDomain(concertDTO);
        return switch (zone) {
            /*Depending on the zone, a different repository method for restoration is used*/
            case "North" ->
                concertRepository.restoreNorthStandsTickets(concert.getId(), 1) > 0;
            case "East" ->
                concertRepository.restoreEastStandsTickets(concert.getId(), 1) > 0;
            case "West" ->
                concertRepository.restoreWestStandsTickets(concert.getId(), 1) > 0;
            case "General" ->
                concertRepository.restoreGeneralAdmissionTickets(concert.getId(), 1) > 0;
            default ->
                false;
        };
    }
}