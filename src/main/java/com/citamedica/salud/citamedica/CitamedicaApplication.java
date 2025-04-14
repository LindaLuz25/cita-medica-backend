package com.citamedica.salud.citamedica;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.citamedica.salud.citamedica.models.DoctorEntity;
import com.citamedica.salud.citamedica.models.RoleEntity;
import com.citamedica.salud.citamedica.models.RoleEnum;
import com.citamedica.salud.citamedica.models.UserEntity;
import com.citamedica.salud.citamedica.repository.AppointmentRepository;
import com.citamedica.salud.citamedica.repository.DoctorRepository;
import com.citamedica.salud.citamedica.repository.RoleRepository;
import com.citamedica.salud.citamedica.repository.UserRepository;

@SpringBootApplication
public class CitamedicaApplication {

	private final DoctorRepository doctorRepository;
	private final RoleRepository roleRepository;

	CitamedicaApplication(DoctorRepository doctorRepository, RoleRepository roleRepository) {
		this.doctorRepository = doctorRepository;
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CitamedicaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			RoleEntity rolePatient = RoleEntity.builder().roleEnum(RoleEnum.PATIENT).build();
			RoleEntity roleAdmin = RoleEntity.builder().roleEnum(RoleEnum.ADMIN).build();
			RoleEntity roleInvited = RoleEntity.builder().roleEnum(RoleEnum.INVITED).build();
			roleRepository.saveAll(List.of(rolePatient,roleAdmin,roleInvited));


			DoctorEntity doctor = DoctorEntity.builder().name("Dr. Juan Perez").specialty("Cardiology").build();
			doctorRepository.save(doctor);
			

		};
	}

}
