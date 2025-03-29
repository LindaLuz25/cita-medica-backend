package com.citamedica.salud.citamedica.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.citamedica.salud.citamedica.models.*;
import com.citamedica.salud.citamedica.repository.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserEntity create(UserEntity user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Buscar si el rol "PATIENT" existe en la base de datos
        RoleEntity patientRole = roleRepository.findByRoleEnum(RoleEnum.PATIENT)
                .orElseGet(() -> {
                    // Si no existe, lo creamos y guardamos en la base de datos
                    RoleEntity newRole = new RoleEntity();
                    newRole.setRoleEnum(RoleEnum.PATIENT);
                    return roleRepository.save(newRole);
                });

        // Agregar el rol al usuario
        user.getRoles().add(patientRole);

        return userRepository.save(user);
    }

    @Transactional(timeout = 10, readOnly = false)
    public UserEntity update(Long id, UserEntity user) {
        var existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        existingUser.setDni(user.getDni());
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword())); 
                                                                                  
        }

        userRepository.save(existingUser);
        return existingUser;
    }

    @Transactional(timeout = 10, readOnly = false)
    public boolean delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        userRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

}
