package com.citamedica.salud.citamedica.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.citamedica.salud.citamedica.models.Appointment;
import com.citamedica.salud.citamedica.repository.AppointmentRepository;
import com.citamedica.salud.citamedica.repository.DoctorRepository;
import com.citamedica.salud.citamedica.repository.UserRepository;

@Service
public class AppointmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional(timeout = 10, readOnly = false)
    public Appointment insert(Appointment entity) {
        if (entity.getUser() == null || entity.getUser().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario es obligatorio.");
        }

        if (entity.getDoctor() == null || entity.getDoctor().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El doctor es obligatorio.");
        }

        if (entity.getDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la cita es obligatoria.");
        }

        if (entity.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la cita no puede ser en el pasado.");
        }

        var user = userRepository.findById(entity.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        var doctor = doctorRepository.findById(entity.getDoctor().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));

        entity.setUser(user);
        entity.setDoctor(doctor);

        return appointmentRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));
    }

    @Transactional(timeout = 10, readOnly = false)
    public Appointment update(Long id, Appointment entity) {
        var existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada"));

        if (entity.getDate() != null && entity.getDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede asignar una fecha pasada.");
        }

        if (entity.getUser() != null && entity.getUser().getId() != null) {
            var user = userRepository.findById(entity.getUser().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            existingAppointment.setUser(user);
        }

        if (entity.getDoctor() != null && entity.getDoctor().getId() != null) {
            var doctor = doctorRepository.findById(entity.getDoctor().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));
            existingAppointment.setDoctor(doctor);
        }

        if (entity.getDate() != null) {
            existingAppointment.setDate(entity.getDate());
        }

        return appointmentRepository.save(existingAppointment);
    }

    @Transactional(timeout = 10, readOnly = false)
    public boolean delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada");
        }
        appointmentRepository.deleteById(id);
        return true;
    }

}
