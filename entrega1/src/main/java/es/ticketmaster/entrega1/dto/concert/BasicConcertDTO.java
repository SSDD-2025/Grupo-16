package es.ticketmaster.entrega1.dto.concert;

import java.time.LocalDateTime;

import es.ticketmaster.entrega1.model.Artist;

public record BasicConcertDTO(long id, Artist artist, String name, String place, LocalDateTime date, String info) {
    /**
     * Getter for the date attribute
     *
     * @return date hour displayed with format
     */
    public String getFormattedTime() {
        return String.format("%02d:%02d", date.getHour(), date.getMinute());
    }
}
