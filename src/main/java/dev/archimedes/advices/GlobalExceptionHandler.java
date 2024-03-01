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
    public ResponseEntity<?> customValidationException(
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
                .httpStatus(HttpStatus.BAD_REQUEST)
                .urlPath(webRequest.getRequestURI())
                .date(new Date())
                .object(errors)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse messageNotReadableException(
            HttpMessageNotReadableException messageNotReadableException,
            HttpServletRequest httpServletRequest){
        return ApiResponse.builder()
                .message("HTTP message not readable")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .urlPath(httpServletRequest.getRequestURI())
                .date(new Date())
                .object(messageNotReadableException.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse illegalArgumentException(
            IllegalArgumentException argumentException, HttpServletRequest servletRequest){
        return ApiResponse.builder()
                .message("Illegal Argument Exception")
                .urlPath(servletRequest.getRequestURI())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .object(argumentException.getLocalizedMessage())
                .date(new Date())
                .build();
    }

    @ExceptionHandler(AttendanceException.class)
    public ResponseEntity<?> attendanceException(AttendanceException attendanceException, HttpServletRequest servletRequest){
        return new ResponseEntity<>(ApiResponse.builder()
                .message(attendanceException.getLocalizedMessage())
                .urlPath(servletRequest.getRequestURI())
                .date(new Date())
                .object(null)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build()
                , HttpStatus.BAD_REQUEST);
    }
}
