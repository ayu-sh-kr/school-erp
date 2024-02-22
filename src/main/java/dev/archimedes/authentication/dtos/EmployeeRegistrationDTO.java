package dev.archimedes.authentication.dtos;

import dev.archimedes.utils.validators.annotations.ValidEmployee;
import dev.archimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EmployeeRegistrationDTO {

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @ValidRole
    @NotNull
    private String role;

    @ValidEmployee
    @NotNull
    private String employeeType;

    @NotNull
    private String date;
}
