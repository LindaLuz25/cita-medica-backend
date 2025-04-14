package com.citamedica.salud.citamedica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citamedica.salud.citamedica.models.DoctorEntity;
import com.citamedica.salud.citamedica.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorEntity> createDoctor(@RequestBody DoctorEntity doctor) {
        return ResponseEntity.ok(doctorService.insert(doctor));
    }

    @GetMapping
    public List<DoctorEntity> getAllDoctors() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorEntity> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorEntity> updateDoctor(@PathVariable Long id, @RequestBody DoctorEntity doctor) {
        return ResponseEntity.ok(doctorService.update(id, doctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        boolean hasRemoved = doctorService.delete(id);
        return hasRemoved ?
        ResponseEntity.noContent().build():
        ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
