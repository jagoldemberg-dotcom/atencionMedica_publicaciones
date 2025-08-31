package com.example.actividad2.posts.model;

import java.time.LocalDateTime;

public record Post(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt
){}
