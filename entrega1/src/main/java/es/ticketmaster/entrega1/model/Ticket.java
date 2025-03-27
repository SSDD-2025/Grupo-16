package es.ticketmaster.entrega1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    /* In the database, the column that will save the user, will be called userId */
    private UserEntity ticketUser;
    @ManyToOne
    @JoinColumn(name = "concertId")
    /* In the database, the column that will save the concert, will be called concertId */
    private Concert concert;
    private String zone;
    private float price;

    protected Ticket() {
        /* Constructor used by the JPA interface */
    }

    /**
     * Constructor to initialize the ticket with its zone and price.
     *
     * @param zone of the ticket (section).
     * @param price is how much its cost.
     */
    public Ticket(String zone, float price) {
        if (!zone.isBlank()) {
            this.zone = zone;
        }
        this.price = price;
    }

    /**
     * Constructor that will initialize the ticket with all of its attributes.
     *
     * @param zone of the ticket (section).
     * @param price is how much its cost.
     * @param user associated to the ticket.
     * @param concert associated to the ticket.
     */
    public Ticket(String zone, float price, UserEntity user, Concert concert) {
        if (!zone.isBlank()) {
            this.zone = zone;
        }
        if (user != null) {
            this.ticketUser = user;
        }
        if (concert != null) {
            this.concert = concert;
        }
        this.price = price;
    }

    public long getId() {
        return this.id;
    }

    public UserEntity getTicketUser() {
        return this.ticketUser;
    }

    public Concert getConcert() {
        return this.concert;
    }

    public String getZone() {
        return this.zone;
    }

    public float getPrice() {
        return this.price;
    }

    public void setUser(UserEntity user) {
        if (user != null) {
            this.ticketUser = user;
        }
    }

    public void setConcert(Concert concert) {
        if (concert != null) {
            this.concert = concert;
        }
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
