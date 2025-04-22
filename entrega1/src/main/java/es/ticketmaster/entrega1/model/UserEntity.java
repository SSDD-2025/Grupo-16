package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;
    private String password;
    private String email;
    private String country;

    @JsonIgnore
    @Lob
    private Blob profilePicture;

    @OneToMany
    private List<Artist> artistsList = new LinkedList<>();
    
    @OneToMany(mappedBy = "ticketUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> ticketList = new LinkedList<>();

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    public UserEntity() {
        /* Constructor used by the JPA interface. */
        this.roles = List.of("USER"); //We stablish the default role
    }

    /* Constructor without the profile picture of the user. */
    public UserEntity(String username, String password, String email, String country) {
        if ((!username.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.country = country;
            this.roles = List.of("USER");
        }
    }

    /* Constructor without the profile picture of the user and letting enter the roles*/
    public UserEntity(String username, String password, String email, String country, String... roles) {
        if ((!username.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.country = country;
            this.roles = List.of(roles);
        }
    }

    /* Constructor with the profile picture of the user. */
    public UserEntity(String username, String password, String email, String country, Blob picture) {
        if ((!username.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.country = country;
        }
        this.profilePicture = picture;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
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

    public List<Ticket> getTicketList() {
        return this.ticketList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String newName) {
        this.username = newName;
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

    public void setArtistList(List<Artist> list) {
        this.artistsList = list;
    }

    public void setTicketList(List<Ticket> list) {
        this.ticketList = list;
    }

    public void setProfilePicture(Blob newPic) {
        this.profilePicture = newPic;
    }

    public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

    public void setRole(String role){
        this.roles.add(role);
    }
}
