package es.ticketmaster.entrega1.dto.concert;

import java.time.LocalDateTime;

import es.ticketmaster.entrega1.model.Artist;

public record ConcertDTO(
    Long id,
    Artist artist,
    String name,
    String info,
    LocalDateTime date,
    String place,
    float price,
    int westStandsNumber,
    int eastStandsNumber, 
    int northStandsNumber,
    int generalAdmissionNumber

    ){}
