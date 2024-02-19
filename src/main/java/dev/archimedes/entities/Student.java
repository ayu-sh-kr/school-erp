package dev.archimedes.entities;

import dev.archimedes.embeddables.Address;
import dev.archimedes.embeddables.Attendance;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BasicRecord{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "student_id")
    private int id;

    @Column(name = "student_name", nullable = false)
    @NotNull
    private String studentName;

    @NotNull
    @Email
    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @NotNull
    @Column(name = "student_father", nullable = false)
    private String fatherName;

    @NotNull
    @Column(name = "student_mother", nullable = false)
    private String motherName;

    @Column(name = "sibling_name")
    private String siblingName;

    @NotNull
    @Column(name = "standard", nullable = false)
    private String standard;

    @NotNull
    @Column(name = "section", nullable = false)
    private String section;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Address> addresses;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "student_attendance",
            joinColumns = @JoinColumn(name = "student_id")
    )
    private List<Attendance> attendances;
}
