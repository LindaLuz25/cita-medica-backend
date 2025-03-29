package com.citamedica.salud.citamedica.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username,
        @NotBlank String password) {

}
