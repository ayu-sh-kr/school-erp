package dev.archimedes.entities;

import dev.archimedes.embeddables.Address;
import dev.archimedes.embeddables.Attendance;
import dev.archimedes.enums.EmployeeType;
import dev.archimedes.utils.BasicRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BasicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "employee_name", nullable = false)
    @NotNull
    private String name;

    @Email
    @NotNull
    @Column(name = "employee_email", nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    private EmployeeType employeeType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "employee_address",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "employee_attendance",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Attendance> attendances = new ArrayList<>();
}
