package dev.archimedes.teacher.service;

import dev.archimedes.entities.Attendance;
import dev.archimedes.entities.Student;
import dev.archimedes.enums.AttendanceType;
import dev.archimedes.enums.Period;
import dev.archimedes.repositories.AttendanceRepository;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.teacher.dto.AttendanceSearchParam;
import dev.archimedes.teacher.exception.AttendanceException;
import dev.archimedes.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
                    "Attendance saved successfully"
            );
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public List<Attendance> fetchData(String standard, String section, String subject, Period period, int teacherId, LocalDate date) {

        System.out.println(attendanceRepository.markedAttendanceCount(subject, standard, section, period, date));
        System.out.println(STR."Standard: \{standard} Section: \{section} Subject: \{subject} Period: \{period.name()} Date: \{date}");

        if(attendanceRepository.isAttendanceMarked(subject, standard, section, period, date)){
            throw new AttendanceException("Attendance Already Marked");
        }

        List<Student> students = studentRepository.findStudentsByStandardAndSection(standard, section);

        List<Attendance> attendances = new ArrayList<>();
        for (Student student: students){
            Attendance attendance = new Attendance();
            attendance.setStandard(standard);
            attendance.setSection(section);
            attendance.setSubject(subject);
            attendance.setPeriod(period);
            attendance.setDate(LocalDate.now());
            attendance.setTeacherId(teacherId);
            attendance.setAttendanceType(AttendanceType.SUBJECT);
            attendance.setStudent(student);
            attendances.add(attendance);
        }

        return attendances;
    }

    public boolean updateAttendance(List<Attendance> attendances){
        try {
            for(Attendance attendance: attendances){
                if(attendanceRepository.existsById(attendance.getId())){
                    attendanceRepository.save(attendance);
                }
            }
            return true;
        }catch (Exception e){
            throw new AttendanceException("Unexpected error during updating attendance");
        }
    }

    public ResponseEntity<?> saveAllAttendances(List<Attendance> attendances, AttendanceSearchParam searchParam) {
        if (!attendanceRepository.isAttendanceMarked(searchParam.getSubject(), searchParam.getStandard(),
                searchParam.getSection(), searchParam.getPeriod(), searchParam.getDate())
        ){
            attendanceRepository.saveAll(attendances);
        }else {
            throw new AttendanceException("Attempt to update marked attendance is not allowed please try via update attendance");
        }

        return new ResponseEntity<>(ApiResponse.generateResponse("Successfully Marked"), HttpStatus.OK);
    }

    public List<Attendance> getMarkedAttendance(AttendanceSearchParam searchParam){
        return attendanceRepository.findAttendanceToUpdateForAPeriod(
                searchParam.getSubject(), searchParam.getStandard(),
                searchParam.getPeriod(), searchParam.getDate(), searchParam.getSection());
    }
}
