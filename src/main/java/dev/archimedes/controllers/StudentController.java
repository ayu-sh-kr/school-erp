package dev.archimedes.controllers;

import dev.archimedes.entities.Student;
import dev.archimedes.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody @Validated Student student, @RequestParam("id") int id){
        studentRepository.save(student);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
