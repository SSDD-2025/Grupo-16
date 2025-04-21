package es.ticketmaster.entrega1.controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import es.ticketmaster.entrega1.dto.artist.ArtistDTO;
import es.ticketmaster.entrega1.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Artists", description = "Endpoints for managing artist resources")
@RequestMapping("/api/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    /*ARTIST SEARCH CONTROLLER METHODS*/
    @Operation(summary = "Get all the existing artist.", description = "Returns a page of artists. The page size is modificable, but its default size is 0. As well, the default page is the first one.")
    @ApiResponse(responseCode = "200", description = "All artists fetched successfully.")
    @GetMapping
    public Page<ArtistDTO> getAllArtists(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(name = "search", required = false) String search) {

        if (search == null) {
            return artistService.getEveryArtist(pageable);
        } else {
            return artistService.getSearchDTOBy(search, pageable);
        }

    }

    @Operation(summary = "Get an specific artist.", description = "Returns the path-specified by id Artist information. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artist obtained successfully."),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist not found."),})
    @GetMapping("/{id}")
    public ArtistDTO getArtist(@PathVariable long id) {

        return artistService.getArtist(id);

    }

    @Operation(summary = "Get the photo of an Artist.", description = "Returns the path-specified by id Artist image. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Photo obtained successfully."),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist or photo not found.")
    })
    @GetMapping("/{id}/photo")
    public ResponseEntity<Object> getPhoto(@PathVariable long id) throws SQLException, IOException {

        Resource mainPhoto = artistService.getPhoto(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg", "image/jpg", "image/png").body(mainPhoto);
    }


    /*ARTIST CREATION CONTROLLER METHODS*/
    @Operation(summary = "Create a new Artist", description = "Creates an the post-specified Artist and returns its saved information. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Artist created with success."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid data or format. The Artist's name is repeated."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO artistDTO) throws Exception {

        artistDTO = artistService.registerNewArtist(artistDTO);

        URI location = fromCurrentRequest().path("/id").buildAndExpand(artistDTO.id()).toUri();

        return ResponseEntity.created(location).body(artistDTO);
    }

    @Operation(summary = "Upload an Artist's photo", description = "Saves and activates the image as the path-specified by id Artist's photo. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image uploaded with success."),
        @ApiResponse(responseCode = "400", description = "Bad Resquest: Invalid image format"),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist or photo not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PostMapping("/{id}/photo")
    public ResponseEntity<Object> createMainPhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        URI location = fromCurrentRequest().build().toUri();

        artistService.createPhotoImage(id, imageFile);

        return ResponseEntity.created(location).build();
    }


    /*ARTIST MODIFICATION CONTROLLER METHODS*/
    @Operation(summary = "Modify an Artist", description = "Modifies the path-specified by id Artist and returns its saved information. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Artist modified with success."),
        @ApiResponse(responseCode = "400", description = "Bad Resquest: Invalid data or format."),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PutMapping("/{id}")
    public ArtistDTO modifyArtist(@PathVariable long id, @RequestBody ArtistDTO artistDTO) throws IOException {

        return artistService.modifyArtistWithId(artistDTO, id);

    }

    @Operation(summary = "Modifies an Artist's photo", description = "Saves and activates the image as the path-specified by id Artist's photo. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image uploaded with success."),
        @ApiResponse(responseCode = "400", description = "Bad Resquest: Invalid image format"),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist or photo not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PutMapping("/{id}/photo")
    public ResponseEntity<Object> replacePhoto(@PathVariable long id, @RequestParam MultipartFile imageFile) throws IOException {

        artistService.replacePhotoImage(id, imageFile);

        return ResponseEntity.noContent().build();
    }


    /*ARTIST DELETION CONTROLLER METHODS.*/
    @Operation(summary = "Delete an Artist", description = "Deletes the path-specified by id Artist and returns its previous-to-deletion information. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Artist deleted with success."),
        @ApiResponse(responseCode = "400", description = "Bad Resquest: Invalid data or format."),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @DeleteMapping("/{id}")
    public ArtistDTO deleteArtist(@PathVariable long id) {

        return artistService.deleteArtistWithId(id);

    }

    @Operation(summary = "Deletes an Artist's photo", description = "Deletes the photo of the path-specified by id Artist. If any error occurs, a personalized error DTO will be returned with its specific information.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image deleted with success."),
        @ApiResponse(responseCode = "404", description = "Not Found: Artist or photo not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute this function."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @DeleteMapping("/{id}/photo")
    public ResponseEntity<Object> deleteMainPhoto(@PathVariable long id) throws SQLException, IOException {

        artistService.deletePhotoImage(id);

        return ResponseEntity.noContent().build();
    }
}
