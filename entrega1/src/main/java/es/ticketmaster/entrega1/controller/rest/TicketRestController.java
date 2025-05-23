package es.ticketmaster.entrega1.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.ticketmaster.entrega1.dto.ticket.TicketDTO;
import es.ticketmaster.entrega1.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Tickets", description = "Endpoints for managing ticket resources")
@RequestMapping("/api")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Get the active user's ticket's list",
        description = "Shows the page which contains 10 tickets the active user owns")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket list succesfully showed."),
        @ApiResponse(responseCode = "400", description = "Bad Request: petition failed or the page was empty."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated.")
    })
    @GetMapping({"/tickets/", "/tickets"})
    public Page<TicketDTO> getUserTickets(Pageable pageable) {
        Page<TicketDTO> list = ticketService.getTicketPage(pageable);
        return list;
    }

    @Operation(summary = "Confirm purchase of tickets", description = "Confirms the purchase of tickets for a specific concert. Requires concert ID to link the tickets with the concert.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket successfully purchased."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Purchase failed."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated.")
    })
    @PostMapping("/concert/{concertId}/ticket")
    public List<TicketDTO> confirmPurchaseREST(@PathVariable long concertId, @RequestBody TicketDTO ticket, 
        @RequestParam int number) {

        return this.ticketService.associateUserWithTicket(ticket.zone(), number, concertId);

    }

    @Operation(
        summary = "Delete a ticket",
        description = "Deletes a specific ticket from the system using its ID. " +
                      "If the ticket does not exist or an error occurs during deletion, " +
                      "it will be handled by a global exception handler."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket successfully deleted."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Ticket could not be deleted."),
        @ApiResponse(responseCode = "404", description = "Ticket not found with the provided ID."),
        @ApiResponse(responseCode = "500", description = "Server error during ticket deletion.")
    })
    @DeleteMapping("/tickets/{id}")
    public TicketDTO deleteTicketREST(@PathVariable long id) {
        return this.ticketService.deleteTicketWithId(id);
    }
}