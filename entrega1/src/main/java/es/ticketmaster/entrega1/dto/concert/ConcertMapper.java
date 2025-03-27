package es.ticketmaster.entrega1.dto.concert;

import es.ticketmaster.entrega1.model.Concert;

@Mapper(componentModel = "spring")
public interface ConcertMapper {
    ConcertDTO toDTO(Concert concert);

    Concert toDomain(ConcertDTO concertDTO);
}
