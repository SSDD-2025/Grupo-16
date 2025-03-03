package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.TicketService;



@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private ConcertService concertService;

    /**
     * This method will be in charge of showing all the ticket information that the user has purchased.
     * This will be showed in the "purchase.html" template.
     * @param model is the model of the dinamic HTML document.
     * @param id is the concert identification number.
     * @param number is the ammount of tickets the user has purchased.
     * @param ticketType is the type of the ticket (the zone/section).
     * @return the "purchase.html" template.
     */
    @PostMapping("/concert/{id}/purchase")
    public String showPurchaseInformation(Model model, @PathVariable long id, @RequestParam int number, @RequestParam String ticketType) {
        Concert concert = this.concertService.findConcertById(id);
        if(this.concertService.existConcert(concert)){ /* Verify if the concert with such id exist. */
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
            float totalPrice = concert.getPrice() * number;
            model.addAttribute("total-price", totalPrice);
            return "purchase";
        } 
        else { /* If the concert does not exists, then an error page will be shown.*/
            return "redirect:/error";
        }
    }
    
    /**
     * This method will be in charge of sending the user to the purchase confirmation page.
     * @param model is the model of the dinamic HTML document.
     * @param id is the identification number for the concert.
     * @param ticketType is the zone/section of the ticket.
     * @param number of tickets that the user has chosen.
     * @return If there is no error on the card`s information, it will shown de "purchase-confirmation" template. 
     * If not, the same "purchase" template but with an error message.
     */
    @PostMapping("/concert/{id}/purchase/confirmation")
    public String showPurchaseConfirmation(Model model, @PathVariable long id, @RequestParam String ticketType, @RequestParam int number) {
        this.ticketService.associateUserWithTicket(ticketType, number, id);
        return "purchase-confirmation";
    }

    /**
     * This method will redirect to the ticket sellection page when the user cancel the purchase.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param model is the model of the dinamic HTML document.
     * @param id is the identification number for the concert.
     * @param type is the zone/section of the ticket.
     * @param number of tickets that the user has selected.
     * @return the page where the user can select the zone and the ammount of tickets he wants.
     */
    @PostMapping("/concert/{id}/cancel-purchase")
    public String cancelPurchase(Model model, @PathVariable long id, @RequestParam String type, @RequestParam int number) {
        this.concertService.restauringTickets(id, number, type);
        return "cancel-verification";
    }  

    /**
     * Method that controls and handles the ticket deletion. After the deletion (or the attempt) of,
     * the user is redirected depending on the success of the operation. If everything went OK,
     * the user page is reloaded (redirected to /profile?showMyConcerts=true). In other case, the
     * error page is returned
     * 
     * IMPORTANT NOTE: Security should be added in this method in the future because if the user changes
     * the ID in the HTML, other concert's tickets could be returned, causing inconsistence.
     * 
     * @param model actual dynamic HTTP model
     * @param id id of the ticket
     * @return the page where the user is redirected
     */
    @PostMapping("/ticket/delete")
    public String ticketDeletion(Model model, @RequestParam long id) {
        
        if(ticketService.deleteTicketWithId(id)){
            return "redirect:/profile?showMyConcerts=true";
        } else {
            return "redirect:/error";
        }
    }
    
}