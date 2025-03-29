package es.ticketmaster.entrega1.controller.web;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.dto.user.UserDTO;
import es.ticketmaster.entrega1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Will show the sign in display on the "sign-in.html" file.
     *
     * @param model is the model of the dinamic HTML document.
     * @param error if there has been any error in previous attempts of logging in.
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
     * @param user represents a determine user in DTO format.
     * @return the sign-in-validation template (if everything goes well), in
     * other case, will shown the sign-in template, with a message of error.
     */
    @PostMapping("/sign-up/validation")
    public String verifySignUp(Model model, @ModelAttribute UserDTO user) {
        if(!userService.registerUser(user)){ // The registration couldn't be done.
            model.addAttribute("newUser", true);
            return "sign-in";
        }
        return "redirect:/sign-in";
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
     * @return the my-profile template.
     */
    @GetMapping("/profile")
    public String accessToProfile(Model model, @RequestParam(required = false) boolean showPersonalInfo,
            @RequestParam(required = false) boolean showMyArtists, @RequestParam(required = false) boolean showMyConcerts,
            HttpServletRequest request) {
        
        Principal user = request.getUserPrincipal();
        if (showMyConcerts) {
            model.addAttribute("showMyConcerts", true);
            model.addAttribute("ticketList", userService.getTicketsForActiveUser(user).ticketList());
        } else if (showMyArtists) {
            model.addAttribute("showMyArtists", true);
            model.addAttribute("artistList", false); // When this DTO is implemented, it will be changed.
        } else { //In case there is no display option, personal information is displayed
            model.addAttribute("user", userService.getActiveUserWithProfilePicture(user));
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

    /**
     * (PROVISIONAL) This method that stabishes default attributes for aspects dealing with the UserController
     * This method should be completed so that the previous controller methods work correctly within the model
     * they have to provide 
     * 
     * @param model is the dynamic HTTP model
     * @param request is the HTTP request to fulfill
     */
    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request){

        Principal principal = request.getUserPrincipal();

        if (principal!=null) {
            model.addAttribute("isLogged", true);
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        } else {
            model.addAttribute("isLogged", false);
        }
    }
}