package es.ticketmaster.entrega1.dto.ticket;

import es.ticketmaster.entrega1.dto.user.UserDTO;

public record TicketDTO(String username, UserDTO user) {}