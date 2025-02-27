package es.ticketmaster.entrega1.service;

import java.io.IOException;
import java.sql.Blob;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/* This service controlls all the modifications and creations of photos to the correct format */

@Component
public class ImageService {
    public Blob getBlobOf(MultipartFile photo) throws IOException{
        return BlobProxy.generateProxy(photo.getInputStream(),photo.getSize());
    }

    public Blob getBlobOf(String url) throws IOException{
        return null;
    }
}
