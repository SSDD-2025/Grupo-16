package es.ticketmaster.entrega1.dto.user;

import java.util.List;

import es.ticketmaster.entrega1.model.Ticket;

public record UserShowTicketsDTO(
    String username,
    List<Ticket> ticketList
) {}