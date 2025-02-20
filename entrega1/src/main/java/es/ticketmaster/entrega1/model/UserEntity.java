package es.ticketmaster.entrega1.model;

import java.sql.Blob;

import org.springframework.web.context.annotation.SessionScope;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
@SessionScope
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String userName;
    private String password;
    private String email;
    private String country;
    @Lob
    private Blob profilePicture;
    @OneToMany
    private List<Artist> artistsList = new LinkedList<>();
    @OneToMany
    private List<Tickets> ticketList = new LinkedList<>();

    protected UserEntity () {
        /* Constructor used by the JPA interface. */
    }

    /* Constructor without the profile picture of the user. */
    public UserEntity (String userName, String password, String email, String country) {
        if ((!userName.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.country = country;
        }
    }

    /* Constructor with the profile picture of the user. */
    public UserEntity (String userName, String password, String email, String country, Blob picture) {
        if ((!userName.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.country = country;
        }
        this.profilePicture = picture;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCountry() {
        return this.country;
    }

    public Blob getProfilePicture() {
        return this.profilePicture;
    }
    
    public List<Artist> getArtistsList() {
         return this.artistsList;
    }

    public List<Tickets> getTicketsList() {
         return this.ticketList;
    }

    public void setUserName(String newName) {
        this.userName = newName;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setCountry(String newCountry) {
        this.country = newCountry;
    }

    public void setProfilePicture(Blob newPic) {
        this.profilePicture = newPic;
    }
}