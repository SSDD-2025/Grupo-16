package es.ticketmaster.entrega1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;


@Controller
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

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

    @GetMapping("/admin/concert/{id}/modify")
    public String formAddConcert(Model model, @PathVariable Long id) {
        model.addAttribute("concert", concertService.getConcertById(id));
        model.addAttribute("artistList",artistService.getEveryArtist());
        return "concert-workbench";
    }
    
    @PostMapping("/admin/concert/workbench")
    public String formModifyConcert(Model model) {
        model.addAttribute("concert",null);
        model.addAttribute("artistList",artistService.getEveryArtist());
        return "concert-workbench";
    }
    

    @PostMapping("/admin/concert/register")
    public String postAddConcert(Model model, @ModelAttribute Concert newConcert, 
        @RequestParam(required = false) MultipartFile poster, @RequestParam(required = false) Long id, 
        @RequestParam(required = false) String newArtistName, @RequestParam Long artistId) throws IOException {
        if (artistId == -1){ //the concert is from a new artist not registered yet
            artistId = artistService.createNewArtist(newArtistName);
        } //else {} the concert is from someone already on the datatbase

        if (id != null){ //modify concert
            concertService.modifyConcert(newConcert,id,poster,artistId);
            model.addAttribute("modify", true);
        } else { //add concert
            concertService.saveConcert(newConcert,poster,artistId);
            model.addAttribute("added", true);
        }
        return "concert-validation";
    }
}
