package com.example.actividad2.medical.service;


import com.example.actividad2.medical.model.MedicalVisit;
import com.example.actividad2.medical.model.Patient;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalCareService {
    private final List<Patient> patients = new ArrayList<>();
    private final List<MedicalVisit> visits = new ArrayList<>();

    @PostConstruct
    void seed(){
        patients.addAll(List.of(
                new Patient(1L, "12.345.678-5", "Ana", "Pérez", LocalDate.of(1990, 3, 12), "+56 9 1234 5678", "ana.perez@example.com"),
                new Patient(2L, "9.876.543-2", "Bruno", "Lagos", LocalDate.of(1985, 11, 5), "+56 9 2222 2222", "bruno.lagos@example.com"),
                new Patient(3L, "7.654.321-K", "Carla", "Muñoz", LocalDate.of(2001, 7, 21), "+56 9 3333 3333", "carla.munoz@example.com")
        ));
        visits.addAll(List.of(
                new MedicalVisit(1L, 1L, LocalDate.now().minusDays(30), "Medicina General", "Rinitis alérgica", "Control anual"),
                new MedicalVisit(2L, 1L, LocalDate.now().minusDays(10), "Dermatología", "Dermatitis", "Tratamiento tópico"),
                new MedicalVisit(3L, 2L, LocalDate.now().minusDays(3), "Kinesiología", "Lumbalgia", "Ejercicios de estiramiento"),
                new MedicalVisit(4L, 3L, LocalDate.now().minusDays(15), "Oftalmología", "Miopía", "Indicación de lentes"),
                new MedicalVisit(5L, 3L, LocalDate.now().minusDays(1), "Medicina General", "Resfrío común", "Reposo 3 días")
        ));
    }

    public List<Patient> getAllPatients(){
        return Collections.unmodifiableList(patients);
    }
    public Optional<Patient> getPatientById(Long id){
        return patients.stream().filter(p -> p.id().equals(id)).findFirst();
    }
    public Optional<Patient> getPatientByRut(String rut){
        return patients.stream().filter(p -> p.rut().equalsIgnoreCase(rut)).findFirst();
    }
    public List<MedicalVisit> getVisitsByPatient(Long patientId){
        return visits.stream().filter(v -> v.patientId().equals(patientId)).collect(Collectors.toList());
    }
    public List<MedicalVisit> searchVisits(Optional<String> specialty){
        return specialty.map(s -> visits.stream().filter(v -> v.specialty().equalsIgnoreCase(s)).toList())
                .orElseGet(() -> Collections.unmodifiableList(visits));
    }
}

