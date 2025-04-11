package es.ticketmaster.entrega1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.ticketmaster.entrega1.dto.ticket.TicketDTO;
import es.ticketmaster.entrega1.dto.ticket.TicketMapper;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.model.Ticket;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.ConcertRepository;
import es.ticketmaster.entrega1.repository.TicketRepository;
import es.ticketmaster.entrega1.repository.UserRepository;
import es.ticketmaster.entrega1.service.exceptions.NotAllowedException;
import es.ticketmaster.entrega1.service.exceptions.TicketNotFoundException;
import es.ticketmaster.entrega1.service.exceptions.UserNotFoundException;

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
    private UserService userService;

    @Autowired
    private TicketMapper ticketMapper;

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
     * Gets the page of the user's tickets
     * 
     * @param pageable characteristics of the page that is being returned
     * @return said page with the tickets as DTOs
     */
    public Page<TicketDTO> getTicketPage(Pageable pageable){
        Optional<UserEntity> user = this.userService.getUser();
        if(user.isPresent()){
            return this.ticketRepository.findTicketByTicketUserId(user.get().getId(), pageable).map(ticketMapper::toDTO);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
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
     */
    public void associateUserWithTicket(String type, int number, long concertId) {
        UserEntity userEntity = this.userService.getUser().orElseThrow(() -> new UserNotFoundException());
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
            /* Once it has been associated, the ticket is saved in its repository. */
        }
        /* Now that the tickets have been added, the user's ticket list is updated. */
        userEntity.setTicketList(userTickets);
        this.userRepository.save(userEntity); /* Finally, we save in the database the updated ticket list for the user. */
    }

    /**
     * Service method that checks if a ticket with id belongs or not to the user who is making use of the app.
     * For that, the user is searched with help of the UserService method: getUser.
     * 
     * This method is important in order to verify that the transactions made with a ticket are only done if the
     * user is the owner of the ticket.
     * 
     * @param id the ID of the ticket that we want to check
     * @param principal
     * @return
     */
    public boolean checkIfTicketBelongsToUser(long id){

        Optional<UserEntity> user = userService.getUser();
        if(user.isPresent()){
            Optional<Ticket> ticket = ticketRepository.findById(id);
            if(ticket.isPresent()){
                return ticket.get().getTicketUser().getId() == user.get().getId();
            } else {
                throw new TicketNotFoundException(id);
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Handles the ticket deletion from a user: the Ticket Entity object is destroyed (deleted)
     * and the number of tickets available in the concert is restored back so that another user 
     * can buy the ticket.
     *  
     * It is COMPULSORY that the user who asks for the ticket deletion owns the ticket to delete.
     * For that, this method makes use of the deleteTicketWithIdReturnDTO method.
     *
     * @param id ticket id.
     * @return true if the transaction occured correctly, false in other case.
     */
    public TicketDTO deleteTicketWithId(long id) {
        boolean belongs = this.checkIfTicketBelongsToUser(id);
        if(belongs){ /*The checkIfTicketBelongsToUser method also detects if the ticket does not exist*/
            Ticket ticket = this.ticketRepository.findById(id).get();

            /* In the ticket is correctly returned, we make the deletion */
            this.concertService.returnTicket(ticket.getConcert().getId(), ticket.getZone());
            /* We delete the ticket entity with that ID */
            this.ticketRepository.deleteById(id);
            /* We return the DTO of the just deleted ticket*/
            return ticketMapper.toDTO(ticket);
        } else {
            throw new NotAllowedException("Delete a ticket of another user.");
        }
    }
}