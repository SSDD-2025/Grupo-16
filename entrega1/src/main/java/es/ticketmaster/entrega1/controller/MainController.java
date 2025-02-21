package es.ticketmaster.entrega1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;


@Controller
public class MainController {
    @Autowired
    private UserEntity activeUser;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcertService concertService;

    /** It will show the main page with the correct lists displays regarding if the user is logged or not
     * @param model is the model of the dinamic HTML document
     * @return the main template
     */
    @GetMapping("/")
    public String getMain(Model model) {
        boolean userLogged = userService.isLogged(activeUser);
        model.addAttribute("isLogged", userLogged);
        model.addAttribute("concertList", concertService.getConcertDisplay(userLogged));
        model.addAttribute("artistList", artistService.getArtistDisplay());
        return "main";
    }
    
}
