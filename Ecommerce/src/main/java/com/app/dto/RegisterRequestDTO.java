package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @NotNull
    private String password;
}
