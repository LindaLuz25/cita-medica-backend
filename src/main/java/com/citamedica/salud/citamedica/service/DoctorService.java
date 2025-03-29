package com.citamedica.salud.citamedica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.citamedica.salud.citamedica.models.DoctorEntity;
import com.citamedica.salud.citamedica.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional(timeout = 10, readOnly = false)
    public DoctorEntity insert(DoctorEntity entity) {
        if (entity.getName() == null || entity.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del doctor es obligatorio.");
        }
        if (entity.getSpecialty() == null || entity.getSpecialty().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La especialidad del doctor es obligatoria.");
        }
        return doctorRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<DoctorEntity> getAll() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DoctorEntity getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));
    }

    @Transactional(timeout = 10, readOnly = false)
    public DoctorEntity update(Long id, DoctorEntity doctor) {
        var existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado"));

        if (doctor.getName() != null && !doctor.getName().isBlank()) {
            existingDoctor.setName(doctor.getName());
        }
        if (doctor.getSpecialty() != null && !doctor.getSpecialty().isBlank()) {
            existingDoctor.setSpecialty(doctor.getSpecialty());
        }

        return doctorRepository.save(existingDoctor);
    }

    @Transactional(timeout = 10, readOnly = false)
    public boolean delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor no encontrado");
        }
        doctorRepository.deleteById(id);
        return true;
    }
}
