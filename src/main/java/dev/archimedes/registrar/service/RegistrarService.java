package dev.archimedes.registrar.service;

import dev.archimedes.entities.Address;
import dev.archimedes.entities.Employee;
import dev.archimedes.entities.Student;
import dev.archimedes.repositories.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;

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

    public ResponseEntity<?> addAddressToStudent(Address address, int registrarId, int studentId){
        try {ApiResponse apiResponse;
            if(studentRepository.existsById(studentId)){
                Student student = studentRepository.getReferenceById(studentId);
                student.addAddress(address);
                student.setUpdatedBy(registrarId);
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

    public ResponseEntity<?> createEmployee(Employee employee, int registrarId){
        try{
            ApiResponse apiResponse;
            if(employeeRepository.existsByEmail(employee.getEmail())){
                apiResponse = ApiResponse.generateResponse(
                        "Email already exist", HttpStatus.BAD_REQUEST
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
            }else {
                employee.setCreatedBy(registrarId);
                employee = employeeRepository.save(employee);
                apiResponse = ApiResponse.generateResponse(
                        "Employee Created", HttpStatus.CREATED
                );
                apiResponse.setObject(employee);
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public ResponseEntity<?> addAddressToEmployee(Address address, int registrarId, int employeeId){
        try {
            ApiResponse apiResponse;
            if(employeeRepository.existsById(employeeId)){
                Employee employee = employeeRepository.getReferenceById(employeeId);
                employee.addAddress(address);
                employee.setUpdatedBy(registrarId);
                apiResponse = ApiResponse.generateResponse(
                        "Address Added", HttpStatus.OK
                );
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            }
            apiResponse = ApiResponse.generateResponse(
                    "Invalid employee id", HttpStatus.BAD_REQUEST
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


}
