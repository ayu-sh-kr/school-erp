package dev.archimedes.registrar.controller;

import dev.archimedes.converters.AddressConverter;
import dev.archimedes.dtos.AddressDTO;
import dev.archimedes.dtos.StudentDTO;
import dev.archimedes.entities.Address;
import dev.archimedes.registrar.service.RegistrarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrar")
@RequiredArgsConstructor
public class RegistrarController {

    private final RegistrarService registrarService;

    private final AddressConverter addressConverter;

    @PostMapping("/student/add-address")
    public ResponseEntity<?> addAddress(
            @RequestParam("registrarId") int registrarId, @RequestParam("studentId") int studentId, @RequestBody @Validated AddressDTO addressDTO
            ){
        Address address = addressConverter.reverseConvert(addressDTO, null);
        return registrarService.addAddressToStudent(address, registrarId, studentId);
    }

    @PostMapping("/student/create-student")
    public ResponseEntity<?> createStudent(@RequestParam("registrarId") int registrarId, @RequestBody @Validated StudentDTO studentDTO){
        return null;
    }


}
