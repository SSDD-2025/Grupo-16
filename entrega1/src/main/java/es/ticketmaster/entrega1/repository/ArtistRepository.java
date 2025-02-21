package es.ticketmaster.entrega1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ticketmaster.entrega1.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist,Long>{
    
    /** Makes a list of the 10 most popular artists (according to their popularityIndex) 
     * @return the specified list
    */
    public List<Artist> findTop10ByOrderByPopularityIndexDesc();

    public List<Artist> findByNameContainingIgnoreCase(String search);
}
