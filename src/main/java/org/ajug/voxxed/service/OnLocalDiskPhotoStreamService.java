package org.ajug.voxxed.service;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
public class OnLocalDiskPhotoStreamService implements PhotoStreamService {

    private final String rootWorkingFolder;

    public OnLocalDiskPhotoStreamService(String rootWorkingFolder) {
        this.rootWorkingFolder = rootWorkingFolder;
    }

    @Override
    public String upload(PhotoObject photoObject) {
        final String fileName = photoObject.getName();
        final List<String> tags = photoObject.getTags();
        final String author = photoObject.getAuthor();
        try {
            IOUtils.copy(photoObject.getPhotoStream(), new FileOutputStream(rootWorkingFolder + File.separator + fileName));
            final FileWriter metadata = new FileWriter(rootWorkingFolder + File.separator + fileName + ".meta");
            tags.stream().forEach((s) -> {
                try {
                    metadata.write(s + " ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            metadata.write("\r\n");
            metadata.write(author);
            metadata.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
