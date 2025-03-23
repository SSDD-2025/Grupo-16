package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

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

    private String userName;
    private String password;
    private String email;
    private String country;
    @Lob
    private Blob profilePicture;
    @OneToMany
    private List<Artist> artistsList = new LinkedList<>();
    @OneToMany(mappedBy = "ticketUser")
    private List<Ticket> ticketList = new LinkedList<>();

    @ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

    protected UserEntity() {
        /* Constructor used by the JPA interface. */
    }

    /* Constructor without the profile picture of the user. */
    public UserEntity(String userName, String password, String email, String country) {
        if ((!userName.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.country = country;
            this.roles = List.of("USER");
        }
    }

    /* Constructor without the profile picture of the user and letting enter the roles*/
    public UserEntity(String userName, String password, String email, String country, String... roles) {
        if ((!userName.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.country = country;
            this.roles = List.of(roles);
        }
    }

    /* Constructor with the profile picture of the user. */
    public UserEntity(String userName, String password, String email, String country, Blob picture) {
        if ((!userName.isBlank()) && (!password.isBlank()) && (!email.isBlank()) && (!country.isBlank())) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.country = country;
        }
        this.profilePicture = picture;
    }

    public long getId() {
        return id;
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

    public List<Ticket> getTicketsList() {
        return this.ticketList;
    }

    public void setId(long id) {
        this.id = id;
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
}
