package com.example.actividad2.medical.model;

import java.time.LocalDate;

public record Patient(
        Long id,
        String rut,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String phone,
        String email
){}