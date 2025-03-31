package com.citamedica.salud.citamedica.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUserRequest(@NotBlank String username,@NotBlank String dni,@NotBlank String name,@NotBlank String email, @NotBlank String password) {

}
