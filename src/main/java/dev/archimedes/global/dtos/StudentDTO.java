package dev.archimedes.global.dtos;

import dev.archimedes.global.entities.Student;
import dev.archimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
@Getter @Setter
public class StudentDTO implements Serializable {

    String id;

    @NotBlank
    String studentName;

    @NotBlank
    @Email
    String studentEmail;

    @NotBlank
    String fatherName;

    @NotBlank
    String motherName;

    String siblingName;

    @NotBlank
    String standard;

    @NotBlank
    String section;

    @ValidRole @NotBlank
    String roleType;
}