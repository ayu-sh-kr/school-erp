package dev.archimedes;

import dev.archimedes.global.entities.Employee;
import dev.archimedes.global.enums.EmployeeType;
import dev.archimedes.global.enums.RoleType;
import dev.archimedes.global.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;

import static java.lang.StringTemplate.STR;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SchoolErpApplication implements CommandLineRunner {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.dob}")
    private String adminDOB;

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SchoolErpApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Employee adminEmployee = new Employee();
        adminEmployee.setName(adminName);
        adminEmployee.setEmail(adminEmail);
        adminEmployee.setPassword(passwordEncoder.encode(adminPassword));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        adminEmployee.setDob(dateFormat.parse(adminDOB));
        adminEmployee.setEmployeeType(EmployeeType.ADMIN);
        adminEmployee.setRoleType(RoleType.ADMIN);
        adminEmployee.setCreatedBy(0);
        adminEmployee.setUpdatedBy(0);
        adminEmployee = employeeRepository.save(adminEmployee);
        int id = adminEmployee.getId();
        log.info(String.valueOf(STR."Admin created successfully with id: \{id}"));
    }
}
