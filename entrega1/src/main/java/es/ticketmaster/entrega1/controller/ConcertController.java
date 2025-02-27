package es.ticketmaster.entrega1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.Concert;
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
    
    @GetMapping("/admin/concert")
    public String getAdminConcert(Model model, @RequestParam String search) {
        if (search != null) {
            model.addAttribute("artistList", concertService.getSearchBy(search));
        } else {
            model.addAttribute("artistList", concertService.getAllConcerts()); //Show every artist
        }

        /*Artists are displayed in modify mode*/
        model.addAttribute("modifyArtist", true);
        return "admin-concerts";
    }

    @GetMapping("/admin/concert/register")
    public String postAddConcert(Model model, @ModelAttribute Concert newConcert, @RequestParam(required = false) MultipartFile poster, @RequestParam(required = false) Long id) throws IOException {
        if (id != null){ //modify concert
            concertService.modifyConcert(newConcert,id,poster);
            model.addAttribute("modify", true);

        } else { //add concert
            concertService.saveConcert(newConcert,poster);
            model.addAttribute("added", true);
        }
        return "concert-validation";
    }
}
