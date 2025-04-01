package es.ticketmaster.entrega1.service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.ticketmaster.entrega1.dto.concert.ConcertMapper;
import es.ticketmaster.entrega1.dto.ticket.TicketDTO;
import es.ticketmaster.entrega1.dto.ticket.TicketMapper;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.model.Ticket;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.ConcertRepository;
import es.ticketmaster.entrega1.repository.TicketRepository;
import es.ticketmaster.entrega1.repository.UserRepository;
import es.ticketmaster.entrega1.service.exceptions.TicketNotFoundException;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertRepository;
    
    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertMapper mapper;

    @Autowired
    private TicketMapper ticketMapper;

    /**
     * Obtain the ticket from the database.
     *
     * @param id is the ticket identification number.
     * @return the ticket in a DTO format or, a TicketNotFoundException if the ticket not exist in the database.
     */
    public TicketDTO getTicket(long id) {
        return this.ticketRepository.findById(id).map(this.ticketMapper :: toDTO).orElseThrow(() -> 
                                                    new TicketNotFoundException(id));
    }

    /**
     * This is a private method that creates a new ticket and save it in the
     * database.
     *
     * @param zone of the ticket that has been purchased.
     * @param price of the ticket.
     * @param concert that corresponds to a certain ticket
     * @return the created ticket.
     */
    private Ticket createTicket(String zone, float price, Concert concert) {
        Ticket newTicket = new Ticket(zone, price, null, concert);
        this.ticketRepository.save(newTicket);
        return newTicket;
    }

    /**
     * This method will create a ticket (by calling the createTicket method).
     * Once created will do the following things: Adding that new ticket to the
     * user ticket list. Once the ticket has being associated to a user, it will
     * be saved in the ticket`s repository. Finally, the same thing will be
     * happening with the user, once all the purchased tickets has beeing
     * included to his list, the user will be saved in its repository.
     *
     * @param type is the type of the ticket.
     * @param number is the ammount of tickets de user has purchased.
     * @param concertId is the identification number for a concert.
     * @param principal is the currently authenticated user, used to retrieve the active user. 
     */
    public void associateUserWithTicket(String type, int number, long concertId, Principal principal) {
        UserEntity userEntity = this.userRepository.findByUsername(principal.getName())
                                                    .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        List<Ticket> userTickets = userEntity.getTicketList(); /* Gets the actual ticket list for the respective user. */

        Concert concert = this.concertRepository.findConcertById(concertId); /* Gets the respective concert. */
        for (int i = 0; i < number; i++) {
            Ticket newTicket = this.createTicket(type, concert.getPrice(), concert);
            /* Creation of ticket. */
            userTickets.add(newTicket);
            /* Adding the ticket to the user ticket list. */
            newTicket.setUser(userEntity);
            /* Associating the ticket to the user. */
            this.ticketRepository.save(newTicket);
            /* Once it has been asssociated, the ticket is saved in its repository. */
        }
        /* Now that the tickets have been added, the user's ticket list is updated. */
        userEntity.setTicketList(userTickets);
        this.userRepository.save(userEntity); /* Finally, we save in the database the updated ticket list for the user. */
    }

    /**
     * Handles the ticket deletion from a user: the Ticket object is destroyed
     * and the number of tickets available in the concert is restored back so
     * that another user can buy the ticket
     *
     * @param id ticket id
     * @return true if the transaction occured correctly, false in other case
     */
    public boolean deleteTicketWithId(long id) {
        Optional<Ticket> ticket = this.ticketRepository.findById(id);
        /* We take the ticket */
        if (!ticket.isPresent()) {
            /* If an ticket with such ID does not exist */
            return false;
        } 
        else {
            if (!this.concertService.returnTicket(mapper.toDTO(ticket.get().getConcert()), ticket.get().getZone())) {
                /* We try to restore the ticket */
                return false;
                /*The change was not possible*/
            }
            /* In the case the change is made, we make the deletion */
            this.ticketRepository.deleteById(id);
            /* We delete the ticket with that ID */
            return !this.ticketRepository.existsById(id);
            /* We return true if the ticket has been correctly deleted */
        }
    }
}