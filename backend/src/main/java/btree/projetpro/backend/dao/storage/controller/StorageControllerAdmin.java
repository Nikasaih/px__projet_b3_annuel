package btree.projetpro.backend.dao.storage.controller;

import btree.projetpro.backend.dao.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/admin/storage")
public class StorageControllerAdmin {
    @Autowired
    StorageService storageService;

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        storageService.store(file);

        return file.getResource().getFilename();
    }
}
