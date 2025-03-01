package es.ticketmaster.entrega1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.model.Concert;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.ArtistRepository;
import es.ticketmaster.entrega1.repository.ConcertRepository;
import es.ticketmaster.entrega1.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class InitService {
    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Initializes the database with default data.
     * 
     * This method is called automatically after the bean is created (PostConstruct). 
     * It populates the database with default entries, including users, artists and concerts.
     * 
     */
    @PostConstruct
    public void initializeDatabase(){
        // Variables of the Entities
        UserEntity user;
        Artist artist;
        Concert concert;
        
        //Create Artist


        //Create Concerts


        //Create Users
        user = new UserEntity("armiiin13", "eras1325", "armiingrc@yahoo.com", "Europe");
        userRepository.save(user);

    }
}
