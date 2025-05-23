package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Artist {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id; //id of the artist

    private String name; //artistic name
    private long popularityIndex; //Number of listeners
    private LocalDateTime sessionCreated;

    @JsonIgnore
    @Lob
    private Blob photo; //photo of the artist
    private String photoLink; //If the Artist has any photo, the API REST link is saved here

    private boolean hasPage;

    private String mainInfo; //main info about the artist (NOT NULL)
    private String extendedInfo; //extended info about the artist (COULD BE NULL)

    //Album Links to Spotify and Apple Music
    private String bestAlbumSpotifyLink;
    private String latestAlbumSpotifyLink;

    //Music Video Link
    private String videoLink;

    /*Concert List -> For the first delivery, we assume that deleting an artist
    also deleting the concerts associated with him or her*/
    @JsonIgnore
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Concert> concertList;

    /**
     * Contructor for the JPA
     */
    public Artist() {
    }

    /**
     * Main Constructor
     *
     * @param name name of the artist
     * @param popularityIndex number of Spotify listeners
     * @param photo photo of the artist
     * @param mainInfo main information about the artist
     * @param extendedInfo extended information about the artist
     * @param bestAlbumSpotifyLink link to Spotify of the best album
     * @param bestAlbumAppleLink link to Apple Music of the best album
     * @param latestAlbumSpotifyLink link to Spotify of the latest album
     * @param latestAlbumAppleLink link to Apple Music of the latest album
     * @param bestAlbumPhoto best album photo cover
     * @param latestAlbumPhoto latest album photo cover
     * @param videoLink URL to youtube to watch the music video
     */
    public Artist(String name, int popularityIndex, Blob photo, String photoLink, String mainInfo, String extendedInfo,
            String bestAlbumSpotifyLink, String latestAlbumSpotifyLink, String videoLink) {
        this.name = name;
        this.popularityIndex = popularityIndex;
        this.photo = photo;
        this.photoLink = photoLink;
        this.mainInfo = mainInfo;
        this.extendedInfo = extendedInfo;
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
        this.videoLink = videoLink;
        this.hasPage = true;
    }

    /**
     * Same as the main constructor but inicialition to null the album photos
     *
     * @param name
     * @param popularityIndex
     * @param photo
     * @param mainInfo
     * @param extendedInfo
     * @param bestAlbumSpotifyLink
     * @param bestAlbumAppleLink
     * @param latestAlbumSpotifyLink
     * @param latestAlbumAppleLink
     * @param videoLink
     */
    public Artist(String name, int popularityIndex, Blob photo, String mainInfo, String extendedInfo,
            String bestAlbumSpotifyLink, String latestAlbumSpotifyLink, String videoLink) {
        this.name = name;
        this.popularityIndex = popularityIndex;
        this.photo = photo;
        this.mainInfo = mainInfo;
        this.extendedInfo = extendedInfo;
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
        this.videoLink = videoLink;
        this.hasPage = true;
    }

    public Artist(String name) {
        this.name = name;
        this.hasPage = false;
    }

    //Getters y Setters
    /**
     *
     * @return artist id
     */
    public long getId() {
        return id;
    }

    /**
     * Id setter needed for database operations
     *
     * @param id the id to be setted
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return artist's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name new name of the artist
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the popularityIndex
     */
    public long getPopularityIndex() {
        return popularityIndex;
    }

    /**
     *
     * @param popularityIndex new listeners number
     */
    public void setPopularityIndex(long popularityIndex) {
        this.popularityIndex = popularityIndex;
    }

    /**
     *
     * @return artist's photo
     */
    public Blob getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo set new photo for the artist
     */
    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    /**
     *
     * @return if the artist has information loaded at the HTML
     */
    public boolean isHasPage() {
        return hasPage;
    }

    /**
     *
     * @param hasPage indicated wheter the artist information is loaded in the
     * database or not
     */
    public void setHasPage(boolean hasPage) {
        this.hasPage = hasPage;
    }

    /**
     *
     * @return the main information about the artist
     */
    public String getMainInfo() {
        return mainInfo;
    }

    /**
     *
     * @param mainInfo set the new main info paragraph
     */
    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    /**
     *
     * @return the extended information about the artist
     */
    public String getExtendedInfo() {
        return extendedInfo;
    }

    /**
     *
     * @param extendedInfo set the new extended info paragraph
     */
    public void setExtendedInfo(String extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    /**
     *
     * @return the link to the best album on Spotify
     */
    public String getBestAlbumSpotifyLink() {
        return bestAlbumSpotifyLink;
    }

    /**
     *
     * @param bestAlbumSpotifyLink the new link to the best album on Spotify
     */
    public void setBestAlbumSpotifyLink(String bestAlbumSpotifyLink) {
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
    }

    /**
     *
     * @return Returns the artist's profile photo link in the API REST. Null in
     * case the artist has no photo
     */
    public String getPhotoLink() {
        return this.photoLink;
    }

    /**
     * @param photoLink API REST photo link of the artist.
     */
    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    /**
     *
     * @return the link to the latest album on Spotify
     */
    public String getLatestAlbumSpotifyLink() {
        return latestAlbumSpotifyLink;
    }

    /**
     *
     * @param latestAlbumSpotifyLink new link to the latest album on Spotify
     */
    public void setLatestAlbumSpotifyLink(String latestAlbumSpotifyLink) {
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
    }

    /**
     *
     * @return music video link
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     *
     * @param videoLink set a new video music link
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    /**
     *
     * @return list of the artist's concerts
     */
    public List<Concert> getConcertList() {
        return concertList;
    }

    /**
     *
     * @param concertList gives the artist a new list of concerts
     */
    public void setConcertList(List<Concert> concertList) {
        this.concertList = concertList;
    }

    /**
     *
     * @return the date when the artist logged for the first time (created its
     * information)
     */
    public LocalDateTime getSessionCreated() {
        return sessionCreated;
    }

    /**
     *
     * @param sessionCreated the new date when the artist logged for the first
     * time (created its information) It will be no necessary to set a new
     * sessionCreated when there is already one
     */
    public void setSessionCreated(LocalDateTime sessionCreated) {
        this.sessionCreated = sessionCreated;
    }

    /**
     * Method that determines if an artist can have or not its own page in the
     * webserver. The criteria used is: "An artist can have page if and only if
     * it has: popularityIndex, mainInfo, extendedInfo and the three required
     * links (Spotify+YouTube)".
     *
     * @return wether the artist can(not) have its own page.
     */
    public boolean canHavePage() {
        return (this.popularityIndex > 0) && (this.mainInfo != null && !this.mainInfo.isBlank()) && (this.extendedInfo != null && !this.extendedInfo.isBlank()) && (this.bestAlbumSpotifyLink != null && !this.bestAlbumSpotifyLink.isBlank()) && (this.latestAlbumSpotifyLink != null && !this.latestAlbumSpotifyLink.isBlank()) && (this.videoLink != null && !this.videoLink.isBlank());
    }
}
