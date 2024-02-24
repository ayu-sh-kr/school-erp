package dev.archimedes.repositories;

import dev.archimedes.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("select (count(s) > 0) from Student s where s.studentEmail = ?1")
    boolean existByEmail(String studentEmail);
}
