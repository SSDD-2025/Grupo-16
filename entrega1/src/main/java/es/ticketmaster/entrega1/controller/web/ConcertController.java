package es.ticketmaster.entrega1.controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ArtistService artistService;

    /**
     * When the user is logged, the ticket selection page of the concert defined
     * by the id parameter is displayed.
     * It is compulsory for the user to be logged in order to buy tickets.
     *
     * @param model model of the dinamic HTML document
     * @param id concert identification to load its ticket choosing page
     * @return name of the html to be responded to the server petition
     */
    @GetMapping("/concert/{id}")
    public String postMethodName(Model model, @PathVariable Long id) {

        model.addAttribute("concert", concertService.findConcertById(id));
        return "ticket-selling";
    }

    /**
     * It loads the administrator page to add or modify concerts
     *
     * @param model the model of the HTML
     * @param search an optional parameter that includes the search done to look
     * for concerts containing that search on their name
     * @return the admin-concerts.html with its attributes added
     */
    @GetMapping("/admin/concert")
    public String getAdminConcert(Model model, @RequestParam(required = false) String search) {

        if (search != null) {
            model.addAttribute("concertList", concertService.getSearchBy(search));
        } else {
            model.addAttribute("concertList", concertService.getAllConcerts()); //Show every concert
        }

        /*Artists are displayed in modify mode*/
        model.addAttribute("modifyConcert", true);
        return "admin-concerts";
    }

    /**
     * This controller sets the attributes on the concert-workbench when a
     * concert is selected for modification
     *
     * @param model the HTML model thats is going to be shown
     * @param id the id of the concert that is being modified
     * @return the concert-workbench.html with the appropriate attributes set
     */
    @PostMapping("/admin/concert/{id}/modify")
    public String formModifyConcert(Model model, @PathVariable long id, @RequestParam Pageable pageable) {

        model.addAttribute("concert", concertService.findConcertById(id));
        model.addAttribute("artistList", artistService.getEveryArtist(pageable));
        return "concert-workbench";
    }

    /**
     * This controller sets the attributes on the concert-workbench when the
     * administrator selects to add a new concert
     *
     * @param model the HTML model thats is going to be shown
     * @return the concert-workbench.html with the appropriate attributes set
     */
    @PostMapping("/admin/concert/workbench")
    public String formAddConcert(Model model) {
        model.addAttribute("concert", null);
        model.addAttribute("artistList", artistService.getEveryArtist());
        return "concert-workbench";
    }

    /**
     * This controller will call the concertService to either add a new concert
     * to the database or to modify an existing one based on the data obtained
     * from the form that the admin filled out (concert-workbench.html)
     *
     * @param model the HTML model that will be displayed
     * @param newConcert JPA concert created with all the data obtained from the
     * form
     * @param poster MultipartFile containing the image uploaded by the admin in
     * the form
     * @param id if the concert is being modified, it's its id (to be set on the
     * newConcert so it can be modified on the DDBB)
     * @param newArtistName name of a new artist (if the artist is not in the
     * database)
     * @param artistId the artist id whose concert is being added/ modified (-1
     * if its a new artist)
     * @return the concert-validation.html with the appropriate attributes set
     * @throws Exception 
     */
    @PostMapping("/admin/concert/register")
    public String postAddConcert(Model model, @ModelAttribute ConcertDTO newConcert,
            @RequestParam(required = false) MultipartFile poster, @RequestParam(required = false) Long id,
            @RequestParam(required = false) String newArtistName, @RequestParam Long artistId) throws Exception {
        if (artistId == -1) { //the concert is from a new artist not registered yet
            // Check if the name the admin entered already exists
            if (artistService.artistExists(newArtistName)) {
                artistId = artistService.getByNameIgnoreCase(newArtistName).get().getId();
            } else {
                ArtistDTO artistWithName = new ArtistDTO(null, newArtistName, null, null, null, null, null, null, null, null);
                artistId = artistService.registerNewArtist(artistWithName).id();
            }
        } //else {} the concert is from someone already on the datatbase

        if (id != null) { //modify concert
            concertService.modifyConcert(newConcert, id, poster, artistId);
            model.addAttribute("modified", true);
        } else { //add concert
            concertService.saveConcert(newConcert, poster, artistId);
            model.addAttribute("added", true);
        }
        return "concert-validation";
    }

    /**
     * This method of the controller will be executed when the admin wants to
     * delete a concert
     *
     * @param model the HTML model that will be displayed
     * @param id id of the concert that is being deleted
     * @return the concert-validation.html with the appropriate attributes set
     */
    @GetMapping("/admin/concert/delete")
    public String postDeleteConcert(Model model, @RequestParam long id) {
        concertService.deleteConcert(id);
        model.addAttribute("deleted", true);
        return "concert-validation";
    }

    /**
     * This method stablishes the needed variables as far as the concert controller is concerned.
     * The information needed is: if the user is logged or if the user is an admin. This variables
     * are used to show diferent UI corresponding to their role.
     * The way this method works is adding the correspondent attributes to the model 
     * when any of the previous controller methods are called so that the information is pre-loaded.
     * 
     * @param model is the model of dynamic HTTP
     * @param request is the HTTP request
     */
    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if (principal!=null) { //If there is a principal user (means it is logged)
            model.addAttribute("isLogged", true);
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        } else {
            model.addAttribute("isLogged", false);
        }
    }
}