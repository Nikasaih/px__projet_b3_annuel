package btree.projetpro.backend.controller;

import btree.projetpro.backend.entity.CommentEntity;
import btree.projetpro.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RequestMapping("/comments")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;


    @GetMapping
    public CollectionModel<EntityModel<CommentEntity>> getAllComments() {
        List<EntityModel<CommentEntity>> commentsWithHateoas = commentRepository.findAll()
                .stream()
                .map(comment -> EntityModel.of(comment,
                        linkTo(methodOn(CommentController.class)
                                .getCommentById(comment.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(CommentController.class)
                                .getAllComments())
                                .withRel("getAll"),

                        linkTo(methodOn(CommentController.class)
                                .deleteComment(comment.getId()))
                                .withRel("DeleteComment")))
                .collect(Collectors.toList());

        return CollectionModel.of(commentsWithHateoas,
                linkTo(methodOn(CommentController.class).getAllComments()).withSelfRel());
    }

    @GetMapping("/{commentId}")
    public EntityModel<CommentEntity> getCommentById(@PathVariable("commentId") Long id) {
        final CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found"));

        return EntityModel.of(comment,
                linkTo(methodOn(CommentController.class)
                        .getCommentById(id))
                        .withSelfRel(),

                linkTo(methodOn(CommentController.class)
                        .getAllComments())
                        .withRel("listAll"),

                linkTo(methodOn(CommentController.class)
                        .deleteComment(id))
                        .withRel("deleteSelf"));
    }

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
