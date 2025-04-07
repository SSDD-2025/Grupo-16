package es.ticketmaster.entrega1.dto.artist;

public record ArtistDTO(Long id, String name, long popularityIndex, boolean hasPage, 
    String mainInfo, String extendedInfo, String bestAlbumSpotifyLink, String latestAlbumSpotifyLink, 
    String videoLink, String photoLink) {}