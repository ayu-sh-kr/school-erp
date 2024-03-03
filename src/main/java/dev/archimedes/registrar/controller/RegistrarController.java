package dev.archimedes.registrar.controller;

import dev.archimedes.authentication.dtos.EmployeeRegistrationDTO;
import dev.archimedes.converters.AddressConverter;
import dev.archimedes.converters.EmployeeRegistrationConverter;
import dev.archimedes.converters.StudentConverter;
import dev.archimedes.dtos.AddressDTO;
import dev.archimedes.dtos.StudentDTO;
import dev.archimedes.entities.Address;
import dev.archimedes.registrar.service.RegistrarService;
import dev.archimedes.repositories.EmployeeRepository;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.service.contract.EncryptionService;
import dev.archimedes.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrar")
@RequiredArgsConstructor
public class RegistrarController {

    private final RegistrarService registrarService;

    private final AddressConverter addressConverter;

    private final StudentConverter studentConverter;

    private final EmployeeRegistrationConverter employeeRegistrationConverter;

    private final EncryptionService hexEncryptionService;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * This method is used to add an address to a student.
     * It takes the registrarId, studentId and address details as input.
     * It uses the registrarService to add the address to the student.
     *
     * @param registrarId The ID of the registrar who is adding the address.
     * @param studentId The ID of the student to whom the address is being added.
     * @param addressDTO The address details to be added.
     * @return ResponseEntity<?> It returns a response entity with the result of the operation.
     */

    @PutMapping("/student/add-address")
    public ResponseEntity<ApiResponse> addAddress(
            @RequestParam("registrarId") int registrarId, @RequestParam("studentId") int studentId, @RequestBody @Valid AddressDTO addressDTO
    ){
        Address address = addressConverter.reverseConvert(addressDTO, null);
        return registrarService.addAddressToStudent(address, registrarId, studentId);
    }

    @PostMapping("/student/create-student")
    public ResponseEntity<ApiResponse> createStudent(@RequestParam("registrarId") String registrarId, @RequestBody @Valid StudentDTO studentDTO){
        if(studentRepository.existByEmail(studentDTO.getStudentEmail())){
            return new ResponseEntity<>(
                    ApiResponse.generateResponse("Email already exist"), HttpStatus.BAD_REQUEST
            );
        }
        return registrarService.createStudent(
                studentConverter.reverseConvert(studentDTO, null), Integer.parseInt(hexEncryptionService.decrypt(registrarId))
        );
    }

    @PostMapping("/teacher/create-employee")
    public ResponseEntity<ApiResponse> createEmployee(
            @RequestParam("registrarId") String registrarId, @RequestBody @Valid EmployeeRegistrationDTO employeeRegistrationDTO
    ){
        if(employeeRepository.existsByEmail(employeeRegistrationDTO.getEmail())){
            return new ResponseEntity<>(
                    ApiResponse.generateResponse("Email already exist"), HttpStatus.BAD_REQUEST
            );
        }
        return registrarService.createEmployee(
                employeeRegistrationConverter.reverseConvert(employeeRegistrationDTO, null), Integer.parseInt(hexEncryptionService.decrypt(registrarId))
        );
    }

}
