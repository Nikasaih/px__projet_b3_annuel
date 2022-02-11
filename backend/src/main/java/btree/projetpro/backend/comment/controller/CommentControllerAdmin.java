package btree.projetpro.backend.comment.controller;

import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/comments")
@RestController
public class CommentControllerAdmin {
    @Autowired
    CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentEntity comment) {
        commentRepository.save(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable("commentId") Long commentId) {
        commentRepository.deleteById(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
