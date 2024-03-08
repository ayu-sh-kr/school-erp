package dev.archimedes.global.repositories;

import dev.archimedes.global.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("select (count(s) > 0) from Student s where s.studentEmail = ?1")
    boolean existByEmail(String studentEmail);

    @Query("select s from Student s where s.standard = ?1 and s.section = ?2")
    List<Student> findStudentsByStandardAndSection(String standard, String section);
}
