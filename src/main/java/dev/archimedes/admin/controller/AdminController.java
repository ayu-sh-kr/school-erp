package dev.archimedes.admin.controller;

import dev.archimedes.admin.service.AdminService;
import dev.archimedes.authentication.dtos.EmployeeRegistrationDTO;
import dev.archimedes.global.entities.Employee;
import dev.archimedes.global.service.contract.Converter;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final Converter<Employee, EmployeeRegistrationDTO> employeeConverter;

    private final EncryptionService hexEncryptionService;


    @PostMapping("/create-registrar")
    public ResponseEntity<ApiResponse> createRegistrar(@RequestBody EmployeeRegistrationDTO employeeRegistrationDTO, @RequestParam("adminId") String adminId){
        ApiResponse apiResponse = adminService.createRegistrar(
                employeeConverter.reverseConvert(employeeRegistrationDTO, null), Integer.parseInt(hexEncryptionService.decrypt(adminId))
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

}
