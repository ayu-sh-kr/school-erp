package dev.archimedes.image.controller;

import dev.archimedes.image.service.ImageService;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    private final EncryptionService hexEncryptionService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam("image")MultipartFile image){
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getImage(@PathVariable("id") String id){
        log.info(STR."id: \{id}");
        log.info(STR."decrypted: \{hexEncryptionService.decrypt(id)}");
        return imageService.getById(Integer.parseInt(hexEncryptionService.decrypt(id)));
    }
}
