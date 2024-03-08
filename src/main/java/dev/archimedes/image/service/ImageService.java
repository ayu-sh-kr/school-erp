package dev.archimedes.image.service;

import dev.archimedes.image.entity.Image;
import dev.archimedes.image.exception.ImageProcessingException;
import dev.archimedes.image.repository.ImageRepository;
import dev.archimedes.image.utils.ImageUtils;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.utils.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    private final EncryptionService hexEncryptionService;

    public ApiResponse save(MultipartFile image){
        try {
            Image image1 = imageRepository.save(
                    Image.builder()
                    .fileName(image.getOriginalFilename())
                    .extension(image.getContentType())
                    .size(image.getSize())
                    .fileData(ImageUtils.compressImage(image.getBytes()))
                    .build()
            );
            return ApiResponse.builder()
                    .message("Image saved successfully")
                    .date(new Date())
                    .urlPath("/image/save")
                    .object(STR."image saved with id: \{hexEncryptionService.encrypt(String.valueOf(image1.getId()))}")
                    .build();
        }catch (IOException ioException){
            throw new ImageProcessingException(ioException.getLocalizedMessage());
        }

    }

    @Transactional
    public ResponseEntity<?> getById(int id){
        if(imageRepository.existsById(id)){
            Image image = imageRepository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(image.getExtension()))
                    .body(ImageUtils.decompressImage(image.getFileData()));
        }
        return new ResponseEntity<>(ApiResponse.builder()
                .message(STR."Invalid Id \{id}")
                .date(new Date())
                .urlPath("/image/get")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
