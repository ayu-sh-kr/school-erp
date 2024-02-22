package dev.archimedes.authentication.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email
    private String email;
    @NotNull
    private String password;
}
