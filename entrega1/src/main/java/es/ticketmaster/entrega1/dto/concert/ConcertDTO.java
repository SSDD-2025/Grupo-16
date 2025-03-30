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

    ){
        /**
         * Method that specifies wether there are remaining West Stands entrances
         * left or not
         *
         * @return a boolean representing the previous value
         */
        public boolean remainWestStands() {
            return westStandsNumber > 0;
        }

        /**
         * Method that specifies wether there are remaining East Stands entrances
         * left or not
         *
         * @return a boolean representing the previous value
         */
        public boolean remainEastStands() {
            return eastStandsNumber > 0;
        }

        /**
         * Method that specifies wether there are remaining North Stands entrances
         * left or not
         *
         * @return a boolean representing the previous value
         */
        public boolean remainNorthStands() {
            return northStandsNumber > 0;
        }

        /**
         * Method that specifies wether there are remaining General Admission Stands
         * entrances left or not
         *
         * @return a boolean representing the previous value
         */
        public boolean remainGeneralAdmissionStands() {
            return generalAdmissionNumber > 0;
        }

        /**
         * Getter for the date attribute
         *
         * @return date hour displayed with format
         */
        public String getFormattedTime() {
            return String.format("%02d:%02d", date.getHour(), date.getMinute());
        }
    }
