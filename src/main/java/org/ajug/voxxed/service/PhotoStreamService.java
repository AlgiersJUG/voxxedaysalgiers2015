package org.ajug.voxxed.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
public interface PhotoStreamService {
    String upload(PhotoObject photoObject);

    List<PhotoObject> getAllPhotos();

    void setPhotoRepository(String s);
}
