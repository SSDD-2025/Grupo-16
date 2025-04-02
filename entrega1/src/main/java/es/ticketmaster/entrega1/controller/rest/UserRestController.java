package es.ticketmaster.entrega1.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.ticketmaster.entrega1.dto.user.ShowUserDTO;
import es.ticketmaster.entrega1.dto.user.UserDTO;
import es.ticketmaster.entrega1.service.UserService;
import es.ticketmaster.entrega1.service.exceptions.GlobalExceptionHandler;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    private static final String BAD_REQUEST_MESSAGE = "The user already exists.";

    /**
     * Verifies if the provided user information is valid and attempts to register the user.
     * If the registration is successful, a new resource is created, and the location of the created resource is returned.
     * If the user already exists, a BAD_REQUEST response is returned.
     * 
     * @param userDTO represents a determine user in DTO format.
     * @return A ResponseEntity object representing the response:
     *         - If successful: A 201 Created response with the URI of the newly created user and the user details.
     *         - If failure (user already exists): A 400 Bad Request response, indicating that the user already exists.
     */
    @PostMapping("/register/")
    public ResponseEntity<Object> verifySignUpREST(@RequestBody UserDTO userDTO) {
        if (this.userService.registerUser(userDTO)) {
            URI location = fromCurrentRequest().path("/profile/{id}/").buildAndExpand(userDTO.id()).toUri();
            return ResponseEntity.created(location).body(userDTO);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BAD_REQUEST_MESSAGE);
        }
    }

    /**
     * Retrieves the profile information of the active user.
     * 
     * @see GlobalExceptionHandler#UserNotFound(UserNotFound)
     * @param principal the principal class from where the username can be requested.
     * @return A ResponseEntity containing:
     *              - 200 OK and the user details in a ShowUserDTO format.
     *              - 404 Not Found if there is no user with the given id.
     */
    @GetMapping("/profile/")
    public ResponseEntity<ShowUserDTO> accesToProfileREST(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            long id = this.userService.getIdOfUser(principal.getName());
            return ResponseEntity.ok(this.userService.getUserWithID(id));
        }   
    }

    /**
     * Retrieves the information for all registered users.
     * 
     * @return the collection of users in the ShowUserDTO format.
     */
    @GetMapping("/all-profiles/")
    public Collection<ShowUserDTO> getAllUsers() {
        return this.userService.getAllUsersFromDatabase();
    }
    
    /**
     * This method handles the HTTP PUT request to update the the active user profile settings.
     * In this case, the profile picture is not being updated as the method is provided with `null` for it.
     * It only update the user´s country.
     * 
     * @param principal the principal class from where the username can be requested.
     * @param user is the updated user data in ShowUserDTO format.
     * @return A ResponseEntity containing:
     *          - 401 UNAUTHORIZED if the principal is null.
     *          - 200 OK and the updated user details if the update is successful.
     *          - 404 Not Found if no user exists with the given ID.
     *          - 400 Bad Request if an IOException occurs while handling the request.
     *          - 500 Internal Server Error if an unexpected error occurs.
     */
    @PutMapping("/modify-profile/")
    public ResponseEntity<ShowUserDTO> changeUserSettingsREST(Principal principal, @RequestBody ShowUserDTO user) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            try {
                long id = this.userService.getIdOfUser(principal.getName());
                if (this.userService.saveUserWithId(id, user.country(), null)) {
                    ShowUserDTO updatedUser = this.userService.getUserWithID(id);
                    return ResponseEntity.ok(updatedUser);
                } 
                else {
                    return ResponseEntity.notFound().build();
                }
            }
            catch (IOException e) {
                return ResponseEntity.badRequest().build();        
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
    
    /**
     * This method attempts to delete the profile of the active user.
     * 
     * @param principal the principal class from where the username can be requested.
     * @return A ResponseEntity with:
     *           - 200 OK if the user was successfully deleted.
     *           - 404 Not Found if the user does not exist.
     */
    @DeleteMapping("/delete-profile/")
    public ResponseEntity<ShowUserDTO> deleteUserProfileREST(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else {
            long id = this.userService.getIdOfUser(principal.getName());
            if (this.userService.removeExistingUserWithId(id)) {
                return ResponseEntity.ok(this.userService.getUserWithID(id));
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
    }
}