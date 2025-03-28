package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Method that tries to make the registration of a new user. For that, it searches if
     * there is an existing user with that username and in case there is no repetition,
     * saves the new user in the database.
     *
     * @param newUser is the user that will be register.
     * @return the user (in case everything goes well).
     */
    public boolean registerUser(UserEntity newUser) {
        try {
            // Verifies if there exist any user with the same username
            if(this.userRepository.findByUsername(newUser.getUsername()).isPresent()){
                return false;
            }
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            this.userRepository.save(newUser);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets the actual active user object from the Principal user provided by SpringSecurity
     *
     * @param principal the principal class from where the username can be requested.
     * @return the actual active user
     */
    public UserEntity getActiveUser(Principal principal) {

        Optional<UserEntity> user = userRepository.findByUsername(principal.getName());

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
     * (TEMPORALY UNAVAILABLE)
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
            //activeUser.setUserAsNotActive();
            return !userRepository.existsById(id); //We return true if the artist has been correctly deleted
        }

    }
}