package dev.archimedes.registrar.service;

import dev.archimedes.embeddables.Address;
import dev.archimedes.entities.Student;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegistrarService {

    private final StudentRepository studentRepository;

    public ResponseEntity<?> createStudent(@Validated Student student, int id){
        try {
            student.setCreatedBy(id);
            studentRepository.save(student);
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("Student Created Successfully")
                            .date(new Date())
                            .httpStatus(HttpStatus.CREATED)
                    .build(),
                    HttpStatus.CREATED
            );
        }catch (Exception e){
            throw  new RuntimeException(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> addAddress(Address address, int id, int studentId){
        try {ApiResponse apiResponse;
            if(studentRepository.existsById(studentId)){
                Student student = studentRepository.getReferenceById(studentId);
                student.addAddress(address);
                student.setUpdatedBy(id);
                studentRepository.save(student);
                apiResponse = ApiResponse.generateResponse(
                        "Address Added",
                        HttpStatus.OK
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }
            throw new RuntimeException("Invalid Request");
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
