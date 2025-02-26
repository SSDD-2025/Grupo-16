package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;


@Controller
public class ConcertController {

    @Autowired
    ConcertService concertService;

    @Autowired
    UserService userService;

    /**
     * When the user is logged, the ticket selection page of the concert defined by the id parameter is displayed.
     * In other case, an error page is shown. It is compulsory for the user to be logged in order to buy tickets.
     * @param model model of the dinamic HTML document
     * @param id concert identification to load its ticket choosing page
     * @return name of the html to be responded to the server petition
     */
    @GetMapping("/concert/{id}")
    public String postMethodName(Model model, @PathVariable Long id) {
        
        boolean userLogged = userService.isLogged();

        if(userLogged){
            model.addAttribute("concert", concertService.getConcertById(id));
            model.addAttribute("isLogged", true);
            return "ticket-selling";
        } else {
            return "redirect:/sign-in"; /*If the user isn't logged, it is redirected to the sign-in page*/
        }
    }
    
    
}
