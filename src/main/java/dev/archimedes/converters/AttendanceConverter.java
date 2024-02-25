package dev.archimedes.converters;

import dev.archimedes.dtos.AttendanceDTO;
import dev.archimedes.entities.Attendance;
import dev.archimedes.enums.AttendanceType;
import dev.archimedes.repositories.AttendanceRepository;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.service.contract.Converter;
import dev.archimedes.service.contract.EncryptionService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("AttendanceConverter")
@RequiredArgsConstructor
public class AttendanceConverter implements Converter<Attendance, AttendanceDTO> {

    private final EncryptionService hexEncryptionService;

    private final AttendanceRepository attendanceRepository;

    private final StudentRepository studentRepository;
    @Override
    public AttendanceDTO convert(Attendance attendance, AttendanceDTO attendanceDTO) {

        if (null == attendanceDTO){
            attendanceDTO = new AttendanceDTO();
        }

        if(null != attendance.getId()){
            attendanceDTO.setId(
                    hexEncryptionService.encrypt(String.valueOf(attendance.getId()))
            );
        }

        attendanceDTO.setDate(attendance.getDate());
        attendanceDTO.setPresent(attendance.isPresent());

        attendanceDTO.setSubject(attendance.getSubject());
        attendanceDTO.setTeacherId(
                hexEncryptionService.encrypt(String.valueOf(attendance.getTeacherId()))
        );

        if(null != attendance.getAttendanceType()){
            attendanceDTO.setAttendanceType(attendance.getAttendanceType().name());
        }

        if(null != attendance.getStudent()){
            attendanceDTO.setStudentId(
                    hexEncryptionService.encrypt(String.valueOf(attendance.getStudent().getId()))
            );
        }

        return attendanceDTO;
    }

    @Override
    public Attendance reverseConvert(AttendanceDTO attendanceDTO, Attendance attendance) {

        if(null == attendance){
            attendance = new Attendance();
        }

        if(StringUtils.isNotBlank(attendanceDTO.getId())){
            attendance.setId(
                    Integer.valueOf(hexEncryptionService.decrypt(attendanceDTO.getId()))
            );
        }

        attendance.setDate(attendanceDTO.getDate());
        attendance.setPresent(attendanceDTO.isPresent());
        attendance.setSubject(attendanceDTO.getSubject());

        attendance.setTeacherId(
                Integer.parseInt(hexEncryptionService.decrypt(attendanceDTO.getTeacherId()))
        );

        attendance.setAttendanceType(
                AttendanceType.valueOf(attendanceDTO.getAttendanceType().toUpperCase())
        );

        if(StringUtils.isNotBlank(attendanceDTO.getStudentId())){
            int id = Integer.parseInt(hexEncryptionService.decrypt(attendanceDTO.getStudentId()));
            if(studentRepository.existsById(id)){
                attendance.setStudent(studentRepository.getReferenceById(id));
            }
        }

        return attendance;
    }
}
