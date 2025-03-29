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

import es.ticketmaster.entrega1.dto.user.ShowUserDTO;
import es.ticketmaster.entrega1.dto.user.UserDTO;
import es.ticketmaster.entrega1.dto.user.UserMapper;
import es.ticketmaster.entrega1.dto.user.UserShowTicketsDTO;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.repository.UserRepository;
import es.ticketmaster.entrega1.service.exceptions.UserAlreadyExistsException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * Method that tries to make the registration of a new user. For that, it searches if
     * there is an existing user with that username and in case there is no repetition,
     * saves the new user in the database.
     *
     * @param newUser is the DTO correspondig to the user.
     * @return true if the user is successfully registered, false if the username is already taken.
     */
    public boolean registerUser(UserDTO newUser) {
        try {
            // Verifies if there is a user with the same username.
            if(this.userRepository.findByUsername(newUser.username()).isPresent()){
                return false;
            }
            UserEntity user = this.userMapper.toDomain(newUser);
            user.setPassword(encoder.encode(newUser.password()));
            this.userRepository.save(user);
            this.userMapper.toPrincipalDTO(user);
            return true;
        } 
        catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
    }

    /**
     * Gets the actual active user object from the Principal user provided by SpringSecurity.
     *
     * @param principal the principal class from where the username can be requested.
     * @return the actual active user as ShowUserDTO, or null if no user is found.
     */
    public ShowUserDTO getActiveUser(Principal principal) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(principal.getName());
        Optional<ShowUserDTO> showUserDTO = userEntity.map(this.userMapper :: toShowUserDTO);

        if (showUserDTO.isEmpty()) {
            return null;
        } 
        else {
            return showUserDTO.get();
        }
    }

    /**
     * Gets the actual active user object from the Principal user provided by SpringSecurity.
     * @implNote This method is for exclusive use by ImageController.
     *
     * @param principal the principal class from where the username can be requested.
     * @return the actual active user.
     */
    public UserEntity getActiveUserWithProfilePicture(Principal principal) {

        Optional<UserEntity> user = userRepository.findByUsername(principal.getName());

        if (user.isEmpty()) {
            return null;
        } else {
            return user.get();
        }
    }

    /**
     * Gets the all the tickets associated with the actual active user object from the Principal user provided by SpringSecurity.
     *
     * @param principal the principal class from where the username can be requested.
     * @return the actual active user as UserShowTicketsDTO, or null if no user is found.
     */
    public UserShowTicketsDTO getTicketsForActiveUser(Principal principal) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(principal.getName());
        Optional<UserShowTicketsDTO> userShowTicketsDTO = userEntity.map(this.userMapper :: toShowTicketsDTO);

        if (userShowTicketsDTO.isEmpty()) {
            return null;
        } 
        else {
            return userShowTicketsDTO.get();
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