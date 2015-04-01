package org.ajug.voxxed.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
public interface PhotoStreamService {

    public void init();

    String upload(PhotoObject photoObject);

    List<PhotoObject> getAllPhotos();

    void setPhotoRepository(String s);

    InputStream getStream(String photoName);
}
