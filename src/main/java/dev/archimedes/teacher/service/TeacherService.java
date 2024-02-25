package dev.archimedes.teacher.service;

import dev.archimedes.entities.Attendance;
import dev.archimedes.entities.Student;
import dev.archimedes.enums.AttendanceType;
import dev.archimedes.repositories.AttendanceRepository;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Attendance> fetchData(String standard, String section, String subject, int teacherId) {
        List<Student> students = studentRepository.findStudentsByStandardAndSection(standard, section);
        List<Attendance> attendances = new ArrayList<>();
        for (Student student: students){
            Attendance attendance = new Attendance();
            attendance.setDate(new Date());
            attendance.setSubject(subject);
            attendance.setTeacherId(teacherId);
            attendance.setAttendanceType(AttendanceType.SUBJECT);
            attendance.setStudent(student);
            attendances.add(attendance);
        }
        return attendances;
    }

    public ResponseEntity<?> saveAllAttendances(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
        return new ResponseEntity<>(ApiResponse.generateResponse("Successfully Marked", HttpStatus.OK), HttpStatus.OK);
    }
}
