package org.ajug.voxxed.controller;

import org.ajug.voxxed.service.PhotoObject;
import org.ajug.voxxed.service.PhotoStreamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
    private final static String photoRepository = System.getProperty("java.io.tmpdir") + File.separator + System.currentTimeMillis();

    public PhotoStreamController() {
        new File(photoRepository).mkdirs();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String upload(@RequestParam("name") String name, @RequestParam("author") String author, @RequestParam("tags") String tags, @RequestParam("file") MultipartFile file) throws IOException {
        photoStreamService.setPhotoRepository(photoRepository);
        if (!file.isEmpty()) {
            final PhotoObject photoObject = new PhotoObject((FileInputStream) file.getInputStream()).withAuthor(author).withName(name).withTags(tags.split(";"));
            return photoStreamService.upload(photoObject);
        } else {
            return "";
        }
    }
}
