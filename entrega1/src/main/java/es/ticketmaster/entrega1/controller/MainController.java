package es.ticketmaster.entrega1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.UserService;


@Controller
public class MainController {
    @Autowired
    private UserEntity activeUser;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private UserService userService;



    @GetMapping("/")
    public String getMain(Model model) {
        model.addAttribute("isLogged", userService);
        return "main";
    }   
}