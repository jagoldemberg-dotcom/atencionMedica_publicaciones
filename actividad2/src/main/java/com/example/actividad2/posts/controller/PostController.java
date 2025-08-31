package com.example.actividad2.posts.controller;

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
    public List<Post> list(){ return service.getAllPosts(); }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable @Min(1) Long id){
        return service.getPost(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> comments(@PathVariable @Min(1) Long id){
        if (service.getPost(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.getCommentsByPost(id));
    }

    @GetMapping("/{id}/rating/average")
    public ResponseEntity<Double> average(@PathVariable @Min(1) Long id){
        if (service.getPost(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.getAverageRating(id));
    }

    @GetMapping("/comments")
    public List<Comment> searchComments(@RequestParam(required = false)
                                        @Min(value = 1, message = "minRating debe ser >= 1")
                                        @Max(value = 5, message = "minRating debe ser <= 5")
                                        Integer minRating){
        return service.searchComments(minRating);
    }
}

