package com.citamedica.salud.citamedica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citamedica.salud.citamedica.models.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {

}
