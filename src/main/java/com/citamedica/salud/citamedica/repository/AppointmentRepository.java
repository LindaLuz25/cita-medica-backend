package com.citamedica.salud.citamedica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citamedica.salud.citamedica.models.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{

}
