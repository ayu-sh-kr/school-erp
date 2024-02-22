package dev.archimedes.authentication.controller;

import dev.archimedes.authentication.dtos.EmployeeRegistrationDTO;
import dev.archimedes.authentication.dtos.LoginRequest;
import dev.archimedes.converters.EmployeeRegistrationConverter;
import dev.archimedes.entities.Employee;
import dev.archimedes.registrar.service.RegistrarService;
import dev.archimedes.repositories.EmployeeRepository;
import dev.archimedes.service.contract.EncryptionService;
import dev.archimedes.service.security.TokenService;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final EncryptionService hexEncryptionService;

    public final AuthenticationManager authenticationManager;

    private final EmployeeRegistrationConverter employeeRegistrationConverter;

    private final RegistrarService registrarService;

    private final TokenService tokenService;

    private final EmployeeRepository employeeRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated EmployeeRegistrationDTO employeeRegistrationDTO){
        Employee employee = employeeRegistrationConverter.reverseConvert(employeeRegistrationDTO, null);
        return registrarService.createEmployee(employee, 1);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest loginRequest){

        Authentication request = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        );
        ApiResponse apiResponse;
        Authentication response = authenticationManager.authenticate(request);
        System.out.println(response.isAuthenticated());
        if(response.isAuthenticated()){
            String token = tokenService.generateToken(response);
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("token", token);
            stringMap.put("userId", employeeRepository.findIdByEmployee_email(loginRequest.getEmail()));
            apiResponse = ApiResponse.generateResponse(
                    "Login successful", HttpStatus.OK
            );
            apiResponse.setObject(stringMap);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        apiResponse = ApiResponse.generateResponse(
                "Invalid Request", HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
