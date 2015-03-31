package org.ajug.voxxed.service;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class PhotoStreamServiceTest {

    private static final String TMP_FOLDER = System.getProperty("java.io.tmpdir");
    private static final String ROOT_PHOTO_REPOSITORY = TMP_FOLDER + File.separator + "PhotoStream" + File.separator + System.currentTimeMillis();

    @Before
    public void before() {
        final File file = new File(ROOT_PHOTO_REPOSITORY);
        if (!file.isDirectory() || !file.exists())
            file.mkdirs();
    }

    @Test
    public void testListAllPhotos() {
        final PhotoStreamService photoStreamService = new OnLocalDiskPhotoStreamService(ROOT_PHOTO_REPOSITORY);
        List<PhotoObject> allPhotos = photoStreamService.getAllPhotos();
        Assertions.assertThat(allPhotos).isNotNull().isNotEmpty().hasSize(1);
        final PhotoObject photoObject = allPhotos.get(0);
        Assertions.assertThat(photoObject).isNotNull();
        Assertions.assertThat(photoObject.getName()).isNotNull().isNotEmpty().isEqualTo("myvoxxed.jpg");
    }

    @Test
    public void testUploadPhoto() throws FileNotFoundException {
        final PhotoStreamService photoStreamService = new OnLocalDiskPhotoStreamService(ROOT_PHOTO_REPOSITORY);
        final FileInputStream originalPhotoStream = new FileInputStream(TMP_FOLDER + File.separator + "test.jpg");
        final PhotoObject photoObject = new PhotoObject(originalPhotoStream).withName("myvoxxed.jpg").withTags("voxxed", "algiers", "2015").withAuthor("Anonymous");
        final String photoObjectId = photoStreamService.upload(photoObject);
        Assertions.assertThat(photoObjectId).isNotNull().isNotEmpty().isEqualTo("myvoxxed.jpg");
    }


}
