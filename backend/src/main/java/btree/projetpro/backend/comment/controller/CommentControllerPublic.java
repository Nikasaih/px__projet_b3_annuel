package btree.projetpro.backend.comment.controller;

import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/public/comments")
@RestController
public class CommentControllerPublic {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public CollectionModel<EntityModel<CommentEntity>> getAllComments() {
        List<EntityModel<CommentEntity>> commentsWithHateoas = commentRepository.findAll()
                .stream()
                .map(comment -> EntityModel.of(comment,
                        linkTo(methodOn(CommentControllerPublic.class)
                                .getCommentById(comment.getId()))
                                .withRel("getSelf"),

                        linkTo(methodOn(CommentControllerPublic.class)
                                .getAllComments())
                                .withRel("getAll"),

                        linkTo(methodOn(CommentControllerAdmin.class)
                                .deleteComment(comment.getId()))
                                .withRel("DeleteComment")))
                .collect(Collectors.toList());

        return CollectionModel.of(commentsWithHateoas,
                linkTo(methodOn(CommentControllerPublic.class).getAllComments()).withSelfRel());
    }

    @GetMapping("/{commentId}")
    public EntityModel<CommentEntity> getCommentById(@PathVariable("commentId") Long id) {
        final CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("comment not found"));

        return EntityModel.of(comment,
                linkTo(methodOn(CommentControllerPublic.class)
                        .getCommentById(id))
                        .withSelfRel(),

                linkTo(methodOn(CommentControllerPublic.class)
                        .getAllComments())
                        .withRel("listAll"),

                linkTo(methodOn(CommentControllerAdmin.class)
                        .deleteComment(id))
                        .withRel("deleteSelf"));
    }
}
