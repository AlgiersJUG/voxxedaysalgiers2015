package org.ajug.voxxed.controller;

import org.ajug.voxxed.service.PhotoObject;
import org.ajug.voxxed.service.PhotoStreamException;
import org.ajug.voxxed.service.PhotoStreamService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 5:31 PM
 */
@RestController
@RequestMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoStreamController {

    public static final Logger logger = LoggerFactory.getLogger(PhotoStreamController.class);
    @Inject
    PhotoStreamService photoStreamService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("name") String name, @RequestParam("author") String author, @RequestParam("tags") String tags, @RequestParam("file") MultipartFile file) throws IOException {
        // TODO init should be called else where
        photoStreamService.init();
        if (!file.isEmpty()) {
            final PhotoObject photoObject = new PhotoObject((FileInputStream) file.getInputStream()).withAuthor(author).withName(name).withTags(tags.split(";"));
            return photoStreamService.upload(photoObject);
        } else {
            return "NO FILE HAS BEEN UPLOADED !";
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public HttpHeaders defaultMethod() {
        throw new PhotoStreamException("This is a piece of shit !");
        /*
        final HttpHeaders httpHeaders = new HttpHeaders();
        try {
            httpHeaders.setLocation(new URI("/photos"));
        } catch (URISyntaxException e) {
            throw new PhotoStreamException("", e);
        }
        return httpHeaders;
        */

    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<PhotoObject> listAllPhotos() {
        final List<PhotoObject> allPhotos = photoStreamService.getAllPhotos();
        logger.info("" + allPhotos);
        return allPhotos;
    }

    @RequestMapping(value = "/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPhotoStream(@PathVariable String photoId, HttpServletResponse response) {
        final PhotoObject photo = photoStreamService.findByPhotoId(photoId);
        response.setContentType(photo.getContentType());
        try {
            IOUtils.copy(photo.getPhotoStream(), response.getOutputStream());
        } catch (IOException e) {
            throw new PhotoStreamException("Error during stream copy from source to output", e);
        }

    }

    @RequestMapping(value = "/url/{photoId}")
    public String buildPhotoUrl(@PathVariable String photoId) {
        final PhotoObject photo = photoStreamService.findByPhotoId(photoId);
        return "http://localhost/photostream/" + photo.getName();
    }
}
