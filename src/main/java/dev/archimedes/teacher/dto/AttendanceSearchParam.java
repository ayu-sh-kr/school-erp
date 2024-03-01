package dev.archimedes.teacher.dto;

import dev.archimedes.enums.Period;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceSearchParam {

    @NotBlank
    String subject;

    @NotBlank
    String standard;

    @NotBlank
    String section;

    Period period;

    LocalDate date;

    @NotBlank
    String teacherId;
}
