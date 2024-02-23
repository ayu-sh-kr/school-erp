package dev.archimedes.authentication.dtos;

import dev.archimedes.utils.validators.annotations.ValidEmployee;
import dev.archimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmployeeRegistrationDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @ValidRole
    @NotBlank
    private String role;

    @ValidEmployee
    @NotBlank
    private String employeeType;

    @NotBlank
    private String date;
}
