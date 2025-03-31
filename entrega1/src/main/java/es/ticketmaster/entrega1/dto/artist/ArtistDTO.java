package es.ticketmaster.entrega1.dto.artist;

public record ArtistDTO(Long id, String name, int popularityIndex, 
    String mainInfo, String extendedInfo, String bestAlbumSpotifyLink, String bestAlbumAppleLink, String latestAlbumSpotifyLink, 
    String latestAlbumAppleLink, String videoLink) {}