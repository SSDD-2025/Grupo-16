package es.ticketmaster.entrega1.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    private static final String BAD_REQUEST_MESSAGE = "The user already exists.";

    @Operation(summary = "Register a new user", description = "Verifies user information and attempts to register the user. Returns user details if successful.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully created."),
        @ApiResponse(responseCode = "400", description = "Bad Request: The user already exists.")
    })
    @PostMapping
    public ResponseEntity<Object> verifySignUpREST(@RequestBody UserDTO userDTO) {
        if (this.userService.registerUser(userDTO)) {
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.id()).toUri();
            return ResponseEntity.created(location).body(userDTO);
        } 
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BAD_REQUEST_MESSAGE);
        }
    }

    @Operation(summary = "Get active user profile", description = "Retrieves the profile information of the active user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile fetched successfully."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "404", description = "Not Found: User not found.")
    })
    @GetMapping("/me")
    public ResponseEntity<ShowUserDTO> accesToProfileREST(Principal principal) {
        long id = this.userService.getIdOfUser(principal.getName());
        return ResponseEntity.ok(this.userService.getUserWithID(id));
    }

    @Operation(summary = "Get all users", description = "Retrieves information for all registered users.")
    @ApiResponse(responseCode = "200", description = "All users fetched successfully.")
    @GetMapping
    public Page<ShowUserDTO> getAllUsers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return this.userService.getAllUsersFromDatabase(pageable);
    }

    @Operation(summary = "Update active user profile settings", description = "Updates the active user profile settings. Only the country is updated.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile updated successfully."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "404", description = "Not Found: User not found."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid data."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PutMapping("/me")
    public ResponseEntity<ShowUserDTO> changeUserSettingsREST(Principal principal, @RequestBody ShowUserDTO user) {
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
    }

    @Operation(summary = "Delete active user profile", description = "Attempts to delete the active user's profile and returns the deleted user's details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User profile deleted successfully."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "404", description = "Not Found: User not found.")
    })
    @DeleteMapping("/me")
    public ResponseEntity<ShowUserDTO> deleteUserProfileREST(Principal principal) {
        long id = this.userService.getIdOfUser(principal.getName());
        ShowUserDTO userToDelete = this.userService.getUserWithID(id);
        if (this.userService.removeExistingUserWithId(id)) {
            return ResponseEntity.ok(userToDelete);
        } 
        else {
            return ResponseEntity.notFound().build();
        }
    }
}