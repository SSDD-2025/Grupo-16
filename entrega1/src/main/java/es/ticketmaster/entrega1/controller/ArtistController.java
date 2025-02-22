package es.ticketmaster.entrega1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertService concertService;

    /**
     * 
     * @param model its the model of the dinamic html document
     * @param id its the artist id which is passed on the URL like a pathVariable
     * @param artistName its the artist name passed as an attribute on the path 
     * @return artist.html with its attributes
     * The artistName parameter it is only used if the artist searched does not have a main page but has concerts available
     */
    @GetMapping("/artist/{id}")
    public String getArtistPage(Model model, @PathVariable long id, @RequestParam String artistName) {
        Optional<Artist> artist = artistService.getArtist(id);
        if (artist.isPresent()){
            model.addAttribute("artist", artist.get());
            model.addAttribute("titleName", artist.get().getName());
            model.addAttribute("concertList", concertService.getArtistConcerts(artist.get().getName()));
        } else {
            model.addAttribute("artist", null);
            model.addAttribute("titleName", "Not Found");
            model.addAttribute("concertList", concertService.getArtistConcerts(artistName));
        }
        return "artist";
    }
    
}
