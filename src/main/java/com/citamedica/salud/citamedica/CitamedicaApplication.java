package com.citamedica.salud.citamedica;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.citamedica.salud.citamedica.models.RoleEntity;
import com.citamedica.salud.citamedica.models.RoleEnum;
import com.citamedica.salud.citamedica.models.UserEntity;
import com.citamedica.salud.citamedica.repository.UserRepository;

@SpringBootApplication
public class CitamedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitamedicaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args ->{
			RoleEntity rolePatient = RoleEntity.builder().roleEnum(RoleEnum.PATIENT).build();

			UserEntity userLinda = UserEntity.builder().dni("12345678").name("Linda Plasencia").username("linda").email("linda@email.com").password("$2a$10$8ZYpRN.oJz6xbn5PgcIJ8.wOrtcZMV0BlFlYfChA91bm91NiTO4LS").roles(Set.of(rolePatient)).build();

			UserEntity userJesus = UserEntity.builder().dni("87654321").name("Jesus Plasencia").email("jesus@example.com").username("jesus").password("$2a$10$8ZYpRN.oJz6xbn5PgcIJ8.wOrtcZMV0BlFlYfChA91bm91NiTO4LS").roles(Set.of(rolePatient)).build();

			userRepository.saveAll(List.of(userJesus,userLinda));
		};
	}

}
