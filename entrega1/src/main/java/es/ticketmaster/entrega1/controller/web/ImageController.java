package es.ticketmaster.entrega1.controller.web;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.ticketmaster.entrega1.model.Artist;
import es.ticketmaster.entrega1.model.UserEntity;
import es.ticketmaster.entrega1.service.ArtistService;
import es.ticketmaster.entrega1.service.ConcertService;
import es.ticketmaster.entrega1.service.ImageService;
import es.ticketmaster.entrega1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ImageController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private UserService userService;

    @Autowired ImageService imageService;

    /**
     * It shows the artist photo on the page where the mapping is called Acts
     * uppon the mapping /artist/{{id}}/download-photo
     *
     * @param id the artist's id whose photo will be shown
     * @return the photo on the ResponseEntity<Object> if everything went well
     * (otherwise returns a not found object)
     * @throws SQLException
     */
    @GetMapping("/artist/{id}/download-photo")
    public ResponseEntity<Object> downloadArtistPhoto(@PathVariable long id) throws SQLException {

        Artist op = artistService.getArtistEntity(id);

        if ((op != null) && op.getPhoto() != null) {

            Blob image = op.getPhoto();
            Resource file = new InputStreamResource(image.getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * It shows the profile picture of the activeUser on the page where the
     * mapping is called Acts uppon the mapping
     * /artist/{{id}}/download-latestAlbum-photo
     *
     * @param request is the HTTP request.
     * @return the photo on the ResponseEntity<Object> if everything went well
     * (otherwise returns a not found object)
     * @throws SQLException
     */
    @GetMapping("/profile/download-profile-picture")
    public ResponseEntity<Object> downloadProfilePicture(HttpServletRequest request) throws SQLException {
        UserEntity user = userService.getActiveUserWithProfilePicture(request.getUserPrincipal());

        if (user != null && user.getProfilePicture() != null) {
            Blob image = user.getProfilePicture();
            Resource file = new InputStreamResource(image.getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(image.length()).body(file);

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     *
     * @param id id of the concert whose poster photo is going to be shown
     * @return the photo on the ResponseEntity<Object> if everything went well
     * (otherwise returns a not found object)
     * @throws SQLException
     */
    @GetMapping("/concert/{id}/download-poster")
    public ResponseEntity<Object> downloadConcertPoster(@PathVariable long id) throws SQLException, IOException {

        Blob concertImage = concertService.getConcertImage(id);

        if (concertImage != null) {

            Resource file = new InputStreamResource(concertImage.getBinaryStream());

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .contentLength(concertImage.length()).body(file);

        } else {
            Resource defaultImage = imageService.getResourceFromURL("https://i.postimg.cc/jS2hjWdd/default-poster.png");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(defaultImage);
        }
    }
}
