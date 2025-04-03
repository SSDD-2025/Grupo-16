package es.ticketmaster.entrega1.dto.artist;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.ticketmaster.entrega1.model.Artist;

@Mapper(componentModel = "spring")
public interface ArtistMapper{
    ArtistDTO toDTO(Artist artist);

    @Mapping(target="photo", ignore = true)
    @Mapping(target="bestAlbumPhoto", ignore = true)
    @Mapping(target="latestAlbumPhoto", ignore = true)
    @Mapping(target="concertList", ignore = true)
    List<ArtistDTO> toDTOs(Collection<Artist> artists);

    @Mapping(target="bestAlbumPhoto", ignore = true)
    @Mapping(target="latestAlbumPhoto", ignore = true)
    @Mapping(target="photo", ignore = true)
    @Mapping(target="hasPage", ignore = true)
    @Mapping(target="sessionCreated", ignore = true)
    @Mapping(target="concertList", ignore = true)
    Artist toDomain(ArtistDTO artistDTO);
}
