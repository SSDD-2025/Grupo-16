package es.ticketmaster.entrega1.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    /**
     * It will show the main page with the correct lists displays regarding if
     * the user is logged or not
     *
     * @param model is the model of the dinamic HTML document
     * @param search (Optional) Search introduced by the user in the searchbar
     * @return the main template
     */
    @GetMapping("/")
    public String getMain(Model model, @RequestParam(required = false) String search, HttpSession session) {

        boolean userLogged = userService.isLogged();

        if (search == null) {

            model.addAttribute("concertList", concertService.getConcertDisplay(userLogged));
            model.addAttribute("modifyConcert", false);
            model.addAttribute("artistList", artistService.getArtistDisplayBySession());

        } else {

            model.addAttribute("personalConcertsList", concertService.getConcertsNearUser(userLogged));
            model.addAttribute("artistList", artistService.getSearchBy(search));
            model.addAttribute("generalConcertsList", concertService.getSearchBy(search));

            return "search-page";

        }
        return "main";
    }

    /**
     * This method stablishes the needed variables as far as the main controller is concerned.
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
