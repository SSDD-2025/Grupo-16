package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.service.CardVerifyingService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.TicketService;


@Controller
public class TicketController {
    @Autowired
    private CardVerifyingService cardService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ConcertService concertService;

    /**
     * This method will be in charge of showing all the ticket information that the user has pruchased.
     * This will be showed in the "purchase.html" template.
     * Apart for that, all the purchased tickets will be associated to the user and viceversa.
     * This last operation is done by the associateUserWithTicket method, implemented in the ticketService class.
     * @param model is the model of the dinamic HTML document.
     * @param id is the concert identification number.
     * @param number is the ammount of tickets the user has purchased.
     * @param ticketType is the type of the ticket (the zone/section).
     * @return the "purchase.html" template.
     */
    @PostMapping("/concert/{id}/purchase")
    public String showPurchaseInformation(Model model, @PathVariable long id, @RequestParam int number, @RequestParam String ticketType) {
        Concert concert = this.concertService.getConcertById(id);
        this.ticketService.associateUserWithTicket(ticketType, number);
        model.addAttribute("ticket", this.concertService.verifyAvailability(id, number, ticketType));
        model.addAttribute("name", concert.getName());
        model.addAttribute("date", concert.getDate());
        model.addAttribute("number", number);
        model.addAttribute("price", concert.getPrice());
        float totalPrice = concert.getPrice() * number;
        model.addAttribute("total-price", totalPrice);
        return "purchase";
    }
    

    /**
     * This method will be in charge of sending the user to the purchase confirmation page 
     * if the credit card`s information is valid. If something happen, an error message will appear informing the user.
     * @param model is the model of the dinamic HTML document.
     * @param cardHolder is the name of the credit card holder introduced by the user.
     * @param cardType is the type of the credit card (Visa, MasterCard or American Express) selected by the user.
     * @param cardId is the credit card identification number introduced by the user.
     * @param expDate is the credit card expiration date introduced by the user.
     * @param cvv is the credit card security number introduced my the user.
     * @return If there is no error on the card`s information, it will shown de "purchase-confirmation" template. 
     * If not, the same "pruchase" template but with an error message.
     */
    @PostMapping("/concert/{id}/purchase/confirmation")
    public String showPurchaseConfirmation(Model model,@RequestParam String cardHolder, @RequestParam String cardType, @RequestParam String cardId, @RequestParam String expDate, @RequestParam String cvv) {
        if ((this.cardService.verifyCardHolder(cardHolder)) && (this.cardService.getType(cardType) != null) && 
            (this.cardService.verifyCreditCardNumber(cardId)) && (this.cardService.verifyExpirationDate(expDate)) && 
            (this.cardService.verifyCVV(cvv))) {
                return "purchase-confirmation";
        }
        model.addAttribute("error", true);
        return "purchase";
    }
}