package es.ticketmaster.entrega1.controller.rest;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.ticketmaster.entrega1.dto.ticket.TicketDTO;
import es.ticketmaster.entrega1.service.TicketService;
import es.ticketmaster.entrega1.service.exceptions.GlobalExceptionHandler;
import es.ticketmaster.entrega1.service.exceptions.TicketNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TicketRestController {
    @Autowired
    private TicketService ticketService;

    /**
     * Get all the tickets from a user (the active user)
     * @param request the HttpRequest that includes the data of the active user
     * @return the ticket list of the active user
     */
    @GetMapping("/tickets/")
    public Page<TicketDTO> getUserTickets(HttpServletRequest request, Pageable pageable){
        return ticketService.getTicketPage(request.getUserPrincipal(),pageable);
    }
    
    /**
     * Confirms the purchase of tickets for a specific concert.
     * The URL contains the concert ID due to the ticket's relationship to it.
     * Since a ticket must be related to a concert (it can't be independent), to associate it with the user,
     * the concert ID is required.
     * 
     * @param concertId The unique identifier of the concert for which the tickets are being purchased.
     * @param ticketDTO The DTO representing the ticket.
     * @param number The number of tickets to be purchased.
     * @param principal The currently authenticated user, used to retrieve the active user and associate tickets with them.
     * 
     * @return ResponseEntity containing the TicketDTO object with the purchased ticket details if successful, or: 
     *          - a UNAUTHORIZED (401) if the principal is null (user is not authenticated). 
     *          - a CREATED (201) if the ticket is succesfully created.
     *          - a BAD_REQUEST (400) response if the purchase fails.
     */
    @PostMapping("/concert/{id}/ticket")
    public ResponseEntity<Object> confirmPurchaseREST(@PathVariable long concertId, @RequestBody TicketDTO ticketDTO, 
            @RequestParam int number, 
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            try {
                this.ticketService.associateUserWithTicket(ticketDTO.zone(), number, concertId, principal);
                URI location = fromCurrentRequest().path("/{id}").buildAndExpand(ticketDTO.id()).toUri();
                return ResponseEntity.created(location).body(ticketDTO);
            } 
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }

    /**
     * Deletes a ticket identified by the given ticket ID. Only if it belongs to the active user.
     * 
     * @see TicketService#getTicket(long)
     * @see GlobalExceptionHandler#TicketNotFoundException(TicketNotFoundException)
     * 
     * @param id is the unique identifier of the ticket to be deleted.
     * @param principal the principal class from where the username can be requested.
     * @return ResponseEntity containing the deleted TicketDTO object if successful, or:
     *                  - a UNAUTHORIZED (401) if the principal is null (user is not authenticated).
     *                  - a BAD_REQUEST (400) response if the ticket cannot be deleted.
     */
    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<TicketDTO> deleteTicketREST(@PathVariable long id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            TicketDTO ticketDTO = this.ticketService.getTicket(id);
            if (this.ticketService.deleteTicketWithId(id)) {
                return ResponseEntity.ok(ticketDTO);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
    }
}