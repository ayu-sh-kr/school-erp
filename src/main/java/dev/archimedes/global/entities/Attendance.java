package dev.archimedes.global.entities;

import dev.archimedes.global.enums.AttendanceType;
import dev.archimedes.global.enums.Period;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean present;

    private String standard;

    private String section;

    @Enumerated(EnumType.STRING)
    private Period period;

    private String subject;

    private AttendanceType attendanceType;

    private Integer teacherId;

    private LocalDate date;

    @ManyToOne
    private Student student;
}
