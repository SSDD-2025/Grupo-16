package es.ticketmaster.entrega1.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.service.ArtistService;



@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    /*ARTIST SEARCH CONTROLLER METHODS*/

    /**
     * REST Controller that returns all the artists in the DDBB
     * 
     * FUTURE REFACTOR: PAGINATION
     * 
     * @return A Collection of all the artist's DTOs.
     */
    @GetMapping({"", "/"})
    public Collection<ArtistDTO> getAllArtists(){

        return artistService.getEveryArtist();

    }

    /**
     * REST Controller that returns the DTO of an artist by its ID
     * 
     * @param id ID of the artist to search.
     * @return DTO of the artist to search.
     */
    @GetMapping("/{id}")
    public ArtistDTO getArtist(@PathVariable long id){

        return artistService.getArtist(id);

    }

    /*ARTIST CREATION CONTROLLER METHODS
     * 
     * FUTURE REFACTOR: Only admins can create artists. Also, images should be handled.
    */

    /**
     * REST Controller that handles the creation of an artist
     * 
     * @param artistDTO DTO sent by the request with the attributes.
     * @return DTO of the artist after it is introduced in the DDBB.
     * @throws Exception
     */
    @PostMapping ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artistDTO) throws Exception{
        
        artistDTO = artistService.registerNewArtist(artistDTO, null, null, null);

        URI location = fromCurrentRequest().path("/id").buildAndExpand(artistDTO.id()).toUri();

        return ResponseEntity.created(location).body(artistDTO);
    }

    /*ARTIST MODIFICATION CONTROLLER METHODS
     * 
     * FUTURE REFACTOR: Only admins can modify artists. Also, images should be handled.
    */
    
    /**
     * REST Controller that handles the modification of an artist.
     * 
     * @param id ID of the artist to modify.
     * @param artistDTO DTO sent by the request with the modifications.
     * @return DTO of the artist after it is modified in the DDBB.
     * @throws IOException
     */
    @PutMapping("/{id}")
    public ArtistDTO modifyArtist(@PathVariable long id, @RequestBody ArtistDTO artistDTO) throws IOException {

        return artistService.modifyArtistWithId(artistDTO, id, null, null, null);

    }

    /*ARTIST DELETION CONTROLLER METHODS
     * 
     * FUTURE REFACTOR: Only admins can delete artists.
    */

    /**
     * REST Controller that handles the deletion of an artist.
     * @param id ID of the artist to delete.
     * @return DTO of the artist as it previously was in the DDBB.
     */
    @DeleteMapping("/{id}")
    public ArtistDTO deleteArtist(@PathVariable long id){

        return artistService.deleteArtistWithId(id);

    }
}
