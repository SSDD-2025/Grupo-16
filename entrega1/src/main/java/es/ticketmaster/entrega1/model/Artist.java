package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

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
    private int popularityIndex; //Number of listeners
    private LocalDateTime sessionCreated;

    @Lob
    private Blob photo; //photo of the artist

    private String mainInfo; //main info about the artist (NOT NULL)
    private String extendedInfo; //extended info about the artist (COULD BE NULL)

    //Album Links to Spotify and Apple Music
    private String bestAlbumSpotifyLink;
    private String bestAlbumAppleLink;
    private String latestAlbumSpotifyLink;
    private String latestAlbumAppleLink;

    //Album Images
    @Lob
    private Blob bestAlbumPhoto;

    @Lob
    private Blob latestAlbumPhoto;

    //Music Video Link
    private String videoLink;

    /*Concert List -> For the first delivery, we assume that deleting an artist
    also deleting the concerts associated with him or her*/
    @OneToMany(mappedBy = "artist", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Concert> concertList;

    /**
     * Contructor for the JPA
     */
    protected Artist(){}

    /** Main Constructor
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
    public Artist(String name, int popularityIndex, Blob photo, String mainInfo, String extendedInfo,
            String bestAlbumSpotifyLink, String bestAlbumAppleLink, String latestAlbumSpotifyLink,
            String latestAlbumAppleLink, Blob bestAlbumPhoto, Blob latestAlbumPhoto, String videoLink) {
        this.name = name;
        this.popularityIndex = popularityIndex;
        this.photo = photo;
        this.mainInfo = mainInfo;
        this.extendedInfo = extendedInfo;
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
        this.bestAlbumAppleLink = bestAlbumAppleLink;
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
        this.latestAlbumAppleLink = latestAlbumAppleLink;
        this.bestAlbumPhoto = bestAlbumPhoto;
        this.latestAlbumPhoto = latestAlbumPhoto;
        this.videoLink = videoLink;
    }

    /**
     * Same as the main constructor but inicialition to null the album photos
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
            String bestAlbumSpotifyLink, String bestAlbumAppleLink, String latestAlbumSpotifyLink,
            String latestAlbumAppleLink, String videoLink) {
        this.name = name;
        this.popularityIndex = popularityIndex;
        this.photo = photo;
        this.mainInfo = mainInfo;
        this.extendedInfo = extendedInfo;
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
        this.bestAlbumAppleLink = bestAlbumAppleLink;
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
        this.latestAlbumAppleLink = latestAlbumAppleLink;
        this.bestAlbumPhoto = null;
        this.latestAlbumPhoto = null;
        this.videoLink = videoLink;
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
     * @param id the id to be setted
     */
    public void setId(long id){
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
    public int getPopularityIndex() {
        return popularityIndex;
    }

    /**
     * 
     * @param popularityIndex new listeners number
     */
    public void setPopularityIndex(int popularityIndex) {
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
     * @return the link to the best album on Apple Music
     */
    public String getBestAlbumAppleLink() {
        return bestAlbumAppleLink;
    }

    /**
     * 
     * @param bestAlbumAppleLink the new link to the best album on Apple Music
     */
    public void setBestAlbumAppleLink(String bestAlbumAppleLink) {
        this.bestAlbumAppleLink = bestAlbumAppleLink;
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
     * @return the link to the latest album on Apple Music
     */
    public String getLatestAlbumAppleLink() {
        return latestAlbumAppleLink;
    }

    /**
     * 
     * @param latestAlbumAppleLink new link to the latest album on Apple Music
     */
    public void setLatestAlbumAppleLink(String latestAlbumAppleLink) {
        this.latestAlbumAppleLink = latestAlbumAppleLink;
    }

    /**
     * 
     * @return photo cover of the best album
     */
    public Blob getBestAlbumPhoto() {
        return bestAlbumPhoto;
    }

    /**
     * 
     * @param bestAlbumPhoto set new cover to the best album
     */
    public void setBestAlbumPhoto(Blob bestAlbumPhoto) {
        this.bestAlbumPhoto = bestAlbumPhoto;
    }

    /**
     * 
     * @return photo cover of the latest album
     */
    public Blob getLatestAlbumPhoto() {
        return latestAlbumPhoto;
    }

    /**
     * 
     * @param latestAlbumPhoto set new cover to the latets album
     */
    public void setLatestAlbumPhoto(Blob latestAlbumPhoto) {
        this.latestAlbumPhoto = latestAlbumPhoto;
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
     * @return the date when the artist logged for the first time (created its information)
     */
    public LocalDateTime getSessionCreated() {
        return sessionCreated;
    }

    /**
     * 
     * @param sessionCreated the new date when the artist logged for the first time (created its information)
     * It will be no necessary to set a new sessionCreated when there is already one 
     */
    public void setSessionCreated(LocalDateTime sessionCreated) {
        this.sessionCreated = sessionCreated;
    }

    
}
