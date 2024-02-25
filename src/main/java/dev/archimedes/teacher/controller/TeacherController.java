package dev.archimedes.teacher.controller;

import dev.archimedes.converters.AttendanceConverter;
import dev.archimedes.dtos.AttendanceDTO;
import dev.archimedes.entities.Attendance;
import dev.archimedes.repositories.AttendanceRepository;
import dev.archimedes.service.contract.EncryptionService;
import dev.archimedes.teacher.service.TeacherService;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final AttendanceRepository attendanceRepository;

    private final TeacherService teacherService;

    private final AttendanceConverter attendanceConverter;

    private final EncryptionService hexEncryptionService;

    @GetMapping("/fetch-data")
    public ResponseEntity<?> fetchStudentToMarkAttendance(
            @RequestParam("standard") String standard, @RequestParam("section") String section,
            @RequestParam("subject") String subject, @RequestParam("teacherId") String teacherId
            ) {
        List<Attendance> attendances = teacherService.fetchData(standard, section, subject, Integer.parseInt(hexEncryptionService.decrypt(teacherId)));
        ApiResponse apiResponse = ApiResponse.generateResponse("Student fetched successfully", HttpStatus.OK);
        apiResponse.setObject(getAttendanceDTOS(attendances, new ArrayList<>()));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/save-attendance")
    public ResponseEntity<?> saveAttendanceToStudent(@RequestBody List<AttendanceDTO> attendanceDTOS){
        return teacherService.saveAllAttendances(getAttendances(attendanceDTOS, new ArrayList<>()));
    }

    List<AttendanceDTO> getAttendanceDTOS(List<Attendance> attendances, List<AttendanceDTO> attendanceDTOS){
        for(Attendance attendance: attendances){
            attendanceDTOS.add(
                    attendanceConverter.convert(attendance, null)
            );
        }
        return attendanceDTOS;
    }

    List<Attendance> getAttendances(List<AttendanceDTO> attendanceDTOS, List<Attendance> attendances){
        for (AttendanceDTO attendanceDTO: attendanceDTOS){
            attendances.add(
                    attendanceConverter.reverseConvert(attendanceDTO, null)
            );
        }
        return attendances;
    }
}
