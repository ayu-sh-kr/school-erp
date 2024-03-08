package dev.archimedes.global.repositories;

import dev.archimedes.global.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);

    Employee findByEmail(String username);

    @Query("select e.id from Employee e where e.email = ?1")
    Integer findIdByEmployee_email(String email);
}
