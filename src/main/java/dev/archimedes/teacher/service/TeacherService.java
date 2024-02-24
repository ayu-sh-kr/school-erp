package dev.archimedes.teacher.service;

import dev.archimedes.entities.Attendance;
import dev.archimedes.repositories.AttendanceRepository;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private  final StudentRepository studentRepository;

    private final AttendanceRepository attendanceRepository;

    public ResponseEntity<?> save(Attendance attendance){
        try {
            attendanceRepository.save(attendance);
            ApiResponse apiResponse = ApiResponse.generateResponse(
                    "Attendance saved successfully", HttpStatus.OK
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
