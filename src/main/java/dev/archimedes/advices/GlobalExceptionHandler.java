package dev.archimedes.advices;

import dev.archimedes.teacher.exception.AttendanceException;
import dev.archimedes.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> customValidationException(
            MethodArgumentNotValidException argumentNotValidException,
            HttpServletRequest webRequest
    ){
        Map<String, Object> errors = new HashMap<>();
        argumentNotValidException.getBindingResult().getFieldErrors()
                .forEach(
                        fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
                );

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Validation Errors")
                .urlPath(webRequest.getRequestURI())
                .date(new Date())
                .object(errors)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> messageNotReadableException(
            HttpMessageNotReadableException messageNotReadableException,
            HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(ApiResponse.builder()
                .message("HTTP message not readable")
                .urlPath(httpServletRequest.getRequestURI())
                .date(new Date())
                .object(messageNotReadableException.getLocalizedMessage())
                .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgumentException(
            IllegalArgumentException argumentException, HttpServletRequest servletRequest){
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Illegal Argument Exception")
                        .urlPath(servletRequest.getRequestURI())
                        .object(argumentException.getLocalizedMessage())
                        .date(new Date())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AttendanceException.class)
    public ResponseEntity<ApiResponse> attendanceException(AttendanceException attendanceException, HttpServletRequest servletRequest){
        return new ResponseEntity<>(ApiResponse.builder()
                .message(attendanceException.getLocalizedMessage())
                .urlPath(servletRequest.getRequestURI())
                .date(new Date())
                .object(null)
                .build(), HttpStatus.BAD_REQUEST
        );
    }
}
