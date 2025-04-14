package com.citamedica.salud.citamedica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citamedica.salud.citamedica.models.Appointment;
import com.citamedica.salud.citamedica.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
@Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment request){
        try {
            var result = appointmentService.insert(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.err.println("Error creating appointment: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAppointment(@PathVariable Long id) {
        return appointmentService.delete(id);
    }
}
