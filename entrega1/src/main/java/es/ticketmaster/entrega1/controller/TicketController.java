package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.service.CardVerifyingService;


@Controller
public class TicketController {
    @Autowired
    private CardVerifyingService cardService;

    /**
     * This method will be in charge of sending the user to the purchase confirmation page 
     * if the credit card`s information is valid. If something happen, an error message will appear informing the user.
     * @param model
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
        return "purchase"; /* THIS IS NOT DEFINITIVE. It remains to add the variable mustache (error) to the template, 
                           so that it displays the error message in case one occurs.
                           */
    }
}