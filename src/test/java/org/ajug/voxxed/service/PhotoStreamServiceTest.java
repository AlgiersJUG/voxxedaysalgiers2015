package org.ajug.voxxed.service;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PhotoStreamServiceTest {

    private static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String ROOT_WORKING_FOLDER = TMP_FOLDER + File.separator + "PhotoStream" + File.separator + System.currentTimeMillis();

    @Before
    public void before() {
        new File(ROOT_WORKING_FOLDER).mkdirs();
    }

    @Test
    public void testUploadPhoto() throws FileNotFoundException {
        final PhotoStreamService photoStreamService = new OnLocalDiskPhotoStreamService(ROOT_WORKING_FOLDER);
        final FileInputStream originalPhotoStream = new FileInputStream(TMP_FOLDER + File.separator + "test.jpg");
        final PhotoObject photoObject = new PhotoObject(originalPhotoStream).withName("myvoxxed.jpg").withTags("voxxed", "algiers", "2015").withAuthor("Anonymous");
        final String photoObjectId = photoStreamService.upload(photoObject);
        Assertions.assertThat(photoObjectId).isNotNull().isNotEmpty().isEqualTo("myvoxxed.jpg");
    }


    public void testListAllPhotos() {
        /*
        final PhotoStreamService photoStreamService = new OnLocalDiskPhotoStreamService(ROOT_WORKING_FOLDER);
        List<PhotoObject> allPhotos = photoStreamService.getAllPhotos();
        Assertions.assertThat(allPhotos).isNotNull().isNotEmpty().hasSize(1);
        final PhotoObject photoObject = allPhotos.get(0);
        Assertions.assertThat(photoObject).isNotNull();
        Assertions.assertThat(photoObject.getName()).isNotNull().isNotEmpty().isEqualTo("myvoxxed.jpg");
        */
    }
}
