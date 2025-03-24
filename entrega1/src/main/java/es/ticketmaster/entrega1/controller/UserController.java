package es.ticketmaster.entrega1.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.ActiveUser;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActiveUser activeUser;

    /**
     * Will show the sign in display on the "sign-in.html" file.
     *
     * @param model is the model of the dinamic HTML document.
     * @param error if there has been any error in previous attempts of logging in
     * @return the sign-in template.
     */
    @GetMapping("/sign-in")
    public String signInUser(Model model, @RequestParam(required = false) boolean error) {
        model.addAttribute("existUser", true);
        if (error) {
            model.addAttribute("error", true);
        }
        return "sign-in";
    }

    /**
     * Will show the sign in display on the "sign-in.html" file.
     *
     * @param model is the model of the dinamic HTML document.
     * @return the sign-in template.
     */
    @GetMapping("/sign-up")
    public String signUpUser(Model model) {
        model.addAttribute("existUser", false);
        return "sign-in";
    }

    /**
     * It will verify if the userName introduced is valid (does not exist).
     *
     * @param model is the model of the dinamic HTML document.
     * @param userName is the one introduced by the user.
     * @param password is the one introduced by the user.
     * @param country is the country from where the user is from.
     * @param email is the email from the user.
     * @return the sign-in-validation template (if everything goes well), in
     * other case, will shown the sign-in template, with a message of error.
     */
    @PostMapping("/sign-up/validation")
    public String verifySignUp(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String country, @RequestParam String email) {
        UserEntity newUser = this.userService.verifyUser(username);
        model.addAttribute("existUser", false);
        if (newUser == null) { // The user does not exist on the database, therefore, can be register.
            newUser = new UserEntity(username, password, email, country);
            this.userService.registerUser(newUser);
            this.activeUser.setNewActiveUser(this.userService.recoverUser(username, password)); // A new session is established.
            model.addAttribute("welcome", newUser.getUserName());
            return "validation";
        }
        // The user exist on the database, therefore, can not be register. 
        model.addAttribute("newUser", true);
        return "sign-in";
    }

    /**
     * Profile page controller, that displays a series of information depending
     * on the parameters it receives. Going from personal information of the
     * user that can be changed (country and photo for now), to favourite
     * artists and purchased tickets.
     *
     * @param model actual model of the dynamic HTML document
     * @param showPersonalInfo if true, personal information is displayed (with
     * its form)
     * @param showMyArtists if true, favourite artists list is diplayed
     * @param showMyConcerts if true, actual purchased tickets are displayed
     * @return
     */
    @GetMapping("/profile")
    public String accessToProfile(Model model, @RequestParam(required = false) boolean showPersonalInfo,
            @RequestParam(required = false) boolean showMyArtists, @RequestParam(required = false) boolean showMyConcerts) {

        if (showMyConcerts) {
            model.addAttribute("showMyConcerts", true);
            //TBC: model.addAttribute("ticketList", userService.getActiveUser().getTicketsList());
        } else if (showMyArtists) {
            model.addAttribute("showMyArtists", true);
            //TBC: model.addAttribute("artistList", userService.getActiveUser().getArtistsList());
        } else { //In case there is no display option, personal information is displayed
            //TBC: model.addAttribute("user", userService.getActiveUser());
            model.addAttribute("showPersonalInfo", true);
        }

        return "my-profile";

    }

    /**
     * Controller which function is to stablish the changed made by the user in
     * its personal page and redirect him/her back to his/her personal page to
     * see the changes applied
     *
     * @param model actual model of the dynamic HTML document
     * @param id if of the user whose changes will be made
     * @param country (optional) new country to be set
     * @param newPhoto new photo to be set
     * @return redirection to personal profile page
     * @throws IOException
     */
    @PostMapping("/profile/changeSettings")
    public String changeUserSettings(Model model, @RequestParam long id, @RequestParam(required = false) String country,
            @RequestParam(required = false) MultipartFile newPhoto) throws IOException {

        if (userService.saveUserWithId(id, country, newPhoto)) {
            return "redirect:/profile?showPersonalInfo=true";
        } else {
            return "redirect:/error";
        }
    }

    /**
     * Method that handles the situation of trying to delete a user. Cases when
     * the ID does not correspond to any user are also handled, redirecting the
     * user to an error page. As well, if the deletion has been achieved, the
     * user is redirected to the main page.
     *
     * @param model model of the actual dynamic HTML
     * @param id id of the future deleted user
     * @return HTML to be loaded
     */
    @PostMapping("/profile/delete-profile")
    public String deleteUserProfile(@RequestParam long id) {

        if (userService.removeExistingUserWithId(id)) {
            return "redirect:/";
        } else {
            return "redirect:/error";
        }
    }
}
