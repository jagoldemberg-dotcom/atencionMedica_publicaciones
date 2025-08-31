package com.example.actividad2.posts.model;

import java.time.LocalDateTime;

public record Comment(
        Long id,
        Long postId,
        String author,
        String text,
        Integer rating,
        LocalDateTime createdAt
){}
