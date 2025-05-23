package es.ticketmaster.entrega1.controller.web;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.exceptions.ArtistAlreadyExistsException;
import es.ticketmaster.entrega1.service.exceptions.ArtistNotFoundException;
import es.ticketmaster.entrega1.service.exceptions.ImageException;
import es.ticketmaster.entrega1.service.exceptions.NotAllowedException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertService concertService;

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

        Artist artist = artistService.getArtistEntity(id);
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
     * @param pageable pagination object
     * @return HTML to be loaded
     */
    @GetMapping("/admin/artist")
    public String artistsAdminPage(Model model, @RequestParam(required = false) String search, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        if (search != null) {
            model.addAttribute("artistList", artistService.getSearchBy(search, pageable));
        } else {
            model.addAttribute("artistList", artistService.getEveryArtist(pageable)); //Show every artist
        }

        /*Artists are displayed in modify mode*/
        model.addAttribute("modifyArtist", true);

        return "admin-artists";
    }

    /**
     * Method that controlls the registration of a new artist. For that, the
     * ArtistService is used, being this Service the one encharged of the DDBB
     * handling. Afterwards, the user is redirected to the artist admin page.
     * 
     * This web method handles the possible errors thrown by the service and
     * displays them inside an HTML so that is more visual for the user.
     * The possible exceptions thrown by the service are:
     * + ArtistAlreadyExistsException: notified using error Mustache variable
     * + ImageException: notified using imageException Mustache variable
     * + NotAllowedException: notified using error Mustache variable
     *
     * @param model the actual dynamic HTML
     * @param artist artist collected from the form
     * @param mainPhoto (optional) artist photo in MultipartFile format
     * @param redirectAttributes attributes for the redirection solicitude
     * @return HTML to be loaded
     * @throws Exception 
     */
    @PostMapping("/admin/artist/register")
    public String registerArtist(Model model, @ModelAttribute ArtistDTO artist, @RequestParam(required = false) MultipartFile mainPhoto, RedirectAttributes redirectAttributes) throws Exception {

        try {
            ArtistDTO registeredArtist = this.artistService.registerNewArtist(artist);
            if (!(mainPhoto == null || mainPhoto.isEmpty())) {
                this.artistService.createPhotoImage(registeredArtist.id(), mainPhoto);
            }

        } catch (ArtistAlreadyExistsException e) {
            /*Notification of the ArtistAlreadyExistsException via HTML */
            redirectAttributes.addFlashAttribute("error", "Artist name already exists");
            ArtistDTO artistAttributes = new ArtistDTO(artist.id(), null, artist.popularityIndex(), artist.hasPage(), artist.mainInfo(), artist.extendedInfo(), artist.bestAlbumSpotifyLink(), artist.latestAlbumSpotifyLink(), artist.videoLink(), artist.photoLink());
            redirectAttributes.addFlashAttribute("artist", artistAttributes);
            return "redirect:/admin/artist/workbench";
        } catch (ImageException e){
            /*Notification of the ImageException via HTML */
            redirectAttributes.addFlashAttribute("imageError", "ImageError: " + e.getMessage());
            ArtistDTO artistAttributes = new ArtistDTO(artist.id(), artist.name(), artist.popularityIndex(), artist.hasPage(), artist.mainInfo(), artist.extendedInfo(), artist.bestAlbumSpotifyLink(), artist.latestAlbumSpotifyLink(), artist.videoLink(), null);
            redirectAttributes.addFlashAttribute("artist", artistAttributes);
            redirectAttributes.addFlashAttribute("modificate", true);
            return "redirect:/admin/artist/workbench";
        } catch (NotAllowedException e){
            /*Notification of the NotAllowedException via HTML */
            redirectAttributes.addFlashAttribute("error", "NotAllowedError: " + e.getMessage());
            redirectAttributes.addFlashAttribute("artist", artist);
            return "redirect:/admin/artist/workbench";
        }

        return "redirect:/admin/artist";
    }

    /**
     * Method that controlls the modification of an existing artist. For that,
     * the ArtistService is used, being this Service the one encharged of the
     * DDBB handling. Afterwards, the user is redirected to the artist admin
     * page
     * 
     * The possible exceptions thrown by the service are:
     * + ArtistNotFoundException: notified using error Mustache variable
     * + ImageException: notified using imageException Mustache variable
     *
     * @param model the actual dynamic HTML
     * @param id id of the artist which modification is desired.
     * @param artist artist collected from the form
     * @param mainPhoto artist photo in MultipartFile format
     * @return HTML to be loaded
     * @throws IOException
     */
    @PostMapping("/admin/artist/{id}/modify")
    public String modifyArtist(Model model, @PathVariable long id, @ModelAttribute ArtistDTO artist, @RequestParam(required = false) MultipartFile mainPhoto, RedirectAttributes redirectAttributes) throws IOException {
        try{
            this.artistService.modifyArtistWithId(artist, id);
            if (!(mainPhoto == null || mainPhoto.isEmpty())) {
                this.artistService.replacePhotoImage(id, mainPhoto);
            }
            return "redirect:/admin/artist";
        } catch (ArtistNotFoundException e) {
            /*Notification of the ArtistNotFoundException via HTML */
            model.addAttribute("errorCode", "404 Not Found");
            model.addAttribute("errorMessage", "Artist Not Found");
            return "error";
        } catch (ImageException e){
            /*Notification of the ImageException via HTML */
            redirectAttributes.addFlashAttribute("imageError", "ImageError:" + e.getMessage());
            ArtistDTO artistAttributes = new ArtistDTO(artist.id(), artist.name(), artist.popularityIndex(), artist.hasPage(), artist.mainInfo(), artist.extendedInfo(), artist.bestAlbumSpotifyLink(), artist.latestAlbumSpotifyLink(), artist.videoLink(), null);
            redirectAttributes.addFlashAttribute("artist", artistAttributes);
            redirectAttributes.addFlashAttribute("modificate", true);
            return "redirect:/admin/artist/workbench";
        } 
    }

    /**
     * Loads an empty artist to be added.
     *
     * @param model the actual dynamic HTML
     * @return the HTML to load
     */
    @GetMapping("/admin/artist/workbench")
    public String prepareArtistWorkbench(Model model) {
        return "artist-workbench";
    }

    /**
     * Method that prepares the workbench in order to modify an existing artist
     *
     * @param model actual dynamic HTML model
     * @param id id of the artist to be modified
     * @return the HTML to load
     */
    @GetMapping("/admin/artist/{id}")
    public String modifyExistingArtist(Model model, @PathVariable long id) {

        Artist artist = artistService.getArtistEntity(id);

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
     * The possible exceptions thrown by the service are:
     * + ArtistNotFoundException: notified using error Mustache variable
     *
     * @param model model of the actual dynamic HTML
     * @param id id of the future deleted artist
     * @return HTML to be loaded
     */
    @PostMapping("/admin/artist/{id}/delete")
    public String deleteExistingArtist(Model model, @PathVariable long id) {

        /*Tries to delete the artist with the specified ID and checks if the deletion has been completed*/
        try {
            /*If the deletion is successfull (the artist existed and now does not) */
            artistService.deleteArtistWithId(id);
            return "redirect:/admin/artist";
        } catch (ArtistNotFoundException e) {
            /*Notification of the ArtistNotFoundException via HTML */
            model.addAttribute("errorCode", "404 Not Found");
            model.addAttribute("errorMessage", "Artist Not Found");
            return "error";
        }
    }

    /**
     * This method stablishes the needed variables as far as the artist
     * controller is concerned. The information needed is: if the user is logged
     * or if the user is an admin. This variables are used to show diferent UI
     * corresponding to their role. The way this method works is adding the
     * correspondent attributes to the model when any of the previous controller
     * methods are called so that the information is pre-loaded.
     *
     * @param model is the model of dynamic HTTP
     * @param request is the HTTP request
     */
    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) { //If there is a principal user (means it is logged)
            model.addAttribute("isLogged", true);
            model.addAttribute("isAdmin", request.isUserInRole("ADMIN"));
        } else {
            model.addAttribute("isLogged", false);
        }
    }
}
