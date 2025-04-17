package es.ticketmaster.entrega1.dto.ticket;

import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.dto.user.ShowUserDTO;

public record TicketDTO(Long id, String zone, float price, ShowUserDTO ticketUser, ConcertDTO concert) {}