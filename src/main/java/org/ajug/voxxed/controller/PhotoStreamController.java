package org.ajug.voxxed.controller;

import org.ajug.voxxed.service.PhotoObject;
import org.ajug.voxxed.service.PhotoStreamService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import java.io.FileInputStream;
import java.io.IOException;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<PhotoObject> listAllPhotos() {
        return photoStreamService.getAllPhotos();
    }

    @RequestMapping(value = "/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public InputStreamResource getPhotoStream(@PathVariable String photoId) {
        return new InputStreamResource(photoStreamService.getStream(photoId));
    }
}
