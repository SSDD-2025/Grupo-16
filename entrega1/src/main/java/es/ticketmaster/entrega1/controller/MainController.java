package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.model.ActiveUser;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private ActiveUser activeUser;

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
     * @return the main template
     */
    @GetMapping("/")
    public String getMain(Model model, @RequestParam(required = false) String search, HttpSession session) {
        boolean userLogged = userService.isLogged();
        if (search == null) {
            if (session.isNew()){
                activeUser.init();
                model.addAttribute("isLogged", false);
            } else {
                model.addAttribute("isLogged", userLogged);
            }
            
            model.addAttribute("concertList", concertService.getConcertDisplay(userLogged));
            model.addAttribute("artistList", artistService.getArtistDisplay());
        } else {

            model.addAttribute("isLogged", false);
            model.addAttribute("artist-list", artistService.getSearchBy(search));
            model.addAttribute("generalConcertsList", concertService.getSearchBy(search));

            return "search-page";

        }
        return "main";
    }

}
