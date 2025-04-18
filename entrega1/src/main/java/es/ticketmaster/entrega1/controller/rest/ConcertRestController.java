package es.ticketmaster.entrega1.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import es.ticketmaster.entrega1.dto.concert.BasicConcertDTO;
import es.ticketmaster.entrega1.dto.concert.ConcertDTO;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.exceptions.ImageException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Tag(name = "Concerts", description = "Endpoints for managing concert resources")
@RequestMapping("/api/concerts")
public class ConcertRestController {
    @Autowired
    private ConcertService concertService;

    @Operation(summary = "Get all concerts", description = "Returns a paginated list of all concerts with basic information")
    @ApiResponse(responseCode = "200", description = "All concerts fetched successfully.")
    @GetMapping("/")
    public Page<BasicConcertDTO> getConcerts(Pageable pageable) {
        return concertService.getAllConcertPage(pageable);
    }

    @Operation(summary = "Get concert by ID", description = "Returns full concert details based on the given concert ID")
    @ApiResponse(responseCode = "200", description = "Concert fetched successfully.")
    @GetMapping("/{id}")
    public ConcertDTO getConcert(@PathVariable long id) {
        return concertService.findConcertById(id);
    }

    @Operation(summary = "Get concerts near user", description = "Returns concerts based on the user's country or location (returns a global paginated list if there is no user logged)")
    @ApiResponse(responseCode = "200", description = "All concerts fetched successfully.")
    @GetMapping("/near-user")
    public Page<BasicConcertDTO> getConcertsNearUser(HttpServletRequest request, Pageable pageable,
            @RequestParam(required = false) String country) {
        if (country == null) { // for API REST petitions on Postman
            return concertService.getUserConcertPage(request.getUserPrincipal(), pageable);
        } else { // for Web Api Rest Petitions on JavaScript script
            return concertService.getUserConcertPage(pageable, country);
        }
    }

    @Operation(summary = "Get concerts by artist", description = "Returns a paginated list of concerts for a specific artist")
    @ApiResponse(responseCode = "200", description = "All concerts fetched successfully.")
    @GetMapping("/artists/{id}")
    public Page<BasicConcertDTO> getArtistConcerts(@PathVariable long id, Pageable pageable) {
        return concertService.getArtistConcerts(id, pageable);
    }

    @Operation(summary = "Create a new concert", description = "Creates and returns the newly created concert")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New concert created successfully."),
            @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
            @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid data."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PostMapping("/")
    public ResponseEntity<ConcertDTO> createConcert(@RequestBody ConcertDTO concert) {
        ConcertDTO created = concertService.saveConcert(concert);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Update a concert", description = "Updates and returns a concert based on the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Concert modified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid concert data"),
            @ApiResponse(responseCode = "404", description = "Not Found: Concert not found."),
            @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
            @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PutMapping("/{id}")
    public ConcertDTO modifyConcert(@PathVariable long id, @RequestBody ConcertDTO concert) {
        return concertService.modifyConcert(concert, id);
    }

    @Operation(summary = "Delete a concert", description = "Deletes a concert by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Concert deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid concert data"),
        @ApiResponse(responseCode = "404", description = "Not Found: Concert not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @DeleteMapping("/{id}")
    public ConcertDTO deleteConcert(@PathVariable long id) {
        return concertService.deleteConcert(id);
    }

    /*
     * The next controller methods will be dedicated to the managment of the poster
     * photo on the API REST
     */

    @Operation(summary = "Get concert image", description = "Retrieves the poster image of a concert by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Concert or image not found")
    })
    @GetMapping("/{id}/image")
    public ResponseEntity<Object> getPosterPhoto(@PathVariable long id) throws SQLException, IOException {
        Blob posterBlob = concertService.getConcertImage(id);
        if (posterBlob != null) {
            Resource poster = new InputStreamResource(posterBlob.getBinaryStream());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(poster);
        } else {
            throw new ImageException("Concert does not have an associated image");
        }
    }

    @Operation(summary = "Upload concert image", description = "Uploads a new poster image for a concert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image format"),
        @ApiResponse(responseCode = "404", description = "Not Found: Concert or image not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PostMapping("/{id}/image")
    public ResponseEntity<Object> postPosterPhoto(@PathVariable long id, @RequestParam MultipartFile posterPhoto)
            throws IOException {
        URI location = fromCurrentRequest().build().toUri();
        concertService.setPosterPhoto(id, posterPhoto.getInputStream(), posterPhoto.getSize(), posterPhoto != null);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Replace concert image", description = "Replaces the poster image of a concert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image modified successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image format"),
        @ApiResponse(responseCode = "404", description = "Not Found: Concert or image not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @PutMapping("/{id}/image")
    public ResponseEntity<Object> putPosterPhoto(@PathVariable long id, @RequestParam MultipartFile poster)
            throws IOException {
        concertService.setPosterPhoto(id, poster.getInputStream(), poster.getSize(), poster != null);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete concert image", description = "Deletes the poster image of a concert by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid image format"),
        @ApiResponse(responseCode = "404", description = "Not Found: Concert or image not found."),
        @ApiResponse(responseCode = "401", description = "Unauthorized: User is not authenticated."),
        @ApiResponse(responseCode = "405", description = "Bad Request: User is not allowed to execute the method."),
        @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error.")
    })
    @DeleteMapping("/{id}/image")
    public ResponseEntity<Object> deletePosterPhoto(@PathVariable long id) throws IOException {
        concertService.deleteConcertPoster(id);
        return ResponseEntity.noContent().build();
    }
}
