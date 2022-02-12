package btree.projetpro.backend.comment.controller;

import btree.projetpro.backend.comment.CommentEntity;
import btree.projetpro.backend.comment.CommentRepository;
import btree.projetpro.backend.util.hateoas.HateoasService;
import btree.projetpro.backend.util.hateoas.ReqControllerPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/public/comments")
@RestController
public class CommentControllerPublic implements ReqControllerPublic<CommentEntity> {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    HateoasService hateoasService;
    @Autowired
    CommentControllerAdmin commentControllerAdmin;

    @Override
    @GetMapping("/{id}")
    public EntityModel<CommentEntity> getById(@PathVariable("id") Long id) {
        final CommentEntity commentFound = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        return hateoasService.getOne(commentFound,
                this,
                commentControllerAdmin);
    }

    @Override
    public CollectionModel<EntityModel<CommentEntity>> getAll() {
        List<EntityModel<CommentEntity>> articlesWithHateoas = hateoasService.getAll(commentRepository.findAll(),
                this,
                commentControllerAdmin);

        return CollectionModel.of(articlesWithHateoas,
                linkTo(methodOn(CommentControllerPublic.class).getAll()).withSelfRel());
    }
}
