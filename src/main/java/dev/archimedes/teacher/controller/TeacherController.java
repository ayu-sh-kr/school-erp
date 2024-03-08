package dev.archimedes.teacher.controller;

import dev.archimedes.global.converters.AttendanceConverter;
import dev.archimedes.global.dtos.AttendanceDTO;
import dev.archimedes.global.entities.Attendance;
import dev.archimedes.global.enums.Period;
import dev.archimedes.global.repositories.AttendanceRepository;
import dev.archimedes.global.service.contract.EncryptionService;
import dev.archimedes.teacher.dto.AttendanceSearchParam;
import dev.archimedes.teacher.service.TeacherService;
import dev.archimedes.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<?> fetchStudentToMarkAttendance(@RequestBody AttendanceSearchParam attendanceSearchParam) {

        List<Attendance> attendances = teacherService.fetchData(
                attendanceSearchParam.getStandard(), attendanceSearchParam.getSection(),
                attendanceSearchParam.getSubject(), attendanceSearchParam.getPeriod(),
                Integer.parseInt(hexEncryptionService.decrypt(attendanceSearchParam.getTeacherId())), attendanceSearchParam.getDate()
        );

        ApiResponse apiResponse = ApiResponse.generateResponse("Student fetched successfully");
        apiResponse.setObject(
                attendances.stream()
                        .map(attendance -> attendanceConverter.convert(attendance, null))
                        .toList()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/save-attendance")
    public ResponseEntity<?> saveAttendanceToStudent(@RequestBody List<AttendanceDTO> attendanceDTOS,
                                                     @RequestParam("standard") String standard,
                                                     @RequestParam("section") String section,
                                                     @RequestParam("date") LocalDate date,
                                                     @RequestParam("subject") String subject,
                                                     @RequestParam("period") Period period
    ) {
        return teacherService.saveAllAttendances(getAttendances(attendanceDTOS, new ArrayList<>()), new AttendanceSearchParam(subject, standard, section, period, date, null));
    }

    @GetMapping("/fetch-marked")
    public ApiResponse findAttendanceToUpdate(@RequestBody @Valid AttendanceSearchParam searchParam) {
        ApiResponse apiResponse = ApiResponse.generateResponse("fetching searched attendance");
        apiResponse.setObject(
                teacherService.getMarkedAttendance(searchParam)
                        .stream()
                        .map(attendance -> attendanceConverter.convert(attendance, null))
                        .toList()
        );
        return apiResponse;
    }

    @PatchMapping("/update-marked")
    public ApiResponse updateAttendanceInBulk(@RequestBody List<AttendanceDTO> attendanceDTOS) {
        boolean updated = teacherService.updateAttendance(getAttendances(attendanceDTOS, new ArrayList<>()));

        if (updated) {
            return ApiResponse.generateResponse("Updated Successfully");
        }
        return ApiResponse.generateResponse("Error");
    }

    List<AttendanceDTO> getAttendanceDTOS(List<Attendance> attendances, List<AttendanceDTO> attendanceDTOS) {
        for (Attendance attendance : attendances) {
            attendanceDTOS.add(
                    attendanceConverter.convert(attendance, null)
            );
        }
        return attendanceDTOS;
    }

    List<Attendance> getAttendances(List<AttendanceDTO> attendanceDTOS, List<Attendance> attendances) {
        for (AttendanceDTO attendanceDTO : attendanceDTOS) {
            attendances.add(
                    attendanceConverter.reverseConvert(attendanceDTO, null)
            );
        }
        return attendances;
    }
}
