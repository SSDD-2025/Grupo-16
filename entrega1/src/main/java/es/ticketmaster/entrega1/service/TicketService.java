package es.ticketmaster.entrega1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ticketmaster.entrega1.model.ActiveUser;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.model.Ticket;
import es.ticketmaster.entrega1.repository.ConcertRepository;
import es.ticketmaster.entrega1.repository.TicketRepository;
import es.ticketmaster.entrega1.repository.UserRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private ActiveUser activeUser;
    @Autowired
    private ConcertService concertService;

    /**
     * Obtain the ticket form the database.
     * @param id is the ticket identification number.
     * @return the ticket.
     */
    public Ticket getTicket(long id) {
        return this.ticketRepository.findTicketById(id);
    }

    /**
     * This is a private method that creates a new ticket and save it in the database.
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
     * This method will create a ticket (by calling the createTicket method). Once created will do the following things:
     * Adding that new ticket to the user ticket list.
     * Once the ticket has being associated to a user, it will be saved in the ticket`s repository.
     * Finally, the same thing will be happening with the user,
     * once all the purchased tickets has beeing included to his list, the user will be saved in its repository. 
     * @param type is the type of the ticket.
     * @param number is the ammount of tickets de user has purchased.
     * @param concertId is the identification number for a concert.
     */
    public void associateUserWithTicket(String type, int number, long concertId) {
        List<Ticket> userTickets = userRepository.findById(this.activeUser.getId()).getTicketsList();
        Concert concert = this.concertRepository.findConcertById(concertId);
        for (int i = 0; i < number; i++) {
            Ticket newTicket = this.createTicket(type, concert.getPrice(), concert); /* Creation of ticket. */
            userTickets.add(newTicket); /* Adding the ticket to the user ticket list. */
            newTicket.setUser(this.userRepository.findById(this.activeUser.getId())); /* Associating the ticket to the user. */
            this.ticketRepository.save(newTicket); /* Once it has been asssociated, the ticket is saved in its repository. */
        }
        this.userRepository.findById(this.activeUser.getId()).setTicketList(userTickets); /* Updating the ticket list of the user. */
        this.userRepository.save(this.userRepository.findById(this.activeUser.getId())); /* Saving the user in its repository. */
    }

    /**
     * Handles the ticket deletion from a user: the Ticket object is destroyed and the number
     * of tickets available in the concert is restored back so that another user can buy
     * the ticket
     * @param id ticket id
     * @return true if the transaction occured correctly, false in other case
     */
    public boolean deleteTicketWithId(long id){

        Optional<Ticket> ticket = ticketRepository.findById(id); /*We take the ticket*/

        if (!ticket.isPresent()) { //If an ticket with such ID does not exist
            return false;
        } else {
            if(!concertService.returnTicket(ticket.get().getConcert(), ticket.get().getZone())){ /*We try to restore the ticket*/
                return false; /*The change was not possible*/
            } /*In the case the change is made, we make the deletion*/
            ticketRepository.deleteById(id); //We delete the ticket with that ID
            return !ticketRepository.existsById(id); //We return true if the ticket has been correctly deleted
        }
    }
}