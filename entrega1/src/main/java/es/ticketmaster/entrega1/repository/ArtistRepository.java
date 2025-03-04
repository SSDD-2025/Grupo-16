package es.ticketmaster.entrega1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ticketmaster.entrega1.model.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    /**
     * Makes a list of the 10 most popular artists (according to their
     * popularityIndex)
     *
     * @return the specified list
     */
    public List<Artist> findTop10ByOrderByPopularityIndexDesc();

    public Optional<Artist> findFirstByName(String name);

    public Optional<Artist> findFirstByNameIgnoreCase(String name);

    /**
     * Makes a list of the 10 last artist to log in
     *
     * @return the specified list
     */
    public List<Artist> findTop10ByOrderBySessionCreatedDesc();

    /**
     * Makes a list of the artists whose name contains the string search
     *
     * @param search substring to be searched inside the artists names
     * @return the specified list
     */
    public List<Artist> findByNameContainingIgnoreCase(String search);

    /**
     * Deletes from the repository the Artist with the given ID
     *
     * @param id Artist's id
     */
    public void deleteById(long id);

}
