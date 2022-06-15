package com.backend.securitygw.controller.redirection.imgmc;

import com.backend.securitygw.service.endpoint.RedirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/img")
public class ImgController {
    @Value("${microservices.imgmc}")
    String imgmc;
    @Autowired
    RedirectionService redirectionService;
    String redirectionControllerUrl = "/img";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(imgmc + redirectionControllerUrl + "/" + filename, Resource.class);
    }

    @PostMapping
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.print(imgmc + redirectionControllerUrl + "\n");
        return redirectionService.redirectRequestParma(HttpMethod.POST, imgmc + redirectionControllerUrl, file);
    }
}
