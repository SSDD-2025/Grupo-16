package es.ticketmaster.entrega1.controller.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import es.ticketmaster.entrega1.dto.concert.BasicConcertDTO;
import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.exceptions.ImageException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    @Autowired
    private ConcertService concertService;
    
    /**
     * @return the list of all the concerts existing in the database (just main information)
     */
    @GetMapping("/")
    public Page<BasicConcertDTO> getConcerts(Pageable pageable) {
        return concertService.getAllConcertPage(pageable);
    }

    /**
     * Get the whole concert from an id
     * @param id id of the concert to show
     * @return said concertDTO
     */
    @GetMapping("/{id}")
    public ConcertDTO getConcert(@PathVariable long id) {
        return concertService.findConcertById(id);
    }

    /**
     * Gets a page of the concerts that are being held at the same country as the user
     * @param request http request information
     * @param pageable the object Pageable which contains the information of the characteristics of the page
     * @return the page with the concerts
     */
    @GetMapping("/near-user")
    public Page<BasicConcertDTO> getConcertsNearUser(HttpServletRequest request, Pageable pageable, @RequestParam(required = false) String country) {
        if (country == null) { //for API REST petitions on Postman
            return concertService.getUserConcertPage(request.getUserPrincipal(), pageable);
        } else { //for Web Api Rest Petitions on JavaScript script
            return concertService.getUserConcertPage(pageable, country);
        }
    }

    @GetMapping("/artist/{id}")
    public Page<BasicConcertDTO> getArtistConcerts(@PathVariable long id, Pageable pageable) {
        return concertService.getArtistConcerts(id,pageable);
    }
    
    
    /**
     * Create a concert
     * @param concert the new concert to be created
     * @return the information of the concert that has been created
     */
    @PostMapping("/")
    public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertDTO concert) {
        ConcertDTO created = concertService.saveConcert(concert);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();
		return ResponseEntity.created(location).body(created);
    }

    /**
     * Modify a concert
     * @param id id of the concert that is being changed
     * @param concert data of the concert modified
     * @return the concert modified
     */
    @PutMapping("/{id}")
    public ConcertDTO modifyConcert(@PathVariable long id, @RequestBody ConcertDTO concert) {
        return concertService.modifyConcert(concert, id);
    }

    /**
     * Delete an existing concert
     * @param id id of the concert to be erased from the database
     * @return the concert that has been deleted
     */
    @DeleteMapping("/{id}")
    public ConcertDTO deleteConcert(@PathVariable long id){
        return concertService.deleteConcert(id);
    }
    
    /* The next controller methods will be dedicated to the managment of the poster photo on the API REST */

    /**
     * REST Controller that handles the display of the Concert's poster photo
     * @param id the id of the concert whose poster wants to be showed
     * @return ResponseEntity with the image if everything went correctly, the error JSON in other case
     * @throws SQLException
     * @throws IOException
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getPosterPhoto(@PathVariable long id) throws SQLException, IOException{
        Blob posterBlob = concertService.getConcertImage(id);
        if (posterBlob != null){
            Resource poster = new InputStreamResource(posterBlob.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(poster);
        } else {
            throw new ImageException("Concert does not have an associated image");
        }
    }

    /**
     * REST Controller that handles the creation of an image
     * @param id id of the concert whose photo is being added
     * @param posterPhoto the photo of the concert's poster
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PostMapping("/{id}/image")
    public ResponseEntity<Object> postPosterPhoto(@PathVariable long id, @RequestParam MultipartFile posterPhoto) throws IOException{
        URI location = fromCurrentRequest().build().toUri();
        concertService.setPosterPhoto(id,posterPhoto.getInputStream(),posterPhoto.getSize(), posterPhoto != null);
        return ResponseEntity.created(location).build();
    }

    /**
     * REST Controller that handles the replacement of an image
     * @param id id of the concert whose photo is being changed
     * @param poster the new photo of the concert's poster
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @PutMapping("/{id}/image")
    public ResponseEntity<Object> putPosterPhoto(@PathVariable long id, @RequestParam MultipartFile poster) throws IOException {
        concertService.setPosterPhoto(id, poster.getInputStream(), poster.getSize(), poster != null);
        return ResponseEntity.noContent().build();
    }

    /**
     * REST Controller that handles the elimination of an image
     * @param id id of the concert whose photo is being erased
     * @return ResponseEntity with the state of the query
     * @throws IOException
     */
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deletePosterPhoto(@PathVariable long id) throws IOException{
        concertService.deleteConcertPoster(id);
        return ResponseEntity.noContent().build();
    }
}
