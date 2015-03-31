package org.ajug.voxxed.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
public class PhotoObject {

    private final FileInputStream photoStream;
    private String name;
    private List<String> tags;
    private String author;

    public PhotoObject(FileInputStream photoStream) {
        this.photoStream = photoStream;
    }

    public PhotoObject withName(String name) {
        this.name = name;
        return this;
    }

    public PhotoObject withTags(String... tags) {
        this.tags = Arrays.asList(tags);
        return this;
    }

    public PhotoObject withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getAuthor() {
        return author;
    }

    public InputStream getPhotoStream() {
        return photoStream;
    }
}
