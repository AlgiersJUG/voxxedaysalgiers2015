package org.ajug.voxxed.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
@Service
public class OnLocalDiskPhotoStreamService implements PhotoStreamService {

    private String rootWorkingFolder;

    public OnLocalDiskPhotoStreamService() {
    }

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

    @Override
    public List<PhotoObject> getAllPhotos() {
        final File repo = new File(rootWorkingFolder);
        if (!repo.isDirectory())
            throw new IllegalArgumentException("Path is not a folder");
        final List<PhotoObject> photos = new ArrayList<>();
        final File[] files = repo.listFiles();

        Arrays.asList(files).stream().forEach(file -> {
            if (!file.getName().contains(".meta")) {
                try {
                    final Scanner metas = new Scanner(new FileInputStream(repo.getPath() + File.separator + file.getName() + ".meta"));
                    final String[] tags = metas.nextLine().split(" ");
                    final String author = metas.nextLine();
                    photos.add(new PhotoObject(new FileInputStream(file)).withName(file.getName()).withAuthor(author).withTags(tags));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        return photos;
    }

    @Override
    public void setPhotoRepository(String rootWorkingFolder) {
        this.rootWorkingFolder = rootWorkingFolder;
    }
}
