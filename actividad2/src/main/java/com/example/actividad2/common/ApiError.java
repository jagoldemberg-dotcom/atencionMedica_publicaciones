package com.example.actividad2.common;

import java.time.Instant;

public record ApiError(String message, String path, Instant timestamp) {
    public static ApiError of(String message, String path){
        return new ApiError(message, path, Instant.now());
    }
}
