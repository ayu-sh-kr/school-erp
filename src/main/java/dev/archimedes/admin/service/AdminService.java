package dev.archimedes.admin.service;

import dev.archimedes.global.entities.Employee;
import dev.archimedes.global.repositories.EmployeeRepository;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmployeeRepository employeeRepository;

    private final EncryptionService hexEncryptionService;

    public ApiResponse createRegistrar(Employee employee, int adminId){
        employee.setCreatedBy(adminId);
        employee.setCreatedAt(new Date());
        employee = employeeRepository.save(employee);
        return ApiResponse.builder()
                .message(STR."Registrar created with id: \{hexEncryptionService
                        .encrypt(String.valueOf(employee.getId()))
                        }")
                .date(new Date())
                .urlPath("/api/admin/create-registrar")
                .build();
    }


}
