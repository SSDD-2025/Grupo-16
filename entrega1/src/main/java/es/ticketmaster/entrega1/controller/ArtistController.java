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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.UserService;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    /**
     *
     * @param model its the model of the dinamic html document
     * @param id its the artist id which is passed on the URL like a
     * pathVariable
     * @param artistName its the artist name passed as an attribute on the path
     * @return artist.html with its attributes The artistName parameter it is
     * only used if the artist searched does not have a main page but has
     * concerts available
     */
    @GetMapping("/artist/{id}")
    public String getArtistPage(Model model, @PathVariable long id) {

        boolean userLogged = userService.isLogged();
        model.addAttribute("isLogged", userLogged);

        Artist artist = artistService.getArtist(id);
        if (artist != null) {
            model.addAttribute("artist", artist);
            model.addAttribute("titleName", artist.getName());
            model.addAttribute("hasPage", artist.isHasPage());
            model.addAttribute("concertList", concertService.getArtistConcerts(artist.getName()));
        } else {
            model.addAttribute("artist", null);
            model.addAttribute("titleName", "Not Found");
            model.addAttribute("hasPage", false);
        }
        model.addAttribute("modifyConcert", false);
        return "artist";
    }

    /**
     * Redirects the user to the admin page where artists can be added, modified
     * or deleted. Also searches can be made within this page, so if there is a
     * search made (optional), the artists that match with it are displayed, for
     * that, the artistService is used
     *
     * @param model the model of the dynamic HTML
     * @param search (optional) artist search made
     * @return HTML to be loaded
     */
    @GetMapping("/admin/artist")
    public String artistsAdminPage(Model model, @RequestParam(required = false) String search) {

        boolean userLogged = userService.isLogged();
        model.addAttribute("isLogged", userLogged);

        if (search != null) {
            model.addAttribute("artistList", artistService.getSearchBy(search));
        } else {
            model.addAttribute("artistList", artistService.getEveryArtist()); //Show every artist
        }

        /*Artists are displayed in modify mode*/
        model.addAttribute("modifyArtist", true);

        return "admin-artists";
    }

    /**
     * * Method that controlls the registration of a new artist/modification of
     * an existing one. For that, the ArtistService is used, being this Service
     * the one encharged of the DDBB handling. Afterwards, the user is
     * redirected to the artist admin page
     *
     * @param model the actual dynamic HTML
     * @param artist artist collected from the form
     * @param mainPhoto (optional) artist photo in MultipartFile format
     * @param bestPhoto (optional) best album photo in MultipartFile format
     * @param latestPhoto (optional) best album photo in MultipartFile format
     * @param id (optional) artist's id
     * @param redirectAttributes attributes for the redirection solicitude
     * @return HTML to be loaded
     * @throws IOException
     */
    @PostMapping("/admin/artist/register")
    public String registerArtist(Model model, @ModelAttribute Artist artist, @RequestParam(required = false) MultipartFile mainPhoto,
            @RequestParam(required = false) MultipartFile bestPhoto, @RequestParam(required = false) MultipartFile latestPhoto,
            @RequestParam(required = false) Long id, RedirectAttributes redirectAttributes) throws IOException {

        if (id == null) { //Add new artist
            if (artistService.checkIfExistsByName(artist.getName())) {
                artist.setName(null);
                redirectAttributes.addFlashAttribute("error", "Artist name already exists");
                redirectAttributes.addFlashAttribute("artist", artist);
                return "redirect:/admin/artist/workbench";
            }
            artistService.registerNewArtist(artist, mainPhoto, bestPhoto, latestPhoto);
        } else {
            if (!artistService.modifyArtistWithId(artist, id, mainPhoto, bestPhoto, latestPhoto)) {
                return "redirect:/error";
            }
        }

        return "redirect:/admin/artist";
    }

    /**
     * Loads an empty artist to be added.
     *
     * @param model the actual dynamic HTML
     * @return the HTML to load
     */
    @GetMapping("/admin/artist/workbench")
    public String prepareArtistWorkbench(Model model) {

        boolean userLogged = userService.isLogged();
        model.addAttribute("isLogged", userLogged);

        return "artist-workbench";
    }

    /**
     * Method that prepares the workbench in order to modify an existing artist
     *
     * @param model actual dynamic HTML model
     * @param id id of the artist to be modified
     * @return the HTML to load
     */
    @PostMapping("/admin/artist/{id}/modify")
    public String modifyExistingArtist(Model model, @PathVariable long id) {

        boolean userLogged = userService.isLogged();
        model.addAttribute("isLogged", userLogged);

        Artist artist = artistService.getArtist(id);

        if (artist != null) {
            model.addAttribute("artist", artist);
            model.addAttribute("modificate", true);
            return "artist-workbench";
        } else {
            return "error";
        }
    }

    /**
     * Method that handles the situation of trying to delete an artist. Cases
     * when the ID does not correspond to any artist are also handled,
     * redirecting the user to an error page. As well, if the deletion has been
     * achieved, the user is redirected to the artists administrator page.
     *
     * @param model model of the actual dynamic HTML
     * @param id id of the future deleted artist
     * @return HTML to be loaded
     */
    @PostMapping("/admin/artist/{id}/delete")
    public String deleteExistingArtist(Model model, @PathVariable long id) {

        /*Tries to delete the artist with the specified ID and checks if the deletion has been completed*/
        boolean success = artistService.deleteArtistWithId(id);

        if (success) {
            /*If the deletion is successfull (the artist existed and now does not) */
            return "redirect:/admin/artist";
        } else {
            /*In any other case*/
            return "error";
        }
    }

}
