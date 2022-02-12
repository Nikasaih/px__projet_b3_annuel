package btree.projetpro.backend.comment.controller;

import btree.projetpro.backend.article.ArticleEntity;
import btree.projetpro.backend.comment.CommentDto;
import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.comment.CommentRepository;
import btree.projetpro.backend.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/comments")
@RestController
public class CommentControllerAdmin {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    DtoEntityConverter dtoEntityConverter;
    CommentEntity uselessEntityForDtoConvertion = new CommentEntity();

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentDto commentDto) {
        CommentEntity entityToSave = (CommentEntity) dtoEntityConverter.dtoToEntity(commentDto, uselessEntityForDtoConvertion);

        commentRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable("commentId") Long commentId) {
        commentRepository.deleteById(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
