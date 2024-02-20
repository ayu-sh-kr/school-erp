package dev.archimedes.advices;

import dev.archimedes.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customValidationException(
            MethodArgumentNotValidException argumentNotValidException,
            WebRequest webRequest
    ){
        Map<String, Object> errors = new HashMap<>();
        argumentNotValidException.getBindingResult().getFieldErrors()
                .forEach(
                        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
                );

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Validation Errors")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .urlPath(webRequest.getContextPath())
                .date(new Date())
                .object(errors)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
