package dev.archimedes.repositories;

import dev.archimedes.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(String email);

    Employee findByEmail(String username);

    @Query("select e.email from Employee e where e.email = ?1")
    String findIdByEmployee_email(String email);
}
