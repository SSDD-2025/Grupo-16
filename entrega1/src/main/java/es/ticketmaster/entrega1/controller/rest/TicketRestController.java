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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    
   @Operation(summary = "Confirm purchase of tickets", description = "Confirms the purchase of tickets for a specific concert. Requires concert ID to link the tickets with the concert.")
   @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket successfully purchased."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Purchase failed."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated.")
    })
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

    @Operation(summary = "Delete a ticket", description = "Deletes a ticket identified by the ticket ID. The ticket must belong to the active user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket successfully deleted."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Unable to delete ticket."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated.")
    })
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