package es.ticketmaster.entrega1.dto.artist;

public record ArtistDTO(Long id, String name, Long popularityIndex, Boolean hasPage, 
    String mainInfo, String extendedInfo, String bestAlbumSpotifyLink, String latestAlbumSpotifyLink, 
    String videoLink, String photoLink) {}