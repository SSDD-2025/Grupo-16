package es.ticketmaster.entrega1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ticketmaster.entrega1.model.Concert;

/**
 * Repository of Concerts containing methods declaration used for querys in the DataBase
 */
public interface ConcertRepository extends JpaRepository<Concert, Long>{
    /** Makes a list containing one concert form each artist and they are ordered by the artist popularityIndex
      * @return the specified list
      */
    /* @Query(
        value = """
            SELECT c.id AS concert_id, a.id as artist_id, c.*, a.* 
                FROM Concert c
                    JOIN Artist a ON c.artist_id = a.id 
                        WHERE c.id = (SELECT MAX(c2.id) FROM Concert c2 WHERE c2.artist_id = c.artist_id) 
                            ORDER BY a.popularity_index DESC;
        """
        , 
        nativeQuery = true)*/
    //public List<Concert> getConcertByDistinctArtistOrderByArtistPopularityIndexDesc();

    /** Searches the concerts taken at a specific place
      * @param place is the country/ city which the search is based
      * @return the list of the concerts taking place at the country/ city specified
      */
    public List<Concert> getConcertByPlace(String place);

    public List<Concert> findByNameContainingIgnoreCase(String search);

    public List<Concert> findByArtistNameContainingIgnoreCase(String search);
}
