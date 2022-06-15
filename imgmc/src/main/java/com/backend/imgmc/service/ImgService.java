package com.backend.imgmc.service;

import com.backend.imgmc.common.exception.ImgExtensionNotAllowedExc;
import com.backend.imgmc.common.exception.StorageFileNotFoundExc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ImgService {
    Path path;
    Pattern pattern = Pattern.compile(".(png|jpg)$");
    private String imgDirPath = "C:\\Users\\dchad\\code\\sup-de-vinci\\b3projet\\px__projet_b3_annuel\\imgmc\\imgDir";

    public Object saveImg(MultipartFile file) throws IOException, ImgExtensionNotAllowedExc {
        String filename = file.getResource().getFilename();
        if (file.isEmpty()) {
            throw new ImgExtensionNotAllowedExc();
        }

        Matcher matcher = pattern.matcher(filename);
        if (!matcher.find()) {
            throw new ImgExtensionNotAllowedExc();
        }

        File directory = new File(imgDirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        Path newPath = Paths.get(imgDirPath, filename);
        try (OutputStream os = Files.newOutputStream(newPath)) {
            os.write(file.getBytes());
        }

        return file.getName();
    }

    public Resource loadAsResource(String filename) throws MalformedURLException, StorageFileNotFoundExc {
        String toto = imgDirPath;
        log.info(toto);
        Path newPath = Paths.get(imgDirPath, filename);


        Resource resource = new UrlResource(newPath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }


        throw new StorageFileNotFoundExc("Could not read file: " + filename);
    }
}
