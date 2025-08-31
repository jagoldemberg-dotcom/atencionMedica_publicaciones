package com.example.actividad2.posts.sevice;

import com.example.actividad2.posts.model.Comment;
import com.example.actividad2.posts.model.Post;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final List<Comment> comments = new ArrayList<>();

    @PostConstruct
    void seed(){
        posts.addAll(List.of(
                new Post(1L, "Bienvenidos", "Primer post de bienvenida", LocalDateTime.now().minusDays(10)),
                new Post(2L, "Novedades", "Hicimos varias mejoras", LocalDateTime.now().minusDays(5)),
                new Post(3L, "Tips", "Consejos útiles", LocalDateTime.now().minusDays(1)),
                new Post(4L, "Curiosidades", "Curiosidades que mataron al gato", LocalDateTime.now().minusDays(1)),
                new Post(5L, "INteres", "In tereses", LocalDateTime.now().minusDays(1))
        ));
        comments.addAll(List.of(
                new Comment(1L, 1L, "Ana", "¡Excelente!", 5, LocalDateTime.now().minusDays(9)),
                new Comment(3L, 1L, "Jacob", "Podría ser mejor", 3, LocalDateTime.now().minusDays(4)),
                new Comment(4L, 1L, "Manuel", "Me gustó", 4, LocalDateTime.now().minusDays(3)),
                new Comment(5L, 1L, "Miguel", "Muy útil", 4, LocalDateTime.now().minusDays(8)),

                new Comment(6L, 2L, "Carla", "Podría ser mejor", 3, LocalDateTime.now().minusDays(4)),
                new Comment(7L, 2L, "Anuel", "Me gustó", 1, LocalDateTime.now().minusDays(3)),
                new Comment(8L, 2L, "Dariel", "Podría ser mejor", 1, LocalDateTime.now().minusDays(4)),
                new Comment(9L, 2L, "Amanda", "Me gustó", 5, LocalDateTime.now().minusDays(3)),

                new Comment(10L, 3L, "panda", "No Me gustó", 4, LocalDateTime.now().minusDays(3)),
                new Comment(11L, 3L, "Manuel", "Me gustó pero no", 4, LocalDateTime.now().minusDays(3)),
                new Comment(12L, 3L, "Amanda", "No me gusto", 1, LocalDateTime.now().minusDays(3)),
                new Comment(13L, 3L, "Eva", "Faltan ejemplos", 2, LocalDateTime.now().minusHours(12))
        ));
    }

    public List<Post> getAllPosts(){ return Collections.unmodifiableList(posts);}
    public Optional<Post> getPost(Long id){ return posts.stream().filter(p -> p.id().equals(id)).findFirst(); }

    public List<Comment> getCommentsByPost(Long postId){
        return comments.stream().filter(c -> c.postId().equals(postId)).collect(Collectors.toList());
    }

    public double getAverageRating(Long postId){
        var list = getCommentsByPost(postId);
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToInt(Comment::rating).average().orElse(0.0);
    }

    public List<Comment> searchComments(Integer minRating){
        return minRating == null ? Collections.unmodifiableList(comments)
                : comments.stream().filter(c -> c.rating() >= minRating).toList();
    }
}

