package es.ticketmaster.entrega1.dto.user;

/**
 * This DTO is the main one for the User, it stores all the main information of a user.
 */
public record UserDTO(Long id, String username, String password, String email, String country) {}