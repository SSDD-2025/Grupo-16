package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.ticketmaster.entrega1.service.ConcertService;


/**
 * 
 */
@Controller
public class ConcertController {

    @Autowired
    ConcertService concertService;

    @GetMapping("/concert/{id}")
    public String postMethodName(Model model, @PathVariable Long id) {
        
        model.addAttribute("concert", concertService.getConcertById(id));

        return "ticket-selling";
    }
    
    
}
