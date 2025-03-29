package es.ticketmaster.entrega1.dto.user;

import java.util.List;

import es.ticketmaster.entrega1.model.Artist;

/**
 * This DTO is used to display the user's artist list.
 */
public record UserShowArtistsDTO(String username, List<Artist> artistsList) {}