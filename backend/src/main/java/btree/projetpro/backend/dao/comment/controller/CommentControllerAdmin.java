package btree.projetpro.backend.dao.comment.controller;

import btree.projetpro.backend.dao.comment.CommentDto;
import btree.projetpro.backend.dao.comment.CommentEntity;
import btree.projetpro.backend.dao.comment.CommentRepository;
import btree.projetpro.backend.dao.util.hateoas.ReqControllerAdmin;
import btree.projetpro.backend.dao.util.persistenceservice.DtoEntityConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/comments")
@RestController
public class CommentControllerAdmin implements ReqControllerAdmin<CommentEntity> {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    DtoEntityConverterService dtoEntityConverter;
    CommentEntity uselessEntityForDtoConvertion = new CommentEntity();

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentDto commentDto) {
        CommentEntity entityToSave = (CommentEntity) dtoEntityConverter.dtoToEntity(commentDto, uselessEntityForDtoConvertion);

        commentRepository.save(entityToSave);

        return new ResponseEntity<>(entityToSave, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<CommentEntity> deleteOne(@PathVariable("id") Long id) {
        commentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}