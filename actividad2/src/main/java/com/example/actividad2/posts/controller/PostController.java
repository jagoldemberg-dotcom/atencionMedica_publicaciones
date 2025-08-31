package com.example.actividad2.posts.controller;

import com.example.actividad2.common.NotFoundException;
import com.example.actividad2.posts.model.Comment;
import com.example.actividad2.posts.model.Post;
import com.example.actividad2.posts.sevice.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {
    private final PostService service;
    public PostController(PostService service){ this.service = service; }

    @GetMapping
    public List<Post> list(){
        var all = service.getAllPosts();
        if (all.isEmpty()) {
            throw new NotFoundException("No se encontraron publicaciones");
        }
        return all;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable @Min(1) Long id){
        return service.getPost(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException(
                        "No se encontró la publicación con id " + id));
    }

    @GetMapping("/{id}/comments")
    public List<Comment> comments(@PathVariable @Min(1) Long id){
        service.getPost(id)
                .orElseThrow(() -> new NotFoundException(
                        "No se encontró la publicación con id " + id));
        var list = service.getCommentsByPost(id);
        if (list.isEmpty()) {
            throw new NotFoundException("No se encontraron comentarios para la publicación " + id);
        }
        return list;
    }

    @GetMapping("/{id}/rating/average")
    public double average(@PathVariable @Min(1) Long id){
        service.getPost(id)
                .orElseThrow(() -> new NotFoundException(
                        "No se encontró la publicación con id " + id));
        if (service.getCommentsByPost(id).isEmpty()) {
            throw new NotFoundException("No hay calificaciones para la publicación " + id);
        }
        return service.getAverageRating(id);
    }

    @GetMapping("/comments")
    public List<Comment> searchComments(@RequestParam(required = false)
                                        @Min(value = 1, message = "minRating debe ser >= 1")
                                        @Max(value = 5, message = "minRating debe ser <= 5")
                                        Integer minRating){
        var result = service.searchComments(minRating);
        if (result.isEmpty()) {
            throw new NotFoundException("No se encontraron comentarios con el criterio dado");
        }
        return result;
    }
}

