package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.ActiveUser;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActiveUser activeUser;

    /**
     * This method will verify if the userName matches with one register on the
     * database.
     *
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

    /**
     * This method will verify if the password introduced by the user, is the
     * same one he used when the account was created.
     *
     * @param userName introduced by the user at the time he try to log in.
     * @param password introduced by the user at the time he try to log in.
     */
    public boolean verifyPassword(String userName, String password) {
        UserEntity user = this.verifyUser(userName);
        return password.equals(user.getPassword());
    }

    /**
     * This method will save a new user in the database, and in case of error,
     * it will be managed.
     *
     * @param newUser is the user that will be register.
     * @return the user (in case everything goes well).
     */
    public UserEntity registerUser(UserEntity newUser) {
        try {
            activeUser.setNewActiveUser(newUser);
            return this.userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * This method will get the session of the user by getting the corresponding
     * user from the database.
     *
     * @param userName.
     * @param password
     * @return the user recovered.
     */
    public UserEntity recoverUser(String userName, String password) {
        return this.userRepository.findByUserNameAndPassword(userName, password);
    }

    /**
     *
     * @return true if the active user at the session is logged or not
     */
    public boolean isLogged() {
        return (activeUser.isUserLogged());
    }

    /**
     * Gets the actual active user object from the DDBB using its id
     *
     * @return the actual active user
     */
    public UserEntity getActiveUser() {

        Optional<UserEntity> user = userRepository.findById(activeUser.getId());

        if (user.isEmpty()) {
            return null;
        } else {
            return user.get();
        }
    }

    /**
     * Saves in the DDBB the user whose id is passed as a parameter and changing
     * its country and photo in case they are not null
     *
     * @param id the user id
     * @param country new country to be changed
     * @param newPhoto new photo to be changed
     * @throws IOException
     */
    public boolean saveUserWithId(long id, String country, MultipartFile newPhoto) throws IOException {

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return false;
        } else {
            if (country != null) {
                user.get().setCountry(country);
            }
            if (!newPhoto.isEmpty()) {
                user.get().setProfilePicture(BlobProxy.generateProxy(newPhoto.getInputStream(), newPhoto.getSize()));
            }
            this.userRepository.save(user.get());
            return true;
        }
    }

    /**
     * Method that, provided with an ID, handles the deletion of an user with
     * the specified id. For that, it is checked if the deletion has been
     * successful or not, searching if an user with the given ID exists after
     * the deletion. Trying to delete a non-existant user is also considered an
     * unsuccessful situation.
     *
     * @param id the User's ID
     * @return if the deletion has been completed successfully
     */
    public boolean removeExistingUserWithId(long id) {

        if (!userRepository.existsById(id)) { //If an user with such ID does not exist
            return false;
        } else {
            userRepository.deleteById(id); //We delete the artist with that ID
            activeUser.setUserAsNotActive();
            return !userRepository.existsById(id); //We return true if the artist has been correctly deleted
        }

    }

}
