package es.ticketmaster.entrega1.dto.ticket;

import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.dto.user.UserDTO;

public record TicketDTO(Long id, String zone, float price, UserDTO ticketUser, ConcertDTO concert) {}