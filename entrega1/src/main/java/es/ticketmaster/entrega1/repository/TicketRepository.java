package es.ticketmaster.entrega1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ticketmaster.entrega1.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public Ticket findTicketById(long id);
}
