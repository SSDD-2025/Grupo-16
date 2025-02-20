package es.ticketmaster.entrega1.model;

import java.sql.Blob;
import java.util.List;

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

    //Concert List
    @OneToMany(mappedBy = "artist")
    private List<Concert> concertList;

    //Constructor for JPA
    protected Artist(){}

    //Contructor
    

    //Getters y Setters
    public long getId() {
        return id;
    }

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
        this.videoLink = videoLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularityIndex() {
        return popularityIndex;
    }

    public void setPopularityIndex(int popularityIndex) {
        this.popularityIndex = popularityIndex;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    public String getExtendedInfo() {
        return extendedInfo;
    }

    public void setExtendedInfo(String extendedInfo) {
        this.extendedInfo = extendedInfo;
    }

    public String getBestAlbumSpotifyLink() {
        return bestAlbumSpotifyLink;
    }

    public void setBestAlbumSpotifyLink(String bestAlbumSpotifyLink) {
        this.bestAlbumSpotifyLink = bestAlbumSpotifyLink;
    }

    public String getBestAlbumAppleLink() {
        return bestAlbumAppleLink;
    }

    public void setBestAlbumAppleLink(String bestAlbumAppleLink) {
        this.bestAlbumAppleLink = bestAlbumAppleLink;
    }

    public String getLatestAlbumSpotifyLink() {
        return latestAlbumSpotifyLink;
    }

    public void setLatestAlbumSpotifyLink(String latestAlbumSpotifyLink) {
        this.latestAlbumSpotifyLink = latestAlbumSpotifyLink;
    }

    public String getLatestAlbumAppleLink() {
        return latestAlbumAppleLink;
    }

    public void setLatestAlbumAppleLink(String latestAlbumAppleLink) {
        this.latestAlbumAppleLink = latestAlbumAppleLink;
    }

    public Blob getBestAlbumPhoto() {
        return bestAlbumPhoto;
    }

    public void setBestAlbumPhoto(Blob bestAlbumPhoto) {
        this.bestAlbumPhoto = bestAlbumPhoto;
    }

    public Blob getLatestAlbumPhoto() {
        return latestAlbumPhoto;
    }

    public void setLatestAlbumPhoto(Blob latestAlbumPhoto) {
        this.latestAlbumPhoto = latestAlbumPhoto;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public List<Concert> getConcertList() {
        return concertList;
    }

    public void setConcertList(List<Concert> concertList) {
        this.concertList = concertList;
    }
}
