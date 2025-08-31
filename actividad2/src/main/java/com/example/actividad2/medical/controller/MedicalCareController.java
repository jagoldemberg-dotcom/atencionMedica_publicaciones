package com.example.actividad2.medical.controller;

import com.example.actividad2.common.NotFoundException;
import com.example.actividad2.medical.model.MedicalVisit;
import com.example.actividad2.medical.model.Patient;
import com.example.actividad2.medical.service.MedicalCareService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical")
@Validated
public class MedicalCareController {

    private final MedicalCareService service;
    public MedicalCareController(MedicalCareService service) { this.service = service; }

    @GetMapping("/patients")
    public List<Patient> listPatients(){
        return service.getAllPatients();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable @Min(1) Long id){
        return service.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("No se encontraron datos para el paciente con id " + id));
    }

    @GetMapping("/patients/by-rut")
    public ResponseEntity<Patient> getPatientByRut(
            @RequestParam
            @Pattern(regexp = "^[0-9]{1,2}\\.[0-9]{3}\\.[0-9]{3}-[0-9Kk]$", message = "RUT inválido. Formato esperado 12.345.678-9")
            String rut){
        return service.getPatientByRut(rut)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("No se encontraron datos para el RUT " + rut));
    }

    @GetMapping("/patients/{id}/visits")
    public List<MedicalVisit> listVisits(@PathVariable @Min(1) Long id){
        service.getPatientById(id)
                .orElseThrow(() -> new NotFoundException("No se encontraron datos para el paciente con id " + id));

        var visits = service.getVisitsByPatient(id);
        if (visits.isEmpty()) {
            throw new NotFoundException("No se encontraron atenciones para el paciente con id " + id);
        }
        return visits;
    }

    @GetMapping("/visits")
    public List<MedicalVisit> searchVisits(@RequestParam(required = false) @Length(max = 30) String specialty){
        return service.searchVisits(java.util.Optional.ofNullable(specialty));
    }
}

