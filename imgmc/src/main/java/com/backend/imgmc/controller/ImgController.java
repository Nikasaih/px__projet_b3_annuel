package com.backend.imgmc.controller;

import com.backend.imgmc.common.exception.StorageFileNotFoundExc;
import com.backend.imgmc.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/img")
@Slf4j
public class ImgController {
    @Autowired
    ImgService imgService;

    @GetMapping("/{filename}")
    public ResponseEntity<Object> serveFile(@PathVariable("filename") String filename) {
        try {
            Resource file = imgService.loadAsResource(filename);
            return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (StorageFileNotFoundExc e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }

    @PostMapping
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            imgService.saveImg(file);
            return ResponseEntity.status(HttpStatus.OK).body(file.getResource().getFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
