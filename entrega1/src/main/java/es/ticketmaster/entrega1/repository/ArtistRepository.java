package es.ticketmaster.entrega1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ticketmaster.entrega1.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist,Long>{
    
}
