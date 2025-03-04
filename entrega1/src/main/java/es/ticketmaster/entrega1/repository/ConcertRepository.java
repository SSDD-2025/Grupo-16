package es.ticketmaster.entrega1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.ticketmaster.entrega1.model.Concert;
import jakarta.transaction.Transactional;

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
    public List<Concert> findByPlace(String place);

    /**
     * Returns the 7 earliest concerts to display on the main page (when anon user is navigating the web)
     * @return a list containing said concerts
     */
    public List<Concert> findTop7ByOrderByDateAsc();

    /**
     * Searches the concerts which name matches (contains) the introduced parameter
     * @param search searching parameter
     * @return List of Concerts that match
     */
    public List<Concert> findByNameContainingIgnoreCaseOrderByArtistPopularityIndexDesc(String search);

    /**
     * Searches the concerts which artist name exactly matches the introduced parameter
     * @param artistName searching parameter
     * @return List of Concerts that match
     */
    public List<Concert> findByArtistNameIgnoreCase(String artistName);

    /**
     * Searches the concerts which artist name matches (contains) the introduced parameter
     * @param search searching parameter
     * @return List of Concerts that match
     */
    public List<Concert> findByArtistNameContainingIgnoreCase(String search);

    /**
     * Tries to find a concert which id matches with the parameter introduced
     * @param id searching parameter id
     * @return Concert which id matches
     */
    public Concert findConcertById(long id);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on controlling the number of available tickets for the westStands section.
     * By doing this, the concurrent access to the the tickets that the different users can purchase is controlled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno. 
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user will be purchasing.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 as I mentioned previosly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.westStandsNumber=c.westStandsNumber-:number WHERE c.id = :id AND c.westStandsNumber-:number >= 0")
    public int availableWestStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on controlling the number of available tickets for the eastStands section.
     * By doing this, the concurrent access to the the tickets that the different users can purchase is controlled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user will be purchasing.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 as I mentioned previosly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.eastStandsNumber=c.eastStandsNumber-:number WHERE c.id = :id AND c.eastStandsNumber-:number >= 0")
    public int availableEastStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on controlling the number of available tickets for the northStands section.
     * By doing this, the concurrent access to the the tickets that the different users can purchase is controlled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user will be purchasing.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 as I mentioned previosly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.northStandsNumber=c.northStandsNumber-:number WHERE c.id = :id AND c.northStandsNumber-:number >= 0")
    public int availableNorthStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on controlling the number of available tickets for the generalAdmission section.
     * By doing this, the concurrent access to the the tickets that the different users can purchase is controlled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno. 
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user will be purchasing.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 as I mentioned previosly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.generalAdmissionNumber=c.generalAdmissionNumber-:number WHERE c.id = :id AND c.generalAdmissionNumber-:number >= 0")
    public int availableGeneralAdmissionTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on updating the number of the westStands tickets available if the user cancel de purchase.
     * In other words, this query will be in charge of restoring the number of westStands tickets selected by the user,
     * when the purchase has been canceled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user has selected.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 ass I mentioned previously.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.westStandsNumber=c.westStandsNumber+:number WHERE c.id = :id")
    public int restoreWestStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on updating the number of the eastStands tickets available if the user cancel de purchase.
     * In other words, this query will be in charge of restoring the number of eastStands tickets selected by the user,
     * when the purchase has been canceled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user has selected.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 ass I mentioned previously.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.eastStandsNumber=c.eastStandsNumber+:number WHERE c.id = :id")
    public int restoreEastStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on updating the number of the northStands tickets available if the user cancel de purchase.
     * In other words, this query will be in charge of restoring the number of northStands tickets selected by the user,
     * when the purchase has been canceled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user has selected.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 ass I mentioned previously.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.northStandsNumber=c.northStandsNumber+:number WHERE c.id = :id")
    public int restoreNorthStandsTickets(@Param ("id") long id, @Param("number") int number);

    /**
     * This query will return the number of rows udpated. In this case, the possible values will be 1 or 0, since only 1 row can be updated.
     * In specific, this query is focus on updating the number of the generalAdmission tickets available if the user cancel de purchase.
     * In other words, this query will be in charge of restoring the number of generalAdmission tickets selected by the user,
     * when the purchase has been canceled.
     * @author Alfonso Rodríguez Gutt and Arminda García Moreno.
     * @param id is the identification number for the concert.
     * @param number is the ammount of tickets that the user has selected.
     * @return the number of rows that will be updated. In this case, should be 1 or 0 ass I mentioned previously.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Concert c SET c.generalAdmissionNumber=c.generalAdmissionNumber+:number WHERE c.id = :id")
    public int restoreGeneralAdmissionTickets(@Param ("id") long id, @Param("number") int number);
}