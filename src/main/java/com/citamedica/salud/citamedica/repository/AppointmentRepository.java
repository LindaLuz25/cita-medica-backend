package com.citamedica.salud.citamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citamedica.salud.citamedica.models.Appointment;
import com.citamedica.salud.citamedica.models.UserEntity;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{
List<Appointment> findByUser(UserEntity user);
}
