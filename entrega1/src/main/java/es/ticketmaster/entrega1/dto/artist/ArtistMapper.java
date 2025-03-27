package es.ticketmaster.entrega1.dto.artist;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import es.ticketmaster.entrega1.model.Artist;

@Mapper(componentModel = "spring")
public interface ArtistMapper{
    ArtistDTO toDTO(Artist artist);
    List<ArtistDTO> toDTOs(Collection<Artist> artists);
    Artist toDomain(ArtistDTO artistDTO);
}
