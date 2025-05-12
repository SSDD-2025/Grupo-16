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

        //Only if data was not previously loaded, we load everything
        if(userRepository.findByUsername("davih").isEmpty()){
            //Save Artist: Tate Mcrae
            mainText = "Tate McRae, a singer, songwriter, and dancer born in Calgary (Canada) on July 1, 2003, gained fame with You Broke Me First (2021). She released hits like greedy, exes, and 2 hands. Her latest album, So Close To What, features the single It's ok I'm ok";
            extText = "Tate McRae has been called the teen dance star turned future pop idol by i-D. She's praised by artists like Paula Abdul, who called her a gift from God, and choreographers like Stacey Tookey, Blake McGrath, and Travis Wall, who named her one of his muses";
            artist = new Artist("Tate McRae", 45652467, imageService.remoteImageToBlob("https://i.postimg.cc/dhppH6Tb/Tate-Mc-Rae.jpg"), "/api/artists/1/photo", mainText, extText, "https://open.spotify.com/intl-es/album/0OUOx6rJXtL66AzTnP9KUE", "https://open.spotify.com/intl-es/album/3w32SV56JvtJXsrYtThwzP", "https://www.youtube.com/embed/rwlFWWGaZ5Y");

            artistRepository.save(artist);

            //Save Tate's Concerts
            concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.MAY, 9, 21, 0), "Europe", "Tate's First Arena Concert Ever", 60, 30, 30, 30, 60, imageService.remoteImageToBlob("https://i.postimg.cc/vDyFw0SM/MPT-poster.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.AUGUST, 22, 21, 0), "America", "Tate's First Arena Concert Ever", 70, 50, 50, 50, 80, imageService.remoteImageToBlob("https://i.postimg.cc/vDyFw0SM/MPT-poster.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Miss Possessive Tour", LocalDateTime.of(2025, Month.SEPTEMBER, 13, 21, 0), "America", "Tate's First Arena Concert Ever", 80, 70, 70, 70, 100, imageService.remoteImageToBlob("https://i.postimg.cc/vDyFw0SM/MPT-poster.jpg"));
            concertRepository.save(concert);

            //Save Artist: Sabrina Carpenter
            mainText = "Sabrina Carpenter, singer, songwriter, and actress born in Pennsylvania (USA) on May 11, 1999, gained fame with Nonsense (2022). She released hits like Sue Me, In My Bed, and Taste. Her latest album, Short n' Sweet, features the single Espresso";
            extText = "Sabrina moved to Island Records in 2021 and released Emails I Can't Send (2022). She opened for Taylor Swift on the Eras Tour (2023) and released Short n' Sweet (2024), topping the Billboard 200. She's starred in TV movies and Broadway musicals.";
            artist = new Artist("Sabrina Carpenter", 72520327, imageService.remoteImageToBlob("https://i.postimg.cc/PCP0Q9Nb/Sabrina-Carpenter.jpg"), "/api/artists/2/photo", mainText, extText, "https://open.spotify.com/intl-es/album/29mlGxS6kxq1EHxlX1EAZK", "https://open.spotify.com/intl-es/album/3iPSVi54hsacKKl1xIR2eH", "https://www.youtube.com/embed/eVli-tstM5E");

            artistRepository.save(artist);

            //Save Sabrina's Concerts
            concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 3, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.remoteImageToBlob("https://i.postimg.cc/WqNHFfQk/SNS-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 13, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.remoteImageToBlob("https://i.postimg.cc/WqNHFfQk/SNS-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Short n' Sweet Tour", LocalDateTime.of(2025, Month.MARCH, 23, 21, 0), "Europe", "Europe leg of the Short n' Sweet Tour", 80, 40, 40, 40, 70, imageService.remoteImageToBlob("https://i.postimg.cc/WqNHFfQk/SNS-Tour.jpg"));
            concertRepository.save(concert);

            //Save Artist: Gracie Abrams
            mainText = "Gracie Abrams, born on September 7, 1999, is an American singer and songwriter, daughter of director J.J. Abrams. She debuted with the EP Minor (2020) and her discography includes Good Riddance (2023) and The Secret of Us (2024)";
            extText = "Abrams cites influences like Bon Iver, Elvis Costello, Taylor Swift, Lorde and Phoebe Bridgers. Artists like Olivia Rodrigo, Billie Eilish and Post Malone admire her, with Rodrigo revealing that Abrams' Minor inspired her hit Drivers License.";
            artist = new Artist("Gracie Abrams", 50811132, imageService.remoteImageToBlob("https://i.postimg.cc/R3JGWzh8/Gracie-Abrams.jpg"), "/api/artists/3/photo", mainText, extText, "https://open.spotify.com/intl-es/album/78YYcghEDz2dHRx0EcDGXZ", "https://open.spotify.com/intl-es/album/56bdWeO40o3WfAD2Lja4dl", "https://www.youtube.com/embed/uxjhN_Donfw");

            artistRepository.save(artist);

            //Save Gracie's Concerts
            concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MARCH, 3, 21, 0), "Europe", "Europe leg of the The Secret Of Us Tour", 75, 30, 30, 30, 60, imageService.remoteImageToBlob("https://i.postimg.cc/Ppz7qkFH/TSOU-Tour-Europe.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MARCH, 10, 21, 0), "Europe", "Europe leg of the The Secret Of Us Tour", 75, 30, 30, 30, 60, imageService.remoteImageToBlob("https://i.postimg.cc/Ppz7qkFH/TSOU-Tour-Europe.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MAY, 3, 21, 0), "Oceania", "Oceania leg of the The Secret Of Us Tour", 80, 40, 40, 40, 50, imageService.remoteImageToBlob("https://i.postimg.cc/Ppz7qkFH/TSOU-Tour-Oceania.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Secret Of Us Tour", LocalDateTime.of(2025, Month.MAY, 17, 21, 0), "Oceania", "Oceania leg of the The Secret Of Us Tour", 80, 40, 40, 40, 50, imageService.remoteImageToBlob("https://i.postimg.cc/Ppz7qkFH/TSOU-Tour-Oceania.jpg"));
            concertRepository.save(concert);

            // Create and Save Artist: Katy Perry
            mainText = "Katy Perry is an American singer, songwriter and actress, best known for her catchy pop songs and colorful style. She rose to fame with her hit \"I Kissed a Girl\", followed by chart-topping singles like \"Firework,\" \"Teenage Dream,\" \"Roar,\" or \"Dark Horse.\"";
            extText = "Katy Perry is renowned for her vibrant persona, elaborate stage productions, and empowering anthems. Over her career, she has received numerous awards, including American Music Awards and Billboard Music Awards.";
            artist = new Artist("Katy Perry", 60637611, imageService.remoteImageToBlob("https://i.postimg.cc/gxTNc0z8/Katy-Perry.jpg"), "/api/artists/4/photo", mainText, extText, "https://open.spotify.com/intl-es/album/06SY6Ke6mXzZHhURLVU57R", "https://open.spotify.com/intl-es/album/3jxt1S4JtW4uFalBwlfehS", "https://www.youtube.com/embed/EVIJUH29pjU");

            artistRepository.save(artist);

            // Create and Save Katy Perry's concerts
            concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.NOVEMBER, 11, 21, 0), "Europe", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 60, 100, 100, 100, 200, imageService.remoteImageToBlob("https://i.postimg.cc/HjzKDdy4/The-Lifetimes-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.AUGUST, 11, 19, 0), "America", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 90, 150, 150, 150, 300, imageService.remoteImageToBlob("https://i.postimg.cc/HjzKDdy4/The-Lifetimes-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Lifetimes Tour", LocalDateTime.of(2025, Month.JUNE, 9, 20, 0), "Oceania", "The Lifetimes Tour is the upcoming fifth concert tour by American singer Katy Perry, following her seventh studio album, 143 (2024).", 60, 100, 100, 100, 200, imageService.remoteImageToBlob("https://i.postimg.cc/HjzKDdy4/The-Lifetimes-Tour.jpg"));
            concertRepository.save(concert);

            // Create and Save Artist: Beyonce
            mainText = "Beyonce is a globally renowned singer, songwriter, actress, and businesswoman. Known for her powerful vocals, electrifying performances, and hits like Halo and Single Ladies, she has won numerous awards, including Grammys.";
            extText = "A trailblazer in music and fashion, Beyoncé blends R&B, pop, and hip-hop with unmatched artistry. Beyond music, she champions social justice, female empowerment, and Black excellence worldwide.";
            artist = new Artist("Beyonce", 60143515, imageService.remoteImageToBlob("https://i.postimg.cc/14GC0PB1/Beyonce.jpg"), "/api/artists/5/photo", mainText, extText, "https://open.spotify.com/intl-es/album/6FJxoadUE4JNVwWHghBwnb", "https://open.spotify.com/intl-es/album/6BzxX6zkDsYKFJ04ziU5xQ", "https://www.youtube.com/embed/UL_JXt4FI6E");

            artistRepository.save(artist);

            // Create and Save Beyonce's concerts
            concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.MAY, 15, 19, 0), "America", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 120, 80, 80, 80, 150, imageService.remoteImageToBlob("https://i.postimg.cc/yk7fv5pg/Cowboy-Carter-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.JUNE, 14, 17, 0), "Europe", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 100, 90, 90, 90, 200, imageService.remoteImageToBlob("https://i.postimg.cc/yk7fv5pg/Cowboy-Carter-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "Cowboy Carter Tour", LocalDateTime.of(2025, Month.MAY, 4, 19, 0), "America", "Beyoncé's Cowboy Carter Tour blends country, R&B, and pop in a groundbreaking celebration of Western culture, Black artistry, and musical evolution.", 120, 80, 80, 80, 150, imageService.remoteImageToBlob("https://i.postimg.cc/yk7fv5pg/Cowboy-Carter-Tour.jpg"));
            concertRepository.save(concert);

            // Create and Save Artist: Taylor Swift
            mainText = "Taylor Swift is a record-breaking singer-songwriter known for her storytelling, genre versatility, and hits like Love Story and Anti-Hero. From country roots to pop, she dominates music charts, wins countless awards, and influences culture worldwide.";
            extText = "A master of reinvention, Taylor Swift crafts deeply personal lyrics that resonate globally. With The Eras Tour, she celebrates her evolution. Beyond music, she’s a business mogul, advocate for artists’ rights, and a voice for social change.";
            artist = new Artist("Taylor Swift", 86426795, imageService.remoteImageToBlob("https://i.postimg.cc/5HzGZzB6/Taylor-Swift.jpg"), "/api/artists/6/photo", mainText, extText, "https://open.spotify.com/intl-es/album/64LU4c1nfjz1t4VnGhagcg", "https://open.spotify.com/intl-es/album/1Mo4aZ8pdj6L1jx8zSwJnt", "https://www.youtube.com/embed/q3zqJs7JUCQ");

            artistRepository.save(artist);

            // Create and Save Taylor's concerts
            concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.MAY, 30, 20, 0), "Europe", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 220, 0, 0, 0, 0, imageService.remoteImageToBlob("https://i.postimg.cc/XG0h3vLx/The-Eras-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.OCTOBER, 18, 17, 0), "America", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 320, 0, 0, 0, 0, imageService.remoteImageToBlob("https://i.postimg.cc/XG0h3vLx/The-Eras-Tour.jpg"));
            concertRepository.save(concert);

            concert = new Concert(artist, "The Eras Tour", LocalDateTime.of(2024, Month.FEBRUARY, 7, 18, 0), "Asia", "Taylor Swift’s Eras Tour is a career-spanning spectacle, celebrating her musical journey with dazzling visuals, emotional storytelling, and record-breaking performances.", 100, 0, 0, 0, 0, imageService.remoteImageToBlob("https://i.postimg.cc/XG0h3vLx/The-Eras-Tour.jpg"));
            concertRepository.save(concert);

            // Create and Save Artist: Rosalia
            mainText = "Rosalía is a Spanish singer and songwriter who blends flamenco with pop, reggaeton, and electronic sounds. Known for her powerful vocals, avant-garde style, and bold visuals, she’s reshaping global music with a fresh and fearless approach.";
            extText = "Beyond her genre-defying sound, Rosalía is celebrated for her striking fashion, conceptual performances, and deep cultural references. Her work bridges tradition and modernity, earning her critical acclaim, Grammy awards, and a global fanbase.";
            artist = new Artist("Rosalia", 21803367, imageService.remoteImageToBlob("https://i.postimg.cc/SJZw8kQN/Rosalia.jpg"), "/api/artists/7/photo", mainText, extText, "https://open.spotify.com/intl-es/album/355bjCHzRJztCzaG5Za4gq", "https://open.spotify.com/intl-es/album/6jbtHi5R0jMXoliU2OS0lo", "https://www.youtube.com/embed/e-CEd6xrRQc");

            artistRepository.save(artist);

            // Create and Save Rosalia's concerts
            concert = new Concert(artist, "Motomami Tour", LocalDateTime.of(2022, Month.JUNE, 19, 22, 0), "Europe", "The Motomami World Tour was Rosalía’s third concert tour, supporting her 2022 album Motomami. Spanning 68 shows across 21 countries, it showcased her bold artistry and drew nearly two million fans across three continents", 150, 0, 0, 0, 0, imageService.remoteImageToBlob("https://i.postimg.cc/T5fcNL0M/Motomami-World-Tour.jpg"));
            concertRepository.save(concert);

            // Create and Save Artist: Ariana Grande
            mainText = "Global pop powerhouse with a soaring vocal range and instantly recognizable high ponytail. From teen idol to chart-topping superstar, delivering infectious hits and undeniable charisma.";
            extText = "Award-winning singer and songwriter known for anthems like \"thank u, next\" and \"7 rings.\" A vocal advocate for animal rights and mental health awareness.";
            artist = new Artist("Ariana Grande", 82801462, imageService.remoteImageToBlob("https://i.postimg.cc/FYRTQS4h/Ariana-Grande.jpg"), "/api/artists/8/photo", mainText, extText, "https://open.spotify.com/intl-es/album/3tx8gQqWbGwqIGZHqDNrGe", "https://open.spotify.com/intl-es/album/5EYKrEDnKhhcNxGedaRQeK", "https://www.youtube.com/embed/9WbCfHutDSE");

            artistRepository.save(artist);

            // Create and Save Artist: Miley Cyrus
            mainText = "Boundary-pushing artist with a powerful, versatile voice. From pop anthems to rock grit, she constantly reinvents herself with raw honesty and bold expression.";
            extText = "Singer, songwriter, and actress. Known for hits like \"Wrecking Ball\" and \"Flowers.\" A fearless and ever-evolving force in music and pop culture.";
            artist = new Artist("Miley Cyrus", 58072460, imageService.remoteImageToBlob("https://i.postimg.cc/4m4DmmgS/Miley-Cyrus.jpg"), "/api/artists/9/photo", mainText, extText, "https://open.spotify.com/intl-es/album/0IuHVgAvbNDJnJepuSZ8Oz", "https://open.spotify.com/intl-es/album/5DvJgsMLbaR1HmAI6VhfcQ", "https://www.youtube.com/embed/My2FRPA3Gf8");

            artistRepository.save(artist);

            // Create and Save Artist: Nicki Minaj
            mainText = "Nicki Minaj is a highly influential rapper, singer, and songwriter recognized for her complex lyrical skills, distinctive flow, and significant impact on contemporary music.";
            extText = "Her extensive discography showcases a range of styles, from hardcore hip-hop to pop-infused tracks, solidifying her position as a multifaceted global artist.";
            artist = new Artist("Nicki Minaj", 45598260, imageService.remoteImageToBlob("https://i.postimg.cc/V5FyfX3t/Nicki-Minaj.jpg"), "/api/artists/10/photo", mainText, extText, "https://open.spotify.com/intl-es/album/22F5ZYY1sxoJjk6HzZfmC1", "https://open.spotify.com/intl-es/album/78H3My21k0xQ72zYFpdrEa", "https://www.youtube.com/embed/SeIJmciN8mo");

            artistRepository.save(artist);

            // Create and Save Artist: Lady Gaga
            mainText = "Lady Gaga is a globally acclaimed singer-songwriter, actress, and cultural icon known for her innovative artistry, powerful vocals, and transformative approach to pop music.";
            extText = "Her work spans diverse genres, from electronic dance-pop to jazz and rock, marked by theatrical performances and a consistent exploration of identity and societal themes.";
            artist = new Artist("Lady Gaga", 113507077, imageService.remoteImageToBlob("https://i.postimg.cc/XBLkRj1Z/LadyGaga.jpg"), "/api/artists/11/photo", mainText, extText, "https://open.spotify.com/intl-es/album/1jpUMnKpRlng1OJN7LJauV", "https://open.spotify.com/intl-es/album/2MHUaRi9OCyTN02SoyRRBJ", "https://www.youtube.com/embed/vBynw9Isr28");

            artistRepository.save(artist);

            // Create and Save Artist: Adele
            mainText = "Adele is a celebrated British singer-songwriter renowned for her powerful mezzo-soprano vocals, emotionally resonant lyrics, and significant impact on popular music.";
            extText = "Her critically acclaimed albums explore themes of love, loss, and motherhood, marked by soulful performances and global commercial success.";
            artist = new Artist("Adele", 56944202, imageService.remoteImageToBlob("https://i.postimg.cc/XBksncqY/Adele.jpg"), "/api/artists/12/photo", mainText, extText, "https://open.spotify.com/intl-es/album/5duyQokC4FMcWPYTV9Gpf9", "https://open.spotify.com/intl-es/album/21jF5jlMtzo94wbxmJ18aa", "https://www.youtube.com/embed/YQHsXMglC9A");

            artistRepository.save(artist);

            // Create and Save Artist: Charli XCX
            mainText = "Charli XCX is a forward-thinking British singer-songwriter and producer known for her experimental approach to pop music, blending electronic sounds with catchy melodies.";
            extText = "Her work often explores themes of technology, futurism, and female empowerment, characterized by collaborations and a distinct, innovative sound.";
            artist = new Artist("Charli XCX", 31538661, imageService.remoteImageToBlob("https://i.postimg.cc/jLvM9XB8/Charli-XCX.jpg"), "/api/artists/13/photo", mainText, extText, "https://open.spotify.com/intl-es/album/2lIZef4lzdvZkiiCzvPKj7", "https://open.spotify.com/intl-es/album/0W5woeQnfOZmVLSbggRRlR", "https://www.youtube.com/embed/huGd4efgdPA");

            artistRepository.save(artist);

            // Create and Save Artist: Chappell Roan
            mainText = "Chappell Roan is an emerging American singer-songwriter known for her vibrant stage presence, anthemic pop songs, and exploration of identity and self-expression.";
            extText = "Her music blends nostalgic pop influences with bold, theatrical aesthetics, creating a unique and captivating artistic persona.";
            artist = new Artist("Chappell Roan", 45118458, imageService.remoteImageToBlob("https://i.postimg.cc/gXvDKqvH/Chappell-Roan.jpg"), "/api/artists/14/photo", mainText, extText, "https://open.spotify.com/intl-es/album/0EiI8ylL0FmWWpgHVTsZjZ", "https://open.spotify.com/intl-es/album/0EiI8ylL0FmWWpgHVTsZjZ", "https://www.youtube.com/embed/6ENzV125lWc");

            artistRepository.save(artist);

            // Create and Save Artist: Doja Cat
            mainText = "Doja Cat is a versatile American singer, rapper, and songwriter recognized for her genre-bending music, incorporating elements of pop, hip-hop, R&B, and electronic music.";
            extText = "Her work is characterized by catchy hooks, playful lyrics, and a dynamic online presence, contributing to her widespread popularity and influence.";
            artist = new Artist("Doja Cat", 55574402, imageService.remoteImageToBlob("https://i.postimg.cc/gX9sRB60/DojaCat.jpg"), "/api/artists/15/photo", mainText, extText, "https://open.spotify.com/intl-es/album/1nAQbHeOWTfQzbOoFrvndW", "https://open.spotify.com/intl-es/album/5ho4S2bnJJdF0uwT8YiQsw", "https://www.youtube.com/embed/m4_9TFeMfJE");

            artistRepository.save(artist);

            //Create Users
            user = new UserEntity("armiiin13", passwordEncoder.encode("eras1325"), "armiingrc@yahoo.com", "Europe", "USER","ADMIN");
            userRepository.save(user);
            user = new UserEntity("Fonssi29", passwordEncoder.encode("pollitoPio"), "fonssi@gmail.com", "America", "USER","ADMIN");
            this.userRepository.save(user);
            user = new UserEntity("davih", passwordEncoder.encode("davilico"), "drg@gmail.com", "Europe", "USER","ADMIN");
            userRepository.save(user);
        }
    }
}
