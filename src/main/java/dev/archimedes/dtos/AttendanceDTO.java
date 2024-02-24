package dev.archimedes.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.archimedes.utils.validators.annotations.ValidAttendance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link dev.archimedes.entities.Attendance}
 */
@Getter @Setter
public class AttendanceDTO implements Serializable {

    String id;
    boolean present = false;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    Date date;

    @NotBlank
    String subject;

    @NotBlank
    String teacherId;

    @NotBlank
    @ValidAttendance
    String attendanceType;

    @NotBlank
    String studentId;
}