package dev.archimedes.global.dtos;

import dev.archimedes.global.entities.Attendance;
import dev.archimedes.utils.validators.annotations.ValidAttendance;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Attendance}
 */
@Getter @Setter
public class AttendanceDTO implements Serializable {

    String id;
    private boolean present;

    @NotBlank
    private String name;

    private String roll;

    @NotBlank
    private String standard;

    @NotBlank
    private String section;

    @NotBlank
    private String period;

    @NotBlank
    private String subject;;

    @NotBlank
    private String teacherId;

    @ValidAttendance
    private String attendanceType;

    private LocalDate date;

    @NotBlank
    private String studentId;

}