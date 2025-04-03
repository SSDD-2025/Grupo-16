package es.ticketmaster.entrega1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ticketmaster.entrega1.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public Ticket findTicketById(long id);

    /**
     * Searches for the tickets by the user's id
     * @param id user's id
     * @param pageable characteristics of the page that it is being returned
     * @return the page with the user's tickets
     */
    public Page<Ticket> findTicketByTicketUserId(long id, Pageable pageable);
}
