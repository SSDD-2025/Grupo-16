package es.ticketmaster.entrega1.service;

import java.util.List;

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
    public TicketRepository ticketRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public ConcertRepository concertRepository;
    @Autowired
    public ActiveUser activeUser;

    /**
     * Obtain the ticket form the database.
     * @param id is the ticket identification number.
     * @return the ticket.
     */
    public Ticket getTicket(long id) {
        return this.ticketRepository.findTicketById(id);
    }

    /**
     * This is a private method that creates a new ticket. It is used when the user has purchased a ticket, 
     * in order to associate a ticket to a user.
     * @param zone of the ticket that has been purchased.
     * @param price of the ticket.
     * @param concert that corresponds to a certain ticket
     * @return the created ticket.
     */
    private Ticket createTicket(String zone, float price, Concert concert) {
        return new Ticket(zone, price, null, concert);
    }

    /**
     * This method will obtain the tickets the user has purchased. Once obtained will do the following things:
     * Adding that new ticket to the user ticket list.
     * Once the ticket has being associated to a user, it will be saved in the ticket`s repository.
     * Finally, the same thing will be happening with the user,
     * once all the purchased tickets has beeing included to his list, the user will be saved in its repository. 
     * @param type is the type of the ticket.
     * @param number is the ammount of tickets de user has purchased.
     */
    public void associateUserWithTicket(String type, int number, long concertId) {
        List<Ticket> userTickets = userRepository.findById(this.activeUser.getId()).getTicketsList();
        Concert concert = this.concertRepository.findConcertById(concertId);
        for (int i = 0; i < number; i++) {
            /* The user is null, because is an available ticket. */
            this.createTicket(type, concert.getPrice(), concert);
            Ticket newTicket = this.ticketRepository.findTicketByZoneAndTicketUser(type, null);
            userTickets.add(newTicket); /* Adding the ticket to the user ticket list. */
            newTicket.setUser(this.userRepository.findById(this.activeUser.getId())); /* Associating the ticket to the user. */
            this.ticketRepository.save(newTicket); /* Once it has been asssociated, the ticket is saved in its repository. */
        }
        this.userRepository.findById(this.activeUser.getId()).setTicketList(userTickets); /* Updating the ticket list of the user. */
        this.userRepository.save(this.userRepository.findById(this.activeUser.getId())); /* Saving the user in its repository. */
    }
}