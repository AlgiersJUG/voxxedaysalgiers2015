package org.ajug.voxxed.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.Size;
import com.flickr4java.flickr.uploader.UploadMetaData;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FlickrServiceTest {

    private static final String FLICKR_API_KEY = "bb6edb0d6e2b5c48c2a6872a44954bcb";
    private static final String FLICKR_API_SECRET = "80b2d6d8b0395e5f";


    @Test
    public void testAuthenticationToFilckrService() throws FlickrException, IOException {
        final String photoId = "16951887906";
        final String outputFileName = System.getProperty("java.io.tmpdir") + File.separator + "flickr-test.png";
        Flickr FLICKR = new Flickr(FLICKR_API_KEY, FLICKR_API_SECRET, new REST());
        final PhotosInterface photosInterface = FLICKR.getPhotosInterface();
        final BufferedImage image = photosInterface.getImage(photosInterface.getPhoto(photoId), Size.LARGE);
        ImageIO.write(image, "png", new FileOutputStream(outputFileName));
    }

    @Test
    public void testUploadImage() throws FlickrException {
        final UploadMetaData umd = new UploadMetaData();
        umd.setFilename("Alger la blanche");
        umd.setTitle("Alger la blanche");
        umd.setTags(Arrays.asList("Alger", "Algiers", "Algeria"));
        final REST transport = new REST();
        transport.setProxy("10.0.1.1",3128);
        Flickr FLICKR = new Flickr(FLICKR_API_KEY, FLICKR_API_SECRET, transport);
        final String upload = FLICKR.getUploader().upload(new File(System.getProperty("java.io.tmpdir") + File.separator + "algiers.jpg"), umd);
        System.out.println(upload);
    }
}
