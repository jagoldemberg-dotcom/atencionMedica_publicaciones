package com.example.actividad2.medical.model;

import java.time.LocalDate;

public record MedicalVisit(
        Long id,
        Long patientId,
        LocalDate date,
        String specialty,
        String diagnosis,
        String notes
){}
