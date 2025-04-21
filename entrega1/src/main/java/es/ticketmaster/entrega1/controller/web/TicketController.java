package es.ticketmaster.entrega1.controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ConcertService concertService;

    /**
     * This method will be in charge of showing all the ticket information that
     * the user has purchased. This will be showed in the "purchase.html"
     * template.
     *
     * @param model is the model of the dinamic HTML document.
     * @param id is the concert identification number.
     * @param number is the ammount of tickets the user has purchased.
     * @param ticketType is the type of the ticket (the zone/section).
     * @return the "purchase.html" template.
     */
    @PostMapping("/concert/{id}/purchase")
    public String showPurchaseInformation(Model model, @PathVariable long id, @RequestParam int number, @RequestParam String ticketType) {
        ConcertDTO concert = this.concertService.findConcertById(id);
        if (this.concertService.existConcert(concert)) {
            /* Verify if the concert with such id exist. */
            /* It will check if there are tickets available for the type of ticket that has been chosen. */
            boolean available = this.concertService.verifyAvailability(id, number, ticketType);
            model.addAttribute("concert", concert);
            model.addAttribute("ticket", available);
            model.addAttribute("number", number);
            /* The next two are for the hidden "inputs" in the "purchase.html" file, 
            to pass this information to the showPurchaseConfirmation method. 
            This method can be found right after the current method. */
            model.addAttribute("ticketType", ticketType);
            model.addAttribute("number", number);
            float totalPrice = this.concertService.getTotalPrice(concert,number);
            model.addAttribute("total-price", totalPrice);
            return "purchase";
        } else {
            /* If the concert does not exists, then an error page will be shown.*/
            return "redirect:/error";
        }
    }

    /**
     * This method will be in charge of sending the user to the purchase
     * confirmation page.
     *
     * @param model is the model of the dinamic HTML document.
     * @param id is the identification number for the concert.
     * @param ticketType is the zone/section of the ticket.
     * @param number of tickets that the user has chosen.
     * @return If there is no error on the card`s information, it will shown de
     * "purchase-confirmation" template. If not, the same "purchase" template
     * but with an error message.
     */
    @PostMapping("/concert/{id}/purchase/confirmation")
    public String showPurchaseConfirmation(Model model, @PathVariable long id, @RequestParam String ticketType, @RequestParam int number) {
        this.ticketService.associateUserWithTicket(ticketType, number, id);
        return "purchase-confirmation";
    }

    /**
     * This method will redirect to the ticket sellection page when the user
     * cancel the purchase.
     *
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param model is the model of the dinamic HTML document.
     * @param id is the identification number for the concert.
     * @param type is the zone/section of the ticket.
     * @param number of tickets that the user has selected.
     * @return the page where the user can select the zone and the ammount of
     * tickets he wants.
     */
    @PostMapping("/concert/{id}/cancel-purchase")
    public String cancelPurchase(Model model, @PathVariable long id, @RequestParam String type, @RequestParam int number) {
        this.concertService.restauringTickets(id, number, type);
        return "cancel-verification";
    }

    /**
     * Method that controls and handles the ticket deletion. After the deletion
     * (or the attempt) of, the user is redirected depending on the success of
     * the operation. If everything went OK, the user page is reloaded
     * (redirected to /profile?showMyConcerts=true). In other case, the error
     * page is returned
     *
     * @param model actual dynamic HTTP model
     * @param id id of the ticket
     * @return the page where the user is redirected
     */
    @GetMapping("/ticket/{id}/delete")
    public String ticketDeletion(Model model, @PathVariable long id) {

        this.ticketService.deleteTicketWithId(id);
        return "redirect:/profile?showMyConcerts=true";
    }

    /**
     * This method stablishes the needed variables as far as the ticket controller is concerned.
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