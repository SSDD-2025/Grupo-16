package es.ticketmaster.entrega1.dto.concert;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import es.ticketmaster.entrega1.model.Concert;

@Mapper(componentModel = "spring")
public interface ConcertMapper {
    ConcertDTO toDTO(Concert concert);

    Concert toDomain(ConcertDTO concertDTO);

    List<ConcertDTO> toDTOs(Collection<Concert> concert);
}
