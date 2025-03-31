package es.ticketmaster.entrega1.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
        if (photo!= null && !photo.isEmpty()) {
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
}
