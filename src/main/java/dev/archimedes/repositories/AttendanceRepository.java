package dev.archimedes.repositories;

import dev.archimedes.entities.Attendance;
import dev.archimedes.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    @Query("select count(a) > 0 from Attendance a where a.subject = ?1 and a.standard = ?2 and a.section = ?3 and a.period = ?4 and a.date = ?5")
    boolean isAttendanceMarked(String subject, String standard, String section, Period period, LocalDate date);

    @Query("select count(a) from Attendance a where a.subject = ?1 and a.standard = ?2 and a.section = ?3 and a.period = ?4 and a.date = ?5")
    int markedAttendanceCount(String subject, String standard, String section, Period period, LocalDate date);

    @Query("select a from Attendance a where a.subject = ?1 and a.standard = ?2 and a.period = ?3 and a.date = ?4 and a.section = ?5")
    List<Attendance> findAttendanceToUpdateForAPeriod(String subject, String standard, Period period, LocalDate date, String section);

}
