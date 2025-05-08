package es.ticketmaster.entrega1.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/* This service controlls all the modifications and creations of photos to the correct format */
@Component
public class ImageService {

    /**
     * This method will create the Blob from a MultipartFile parameter
     *
     * @param photo the MultipartFile that is going to generate the Blob
     * @return the Blob object (or null if there is an error)
     * @throws IOException if an error occurs during file handling
     */
    public Blob getBlobOf(MultipartFile photo) throws IOException {
        if (photo != null && !photo.isEmpty()) {
            return BlobProxy.generateProxy(photo.getInputStream(), photo.getSize());
        } else {
            return null;
        }
    }

    /**
     * This method will create the Blob from a URL (String type)
     *
     * @param url the string containing the route of the image
     * @return the Blob object (or null if there is an error handling the link)
     */
    public Blob getBlobOf(String url) {
        try {
            // create a file with the url
            File imageFile = new File(url);
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            return BlobProxy.generateProxy(fileInputStream, imageFile.length());
        } catch (IOException e) {
            return null; //in case of error
        }
    }

    public Resource getResourceFromURL(String weburl) throws IOException {
        URL url = new URL(weburl);
        InputStream in = url.openStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int nRead;
        while ((nRead = in.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }
        in.close();
        byte[] imageBytes = buffer.toByteArray();

        return new InputStreamResource(new ByteArrayInputStream(imageBytes));
    }

    /**
     * Converts a remote image from a given URL into a Blob object.
     *
     * @param imageUrl the URL of the remote image to be converted into a Blob.
     * @return a Blob object representing the remote image.
     * @throws ResponseStatusException if an error occurs while processing the image,
     * such as an invalid URL or an issue with reading the image.
     */
    public Blob remoteImageToBlob(String imageUrl){
        try {
            Resource image = new UrlResource(imageUrl);
		    return BlobProxy.generateProxy(image.getInputStream(), image.contentLength());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error at processing the image");
        }
	}
}