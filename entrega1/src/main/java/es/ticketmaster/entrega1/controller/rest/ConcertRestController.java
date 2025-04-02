package es.ticketmaster.entrega1.controller.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ticketmaster.entrega1.dto.concert.BasicConcertDTO;
import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.service.ConcertService;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/concerts")
public class ConcertRestController {
    @Autowired
    private ConcertService concertService;
    
    /**
     * NOTE: add pageable
     * @return the list of all the concerts existing in the database (just main information)
     */
    @GetMapping("/")
    public Collection<BasicConcertDTO> getConcerts() {
        return concertService.getAllConcerts();
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
    
}
