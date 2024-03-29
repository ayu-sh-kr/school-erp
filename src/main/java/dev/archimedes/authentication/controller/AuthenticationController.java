package dev.archimedes.authentication.controller;

import dev.archimedes.authentication.dtos.EmployeeRegistrationDTO;
import dev.archimedes.authentication.dtos.LoginRequest;
import dev.archimedes.global.converters.EmployeeRegistrationConverter;
import dev.archimedes.global.entities.Employee;
import dev.archimedes.registrar.service.RegistrarService;
import dev.archimedes.global.repositories.EmployeeRepository;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.global.service.security.TokenService;
import dev.archimedes.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid EmployeeRegistrationDTO employeeRegistrationDTO){
        Employee employee = employeeRegistrationConverter.reverseConvert(employeeRegistrationDTO, null);
        return registrarService.createEmployee(employee, 1);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest){

        Authentication request = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()
        );
        ApiResponse apiResponse;
        Authentication response = authenticationManager.authenticate(request);
        if(response.isAuthenticated()){
            String token = tokenService.generateToken(response);
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("token", token);
            stringMap.put("userId", hexEncryptionService.encrypt(
                    String.valueOf(employeeRepository.findIdByEmployee_email(loginRequest.getEmail()))
            ));
            apiResponse = ApiResponse.generateResponse("Login successful");
            apiResponse.setObject(stringMap);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }
        apiResponse = ApiResponse.generateResponse("Invalid Request");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
