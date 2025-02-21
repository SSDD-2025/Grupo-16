package es.ticketmaster.entrega1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /** This method will verify if the userName matches with one register on the database. 
    * @param userName introduced by the user at the time he try to log in.
    * @return the user (if exists).
    */
    public UserEntity verifyUser(String userName) {
        UserEntity existingUser = this.userRepository.findByUserName(userName);
        if (existingUser != null) {
            return existingUser;
        }
        return null;
    }

    /** This method will verify if the password introduced by the user, is the same one he used when the account was created. 
    * @param userName introduced by the user at the time he try to log in.
    * @param password introduced by the user at the time he try to log in.
    */
    public boolean verifyPassword(String userName, String password) {
        UserEntity user = this.verifyUser(userName);
        return password.equals(user.getPassword());
    }

    /** This method will save a new user in the database, and in case of error, it will be managed.
    * @param newUser is the user that will be register.
    * @return the user (in case everything goes well).
    */
    public UserEntity registerUser(UserEntity newUser) {
        try {
            return this.userRepository.save(newUser);
        } 
        catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /** This method will get the session of the user by getting the corresponding user from the database.
    * @param userName.
    * @param password
    * @return the user recovered.
    */
    public UserEntity recoverUser(String userName, String password) {
        return this.userRepository.findByUserNameAndPassword(userName, password);
    }

    public boolean isLogged(UserEntity activeUser){
        return (activeUser != null);
    }
}