package es.ticketmaster.entrega1.service;

import java.time.LocalDateTime;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Initializes the database with default data.
     *
     * This method is called automatically after the bean is created
     * (PostConstruct). It populates the database with default entries,
     * including users, artists and concerts.
     *
     */
    @PostConstruct
    public void initializeDatabase() {
        // Variables of the Entities
        UserEntity user;
        Artist artist;
        Concert concert;

        String mainText;
        String extText;

        //Save Artist: Tate Mcrae
        mainText = "Tate McRae, a singer, songwriter, and dancer born in Calgary (Canada) on July 1, 2003, gained fame with You Broke Me First (2021). She released hits like greedy, exes, and 2 hands. Her latest album, So Close To What, features the single It's ok I'm ok";
        extText = "Tate McRae has been called the teen dance star turned future pop idol by i-D. She's praised by artists like Paula Abdul, who called her a gift from God, and choreographers like Stacey Tookey, Blake McGrath, and Travis Wall, who named her one of his muses";
        artist = new Artist("Tate McRae", 45652467, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Tate McRae.jpg"), mainText, extText, "https://open.spotify.com/intl-es/album/0OUOx6rJXtL66AzTnP9KUE", "https://music.apple.com/es/album/think-later/1716102849", "https://open.spotify.com/intl-es/album/3w32SV56JvtJXsrYtThwzP", "https://music.apple.com/es/album/so-close-to-what/1797590677", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\think later.jpg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\SCTW.webp"), "https://www.youtube.com/embed/rwlFWWGaZ5Y");

        artistRepository.save(artist);

        //Save Tate's Concerts
        concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.MAY, 9, 21, 0), "Europe", "Tate's First Arena Concert Ever", 60, 30, 30, 30, 60, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\MPT poster.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.AUGUST, 22, 21, 0), "America", "Tate's First Arena Concert Ever", 70, 50, 50, 50, 80, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\MPT poster.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.SEPTEMBER, 13, 21, 0), "America", "Tate's First Arena Concert Ever", 80, 70, 70, 70, 100, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\MPT poster.jpg"));
        concertRepository.save(concert);

        //Save Artist: Sabrina Carpenter
        mainText = "Sabrina Carpenter, singer, songwriter, and actress born in Pennsylvania (USA) on May 11, 1999, gained fame with Nonsense (2022). She released hits like Sue Me, In My Bed, and Taste. Her latest album, Short n' Sweet, features the single Espresso";
        extText = "Sabrina moved to Island Records in 2021 and released Emails I Can't Send (2022). She opened for Taylor Swift on the Eras Tour (2023) and released Short n' Sweet (2024), topping the Billboard 200. She's starred in TV movies and Broadway musicals.";
        artist = new Artist("Sabrina Carpenter", 72520327, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Sabrina Carpenter.jpg"), mainText, extText, "https://open.spotify.com/intl-es/album/29mlGxS6kxq1EHxlX1EAZK", "https://music.apple.com/es/album/singular-act-i/1438671104", "https://open.spotify.com/intl-es/album/3iPSVi54hsacKKl1xIR2eH", "https://music.apple.com/es/album/short-n-sweet/1750307020", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Singular Act 1.jpg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Short n Sweet.jpg"), "https://www.youtube.com/embed/eVli-tstM5E");

        artistRepository.save(artist);

        //Save Sabrina's Concerts
        concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 3, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\SNS Tour.jpeg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 13, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\SNS Tour.jpeg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 23, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\SNS Tour.jpeg"));
        concertRepository.save(concert);

        //Save Artist: Gracie Abrams
        mainText = "Gracie Abrams, born on September 7, 1999, is an American singer and songwriter, daughter of director J.J. Abrams. She debuted with the EP Minor (2020) and her discography includes Good Riddance (2023) and The Secret of Us (2024)";
        extText = "Abrams cites influences like Bon Iver, Elvis Costello, Taylor Swift, Lorde and Phoebe Bridgers. Artists like Olivia Rodrigo, Billie Eilish and Post Malone admire her, with Rodrigo revealing that Abrams' Minor inspired her hit Drivers License.";
        artist = new Artist("Gracie Abrams", 50811132, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Gracie Abrams.jpg"), mainText, extText, "https://open.spotify.com/intl-es/album/78YYcghEDz2dHRx0EcDGXZ", "https://music.apple.com/es/album/good-riddance-deluxe/1691828042", "https://open.spotify.com/intl-es/album/56bdWeO40o3WfAD2Lja4dl", "https://music.apple.com/es/album/the-secret-of-us/1743054543", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Good Riddance.jpg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Secret Of Us.jpg"), "https://www.youtube.com/embed/uxjhN_Donfw");

        artistRepository.save(artist);

        //Save Gracie's Concerts
        concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MARCH, 3, 21, 0), "Europe", "Europe leg of the The Secret Of Us Tour", 75, 30, 30, 30, 60, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\TSOU Tour Europe.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MARCH, 10, 21, 0), "Europe", "Europe leg of the The Secret Of Us Tour", 75, 30, 30, 30, 60, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\TSOU Tour Europe.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MAY, 3, 21, 0), "Oceania", "Oceania leg of the The Secret Of Us Tour", 80, 40, 40, 40, 50, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\TSOU Tour Oceania.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MAY, 17, 21, 0), "Oceania", "Oceania leg of the The Secret Of Us Tour", 80, 40, 40, 40, 50, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\TSOU Tour Oceania.jpg"));
        concertRepository.save(concert);

        // Create and Save Artist: Katy Perry
        mainText = "Katy Perry is an American singer, songwriter and actress, best known for her catchy pop songs and colorful style. She rose to fame with her hit \"I Kissed a Girl\", followed by chart-topping singles like \"Firework,\" \"Teenage Dream,\" \"Roar,\" or \"Dark Horse.\"";
        extText = "Katy Perry is renowned for her vibrant persona, elaborate stage productions, and empowering anthems. Over her career, she has received numerous awards, including American Music Awards and Billboard Music Awards.";
        artist = new Artist("Katy Perry", 60637611, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Katy Perry.jpeg"), mainText, extText, "https://open.spotify.com/intl-es/album/06SY6Ke6mXzZHhURLVU57R", "https://music.apple.com/es/album/teenage-dream/716340563", "https://open.spotify.com/intl-es/album/3jxt1S4JtW4uFalBwlfehS", "https://music.apple.com/es/album/143/1756201803", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Teenage Dream.jpeg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\143.jpeg"), "https://www.youtube.com/embed/EVIJUH29pjU");

        artistRepository.save(artist);

        // Create and Save Katy Perry's concerts
        concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.NOVEMBER, 11, 21, 0), "Europe", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 60, 100, 100, 100, 200, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Lifetimes Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.AUGUST, 11, 19, 0), "America", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 90, 150, 150, 150, 300, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Lifetimes Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.JUNE, 9, 20, 0), "Oceania", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 60, 100, 100, 100, 200, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Lifetimes Tour.jpg"));
        concertRepository.save(concert);

        // Create and Save Artist: Beyonce
        mainText = "Beyonce is a globally renowned singer, songwriter, actress, and businesswoman. Known for her powerful vocals, electrifying performances, and hits like Halo and Single Ladies, she has won numerous awards, including Grammys.";
        extText = "A trailblazer in music and fashion, Beyoncé blends R&B, pop, and hip-hop with unmatched artistry. Beyond music, she champions social justice, female empowerment, and Black excellence worldwide.";
        artist = new Artist("Beyonce", 60143515, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Beyonce.jpeg"), mainText, extText, "https://open.spotify.com/intl-es/album/6FJxoadUE4JNVwWHghBwnb", "https://music.apple.com/es/album/renaissance/1630005298", "https://open.spotify.com/intl-es/album/6BzxX6zkDsYKFJ04ziU5xQ", "https://music.apple.com/es/album/cowboy-carter/1738363766", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Renaissance.jpeg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Cowboy Carter.jpeg"), "https://www.youtube.com/embed/UL_JXt4FI6E");

        artistRepository.save(artist);

        // Create and Save Beyonce's concerts
        concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.MAY, 15, 19, 0), "America", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 120, 80, 80, 80, 150, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Cowboy Carter Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.JUNE, 14, 17, 0), "Europe", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 100, 90, 90, 90, 200, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Cowboy Carter Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.MAY, 4, 19, 0), "America", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 120, 80, 80, 80, 150, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Cowboy Carter Tour.jpg"));
        concertRepository.save(concert);

        // Create and Save Artist: Taylor Swift
        mainText = "Taylor Swift is a record-breaking singer-songwriter known for her storytelling, genre versatility, and hits like Love Story and Anti-Hero. From country roots to pop, she dominates music charts, wins countless awards, and influences culture worldwide.";
        extText = "A master of reinvention, Taylor Swift crafts deeply personal lyrics that resonate globally. With The Eras Tour, she celebrates her evolution. Beyond music, she’s a business mogul, advocate for artists’ rights, and a voice for social change.";
        artist = new Artist("Taylor Swift", 86426795, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\Taylor Swift.jpeg"), mainText, extText, "https://open.spotify.com/intl-es/album/64LU4c1nfjz1t4VnGhagcg", "https://music.apple.com/es/album/1989-taylors-version/1708308989", "https://open.spotify.com/intl-es/album/1Mo4aZ8pdj6L1jx8zSwJnt", "https://music.apple.com/es/album/the-tortured-poets-department/1736268193", imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\1989.jpeg"), imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\TTPD.jpeg"), "https://www.youtube.com/embed/q3zqJs7JUCQ");

        artistRepository.save(artist);

        // Create and Save Taylor's concerts
        concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.MAY, 30, 20, 0), "Europe", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 220, 0, 0, 0, 0, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Eras Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.OCTOBER, 18, 17, 0), "America", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 320, 0, 0, 0, 0, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Eras Tour.jpg"));
        concertRepository.save(concert);

        concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.FEBRUARY, 7, 18, 0), "Asia", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 100, 0, 0, 0, 0, imageService.getBlobOf("entrega1\\src\\main\\resources\\static\\DDBB\\The Eras Tour.jpg"));
        concertRepository.save(concert);

        //Create Users
        user = new UserEntity("armiiin13", passwordEncoder.encode("eras1325"), "armiingrc@yahoo.com", "Europe", "USER","ADMIN");
        userRepository.save(user);
        user = new UserEntity("Fonssi29", passwordEncoder.encode("pollitoPio"), "fonssi@gmail.com", "America", "USER","ADMIN");
        this.userRepository.save(user);
        user = new UserEntity("davih", passwordEncoder.encode("davilico"), "drg@gmail.com", "Europe", "USER","ADMIN");
        userRepository.save(user);
    }
}
