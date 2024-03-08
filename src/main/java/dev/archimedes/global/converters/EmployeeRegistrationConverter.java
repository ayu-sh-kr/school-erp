package dev.archimedes.global.converters;

import dev.archimedes.authentication.dtos.EmployeeRegistrationDTO;
import dev.archimedes.global.entities.Employee;
import dev.archimedes.global.enums.EmployeeType;
import dev.archimedes.global.enums.RoleType;
import dev.archimedes.global.repositories.EmployeeRepository;
import dev.archimedes.global.service.contract.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component("EmployeeRegistrationConverter")
@RequiredArgsConstructor
public class EmployeeRegistrationConverter implements Converter<Employee, EmployeeRegistrationDTO> {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeRegistrationDTO convert(Employee employee, EmployeeRegistrationDTO employeeRegistrationDTO) {
        return null;
    }

    @Override
    public Employee reverseConvert(EmployeeRegistrationDTO employeeRegistrationDTO, Employee employee) {
        if(null == employee){
            employee = new Employee();
        }

        employee.setName(employeeRegistrationDTO.getName());
        employee.setEmail(employeeRegistrationDTO.getEmail());
        employee.setPassword(employeeRegistrationDTO.getPassword());
        employee.setPassword(passwordEncoder.encode(employeeRegistrationDTO.getPassword()));
        employee.setRoleType(RoleType.valueOf(employeeRegistrationDTO.getRole().toUpperCase()));
        employee.setEmployeeType(EmployeeType.valueOf(employeeRegistrationDTO.getEmployeeType().toUpperCase()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            employee.setDob(dateFormat.parse(employeeRegistrationDTO.getDate()));
        } catch (ParseException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return employee;
    }
}
