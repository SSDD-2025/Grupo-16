package es.ticketmaster.entrega1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private UserEntity activeUser;

    /* Will show the sign in display on the "sign-in.html" file.
    @return the sign-in template.
    */
    @GetMapping("/sign-in")
    public String signInUser(Model model) {
        model.addAttribute("existUser", true);
        return "sign-in";
    }

    /* Will show the sign in display on the "sign-in.html" file.
    @return the sign-in template.
    */
    @GetMapping("/sign-up")
    public String signUpUser(Model model) {
        model.addAttribute("existUser", false);
        return "sign-in";
    }

    /* It will verify if the userName introduced is valid (does exist). 
    @param userName is the one introduced by the user.
    @param password is the one introduced by the user.
    @return the sign-in-validation template (if everything goes well), 
    in other case, will shown the sign-in template, with a message of error.
    */
    @PostMapping("/TBD") /* This URL will be specified soon */
    public String verifySignIn(Model model, @RequestParam String userName, @RequestParam String password) {
        UserEntity receivedUser = this.userService.verifyUser(userName);
        /* Verify if the user exist on the database and his password matches */
        if ((receivedUser != null) && (this.userService.verifyPassword(userName, password))) {
            this.activeUser = this.userService.recoverUser(userName, password); /* Session is established. */
            model.addAttribute("name", receivedUser.getUserName());
            return "sign-in-validation";
        }
        model.addAttribute("error", true);
        return "sign-in"; /* It will shown the same template, but, with a message indicating the user
                         if the userName or password (or both) do not match. */
    }

    /* It will verify if the userName introduced is valid (does not exist). 
    @param userName is the one introduced by the user.
    @param password is the one introduced by the user.
    @param country is the country from where the user is from.
    @email is the email from the user.
    @return the sign-in-validation template (if everything goes well), 
    in other case, will shown the sign-in template, with a message of error.
    */
    @PostMapping("/TBD") /* This URL will be specified soon */
    public String verifySignUp(Model model, @RequestParam String userName, @RequestParam String password, @RequestParam String country, @RequestParam String email) {
        if ((password.isBlank()) || (country.isBlank()) || (email.isBlank())) {
            model.addAttribute("missingInformation", true);
        }
        else {
            UserEntity newUser = this.userService.verifyUser(userName);
            if (newUser == null) { /* The user does not exist on the database, therefore, can be register. */
                newUser = new UserEntity(userName, password, email, country);
                this.userService.registerUser(newUser);
                this.activeUser = this.userService.recoverUser(userName, password); /* A new session is established. */
                model.addAttribute("welcome", newUser.getUserName());
                return "sign-up-validation";   
            }
        }
        /* The user exist on the database, therefore, can not be register. */
        model.addAttribute("newUser", true);
        return "sign-in";
    }

    @GetMapping("/")
    public String showMain() {
        return "main";
    } 
}