package es.ticketmaster.entrega1.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
    @GetMapping
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

    /**
     * REST Controller that handles the display of the Artist's main photo
     * 
     * @param id the ID of the artist which main photo is searched
     * @return ResponseEntity with the image if everything went correctly, the error JSON in other case
     * @throws IOException
     */
    @GetMapping("/{id}/main-photo")
    public ResponseEntity<Object> getMainPhoto(@PathVariable long id) throws SQLException, IOException {

        Resource mainPhoto = artistService.getMainPhoto(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/jpg", "image/png").body(mainPhoto);
    }

    /**
     * REST Controller that handles the display of the Artist's best photo
     * 
     * @param id the ID of the artist which best photo is searched
     * @return ResponseEntity with the image if everything went correctly, the error JSON in other case
     * @throws IOException
     */
    @GetMapping("/{id}/best-photo")
    public ResponseEntity<Object> getBestPhoto(@PathVariable long id) throws SQLException, IOException {

        Resource bestPhoto = artistService.getBestPhoto(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/jpg", "image/png").body(bestPhoto);
    }

    /**
     * REST Controller that handles the display of the Artist's latest photo
     * 
     * @param id the ID of the artist which latest photo is searched
     * @return ResponseEntity with the image if everything went correctly, the error JSON in other case
     * @throws IOException
     */
    @GetMapping("/{id}/latest-photo")
    public ResponseEntity<Object> getLatestPhoto(@PathVariable long id) throws SQLException, IOException {

        Resource latestPhoto = artistService.getLatestPhoto(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/jpg", "image/png").body(latestPhoto);
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
    @PostMapping 
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artistDTO) throws Exception{
        
        artistDTO = artistService.registerNewArtist(artistDTO);

        URI location = fromCurrentRequest().path("/id").buildAndExpand(artistDTO.id()).toUri();

        return ResponseEntity.created(location).body(artistDTO);
    }

    /**
     * REST Controller that handles the creation of the Artist's main-photo
     * 
     * @param id the ID of the artist which main photo is going to be created
     * @param imageFile the aledgly main-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PostMapping("/{id}/main-photo")
    public ResponseEntity<Object> createMainPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        URI location = fromCurrentRequest().build().toUri();

        artistService.createMainPhotoImage(id, imageFile);

        return ResponseEntity.created(location).build();
    }

    /**
     * REST Controller that handles the creation of the Artist's best-photo
     * 
     * @param id the ID of the artist which best photo is going to be created
     * @param imageFile the aledgly best-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PostMapping("/{id}/best-photo")
    public ResponseEntity<Object> createBestPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        URI location = fromCurrentRequest().build().toUri();

        artistService.createBestPhotoImage(id, imageFile);

        return ResponseEntity.created(location).build();
    }

    /**
     * REST Controller that handles the creation of the Artist's latest-photo
     * 
     * @param id the ID of the artist which latest photo is going to be created
     * @param imageFile the aledgly latest-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PostMapping("/{id}/latest-photo")
    public ResponseEntity<Object> createLatestPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        URI location = fromCurrentRequest().build().toUri();

        artistService.createLatestPhotoImage(id, imageFile);

        return ResponseEntity.created(location).build();
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

        return artistService.modifyArtistWithId(artistDTO, id);

    }

    /**
     * REST Controller that handles the modification of the Artist's main-photo
     * 
     * @param id the ID of the artist which main photo is going to be modified
     * @param imageFile the new desired main-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PutMapping("/{id}/main-photo")
    public ResponseEntity<Object> replaceMainPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        artistService.replaceMainPhotoImage(id, imageFile);

        return ResponseEntity.noContent().build();
    }

    /**
     * REST Controller that handles the modification of the Artist's best-photo
     * 
     * @param id the ID of the artist which best photo is going to be modified
     * @param imageFile the new desired best-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PutMapping("/{id}/best-photo")
    public ResponseEntity<Object> replaceBestPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        artistService.replaceBestPhotoImage(id, imageFile);

        return ResponseEntity.noContent().build();
    }

    /**
     * REST Controller that handles the modification of the Artist's latest-photo
     * 
     * @param id the ID of the artist which latest photo is going to be modified
     * @param imageFile the new desired latest-photo of the artist
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PutMapping("/{id}/latest-photo")
    public ResponseEntity<Object> replaceLatestPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        artistService.replaceLatestPhotoImage(id, imageFile);

        return ResponseEntity.noContent().build();
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

    /**
     * REST Controller that handles the deletion of the Artist's main photo
     * 
     * @param id the ID of the artist which main photo is going to be deleted
     * @return ResponseEntity with the JSON to show how the transaction went
     * @throws IOException
     */
    @DeleteMapping("/{id}/main-photo")
    public ResponseEntity<Object> deleteMainPhoto(@PathVariable long id) throws SQLException, IOException {

        artistService.deleteMainPhoto(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * REST Controller that handles the deletion of the Artist's best photo
     * 
     * @param id the ID of the artist which best photo is going to be deleted
     * @return ResponseEntity with the JSON to show how the transaction went
     * @throws IOException
     */
    @DeleteMapping("/{id}/best-photo")
    public ResponseEntity<Object> deleteBestPhoto(@PathVariable long id) throws SQLException, IOException {

        artistService.deleteBestPhoto(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * REST Controller that handles the deletion of the Artist's latest photo
     * 
     * @param id the ID of the artist which latest photo is going to be deleted
     * @return ResponseEntity with the JSON to show how the transaction went
     * @throws IOException
     */
    @DeleteMapping("/{id}/latest-photo")
    public ResponseEntity<Object> deleteLatestPhoto(@PathVariable long id) throws SQLException, IOException {

        artistService.deleteLatestPhoto(id);

        return ResponseEntity.noContent().build();
    }
}
