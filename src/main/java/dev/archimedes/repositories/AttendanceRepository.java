package dev.archimedes.repositories;

import dev.archimedes.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("select a from Attendance a where a.subject = ?1 and a.date = ?2 and a.student.standard = ?3")
    List<Attendance> findAttendanceByCodeAndDateAndStandard(String subject, Date date, String standard);
}
