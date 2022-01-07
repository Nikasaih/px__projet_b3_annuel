package b3.projetpro.backend.controller;

import b3.projetpro.backend.entity.ArticleEntity;
import b3.projetpro.backend.entity.CommentEntity;
import b3.projetpro.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping(path = "/")
    public ResponseEntity<List<CommentEntity>> getAllComments() {
        final List<CommentEntity> response = commentRepository.findAll();
        return new ResponseEntity<List<CommentEntity>>(response, HttpStatus.OK);
    }
}
