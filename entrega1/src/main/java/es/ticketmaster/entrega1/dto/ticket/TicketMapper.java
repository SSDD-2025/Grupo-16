package es.ticketmaster.entrega1.dto.ticket;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.ticketmaster.entrega1.model.Ticket;

@Mapper(componentModel= "spring")
public interface TicketMapper {
    @Mapping(source= "user", target = "ticketUser")
    TicketDTO toDTO(Ticket ticket);
    
    List<TicketDTO> toDTOs(Collection<Ticket> tickets);
}