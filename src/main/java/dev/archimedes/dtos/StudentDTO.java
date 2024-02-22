package dev.archimedes.dtos;

import dev.archimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.archimedes.entities.Student}
 */
@Getter @Setter
public class StudentDTO implements Serializable {

    String id;

    @NotNull
    String studentName;

    @NotNull
    @Email
    String studentEmail;

    @NotNull
    String fatherName;

    @NotNull
    String motherName;

    String siblingName;

    @NotNull
    String standard;

    @NotNull
    String section;

    @ValidRole
    String roleType;
}