package es.ticketmaster.entrega1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ticketmaster.entrega1.model.Ticket;
import es.ticketmaster.entrega1.model.UserEntity;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    public Ticket findTicketByID(long id);
    public Ticket findTicketByZoneAndTicketUser(String zone, UserEntity user);
}