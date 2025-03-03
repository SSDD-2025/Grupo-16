package es.ticketmaster.entrega1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ticketmaster.entrega1.model.UserEntity;

/* This will be the repository that will manage all the information regarding all the register users in the application. */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>  {
    public UserEntity findByUserName(String userName);
    public UserEntity findByUserNameAndPassword(String userName, String password);
    public Optional<UserEntity> findById(long id);
}