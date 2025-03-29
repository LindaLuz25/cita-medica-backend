package com.citamedica.salud.citamedica.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "message", "status", "jwt" })
public record AuthResponse(
        String username,
        String message,
        String jwt,
        boolean status) {

}
