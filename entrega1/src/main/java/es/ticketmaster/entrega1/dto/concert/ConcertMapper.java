package es.ticketmaster.entrega1.dto.concert;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.ticketmaster.entrega1.model.Concert;

@Mapper(componentModel = "spring")
public interface ConcertMapper {
    ConcertDTO toDTO(Concert concert);

    Concert toDomain(ConcertDTO concertDTO);

    List<ConcertDTO> toDTOs(Collection<Concert> concert);

    BasicConcertDTO toBasicConcertDTO(Concert concert);
    List<BasicConcertDTO> toBasicConcertDTOs(Collection<Concert> concerts);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "ticketList", ignore = true)
    @Mapping(target = "eastStandsNumber", ignore = true)
    @Mapping(target = "northStandsNumber", ignore = true)
    @Mapping(target = "westStandsNumber", ignore = true)
    @Mapping(target = "generalAdmissionNumber", ignore = true)
    Concert toBasicConcertEntity(BasicConcertDTO concertDTO);
}
