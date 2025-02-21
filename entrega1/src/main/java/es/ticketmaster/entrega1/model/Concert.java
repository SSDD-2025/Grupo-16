package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


/**
 * Entity used by JPA Data to save Concerts in the DDBB.
 * <p>
 * Proportions structure and basic public methods to create, modify and save Concerts.
 *
 * @since 19-02-2025
 * @see Artist
 */
@Entity
public class Concert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; //DDBB automatically generated ID of the concert

    @ManyToOne
    private Artist artist; //Artist who gives the concert

    private String name; //Name that the concert has, may be the same as the tour name

    private String info; //Brief description of the concert, including specific information of the artist and place

    private LocalDateTime date; //Specific date (day and hour) when the concert occurs

    private String place; //Name of the place where the concert takes place

    private long price; //General price of every ticket

    private int westStandsNumber; //Number of West Stands tickets available 
    private int eastStandsNumber; //Number of East Stands tickets available 
    private int southStandsNumber; //Number of South Stands tickets available
    private int generalAdmissionNumber; //Number of General Admission tickets available

    private Blob image; //Image of the concert's poster

    /*
     * Constructor used by DataJPA internally
     */
    public Concert(){

    }

    /**
     * Constructor that creates a concert with it's basic attributes (which should not be null)
     * 
     * @param artist Artist who gives the concert
     * @param name Name of the concert - may coincide with the tour name
     * @param date Date when the concert occurs
     * @param place Ubication where the concert takes place
     * @param info General and specific information of the concert
     * @param price Price per ticket, equal price for every zone
     * @param westStandsNumber Remaining West Stands tickets available to buy
     * @param eastStandsNumber Remaining East Stands tickets available to buy
     * @param southStandsNumber Remaining South Stands tickets available to buy
     * @param generalAdmissionNumber Remaining General Admission Stands tickets available to buy
     */
    public Concert(Artist artist, String name, LocalDateTime date, String place, String info, 
    long price, int westStandsNumber, int eastStandsNumber, int southStandsNumber, int generalAdmissionNumber){
        super();
        this.artist = artist;
        this.name = name;
        this.date = date;
        this.place = place;
        this.info = info;
        this.price = price;
        this.westStandsNumber = westStandsNumber;
        this.eastStandsNumber = eastStandsNumber;
        this.southStandsNumber = southStandsNumber;
        this.generalAdmissionNumber = generalAdmissionNumber;
    }

    /**
     * Constructor that creates a concert with including the image poster
     * 
     * @param artist Artist who gives the concert
     * @param name Name of the concert - may coincide with the tour name
     * @param date Date when the concert occurs
     * @param place Ubication where the concert takes place
     * @param info General and specific information of the concert
     * @param price Price per ticket, equal price for every zone
     * @param westStandsNumber Remaining West Stands tickets available to buy
     * @param eastStandsNumber Remaining East Stands tickets available to buy
     * @param southStandsNumber Remaining South Stands tickets available to buy
     * @param generalAdmissionNumber Remaining General Admission Stands tickets available to buy
     * @param image Promotional poster of the concert
     */
    public Concert(Artist artist, String name, LocalDateTime date, String place, String info, long price, 
    int westStandsNumber, int eastStandsNumber, int southStandsNumber, int generalAdmissionNumber, Blob image){
        this(artist, name, date, place, info, price, westStandsNumber, eastStandsNumber, 
        southStandsNumber, generalAdmissionNumber);
        this.image = image;
    }

    /* Getters and setters of the attributes */

    /**
     * Getter for the id attribute
     * @return id of the concert
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the artist attribute
     * @return Artist who gives the concert
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Getter for the name attribute
     * @return Name of the concert
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the information attribute
     * @return Specific and general information of the concert
     */
    public String getInfo() {
        return info;
    }

    /**
     * Getter for the date attribute
     * @return Date when the concerts takes place
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Getter for the place attribute
     * @return Place where the concert takes place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Getter for the price attribute
     * @return General price for every ticket of the concert
     */
    public long getPrice() {
        return price;
    }

    /**
     * Getter for the westStandsNumber attribute
     * @return Number of available entrances/tickets in the West Stands zone
     */
    public int getWestStandsNumber() {
        return westStandsNumber;
    }

    /**
     * Getter for the easyStandsNumber attribute
     * @return Number of available entrances/tickets in the East Stands zone
     */
    public int getEastStandsNumber() {
        return eastStandsNumber;
    }

    /**
     * Getter for the southStandsNumber attribute
     * @return Number of available entrances/tickets in the South Stands zone
     */
    public int getSouthStandsNumber() {
        return southStandsNumber;
    }

    /**
     * Getter for the generalAdmissionNumber attribute
     * @return Number of available entrances/tickets in the General Admission zone
     */
    public int getGeneralAdmissionNumber() {
        return generalAdmissionNumber;
    }

    /**
     * Getter for the image attribute
     * @return Promotional poster of the concert
     */
    public Blob getImage() {
        return image;
    }

    /**
     * Setter for the name attribute
     * @param name Name of the concert
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the info attribute
     * @param info Specific and general information of the concert 
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Setter for the date attribute
     * @param date Date and hour when the concert takes time
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Setter for the place attribute
     * @param place Place where the concert takes place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Setter for the price attribute
     * @param price General price for every ticket of the concert
     */
    public void setPrice(long price) {
        this.price = price;
    }

    /**
     * Setter fot the westStandsNumber attribute
     * @param westStandsNumber Number of available entrances/tickets in the West Stands zone
     */
    public void setWestStandsNumber(int westStandsNumber) {
        this.westStandsNumber = westStandsNumber;
    }

    /**
     * Setter fot the eastStandsNumber attribute
     * @param eastStandsNumber Number of available entrances/tickets in the East Stands zone
     */
    public void setEastStandsNumber(int eastStandsNumber) {
        this.eastStandsNumber = eastStandsNumber;
    }

    /**
     * Setter fot the southStandsNumber attribute
     * @param southStandsNumber Number of available entrances/tickets in the South Stands zone
     */
    public void setSouthStandsNumber(int southStandsNumber) {
        this.southStandsNumber = southStandsNumber;
    }

    /**
     * Setter fot the generalAdmissionNumber attribute
     * @param generalAdmissionNumber Number of available entrances/tickets in the General Admission zone
     */
    public void setGeneralAdmissionNumber(int generalAdmissionNumber) {
        this.generalAdmissionNumber = generalAdmissionNumber;
    }

    /**
     * Setter fot the image attribute
     * @param image Promotional poster of the concert
     */
    public void setImage(Blob image) {
        this.image = image;
    }

    /**
     * Getter for the date attribute
     * @return date hour displayed with format
     */
    public String getFormattedTime() {
        return String.format("%02d:%02d", date.getHour(), date.getMinute());
    }

    
}
