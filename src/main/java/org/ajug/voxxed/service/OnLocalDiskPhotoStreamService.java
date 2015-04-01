package org.ajug.voxxed.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 3/31/15
 * Time: 3:53 PM
 */
@Service
public class OnLocalDiskPhotoStreamService implements PhotoStreamService {

    private static final Logger logger = LoggerFactory.getLogger(OnLocalDiskPhotoStreamService.class);

    @Value("${photostream.service.repository}")
    private String rootWorkingFolder;

    @Override
    public void init() {
        final File photoStreamRootWorkingRepository = new File(rootWorkingFolder);
        if (photoStreamRootWorkingRepository.exists()) {
            if (photoStreamRootWorkingRepository.isFile())
                throw new PhotoStreamException(rootWorkingFolder + " Must be a folder but it's actually a File");
        } else
            photoStreamRootWorkingRepository.mkdirs();
    }

    @Override
    public String upload(PhotoObject photoObject) {
        logger.info("Current Photos Repository " + rootWorkingFolder);
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
            logger.error("[PhotoStream] Error during photo upload.", e);
            return "[PhotoStream] Error during photo upload.";
        }
        return fileName;
    }

    @Override
    public List<PhotoObject> getAllPhotos() {
        final List<PhotoObject> photos = new ArrayList<>();
        final File photoRepository = new File(rootWorkingFolder);
        final File[] files = getAllFiles(photoRepository);

        Arrays.asList(files).stream().forEach(file -> {
            if (!file.getName().contains(".meta")) {
                try {
                    final Scanner metas = new Scanner(new FileInputStream(photoRepository.getPath() + File.separator + file.getName() + ".meta"));
                    final String[] tags = metas.nextLine().split(" ");
                    final String author = metas.nextLine();
                    photos.add(new PhotoObject(new FileInputStream(file)).withName(file.getName()).withAuthor(author).withTags(tags));
                } catch (FileNotFoundException e) {
                    logger.error("[PhotoStream] Error during loading photo from repository.", e);
                }

            }
        });
        return photos;
    }

    private File[] getAllFiles(File photoRepository) {
        if (!photoRepository.isDirectory()) {
            throw new IllegalArgumentException("Path is not a folder");
        }
        return photoRepository.listFiles();
    }

    @Override
    public void setPhotoRepository(String rootWorkingFolder) {
        this.rootWorkingFolder = rootWorkingFolder;
    }

    @Override
    public InputStream getStream(String photoName) {
        final File[] allFiles = getAllFiles(new File(rootWorkingFolder));
        final List<File> files = Arrays.asList(allFiles)
            .stream()
            .filter(f -> f.getName().contains(photoName) && !f.getName().endsWith(".meta"))
            .collect(Collectors.toList());
        if (files.size()==0)
            throw new PhotoStreamException("Image " + photoName + " not found");
        try {
            return new FileInputStream(files.get(0));
        } catch (FileNotFoundException e) {
            throw new PhotoStreamException("File was not found");
        }
    }
}
