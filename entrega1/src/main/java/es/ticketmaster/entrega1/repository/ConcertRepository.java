package es.ticketmaster.entrega1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ticketmaster.entrega1.model.Concert;

/**
 * Repository of Concerts containing methods declaration used for querys in the DataBase
 */
public interface ConcertRepository extends JpaRepository<Concert, Long>{
    
}
