package es.ticketmaster.entrega1.dto.artist;

import java.time.LocalDateTime;

public record ArtistDTO(Long id, String name, int popularityIndex, LocalDateTime sessionCreated, boolean hasPage, 
    String mainInfo, String extendedInfo, String bestAlbumSpotifyLink, String bestAlbumAppleLink, String latestAlbumSpotifyLink, 
    String latestAlbumAppleLink, String videoLink) {}