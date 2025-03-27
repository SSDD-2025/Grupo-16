package es.ticketmaster.entrega1.dto.ticket;

import org.mapstruct.Mapper;

import es.ticketmaster.entrega1.model.Ticket;
import java.util.List;
import java.util.Collection;

@Mapper(componentModel= "spring")
public interface TicketMapper {
    TicketDTO toDTO(Ticket ticket);
    
    List<TicketDTO> toDTOs(Collection<Ticket> tickets);
}