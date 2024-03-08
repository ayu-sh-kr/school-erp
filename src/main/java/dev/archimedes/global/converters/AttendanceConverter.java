package dev.archimedes.global.converters;

import dev.archimedes.global.dtos.AttendanceDTO;
import dev.archimedes.global.entities.Attendance;
import dev.archimedes.global.enums.AttendanceType;
import dev.archimedes.global.enums.Period;
import dev.archimedes.global.repositories.AttendanceRepository;
import dev.archimedes.global.repositories.StudentRepository;
import dev.archimedes.global.service.contract.Converter;
import dev.archimedes.global.service.contract.EncryptionService;
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

        attendanceDTO.setName(attendance.getStudent().getStudentName());
        attendanceDTO.setPresent(attendance.isPresent());
        attendanceDTO.setStandard(attendance.getStandard());
        attendanceDTO.setSection(attendance.getSection());
        attendanceDTO.setPeriod(attendance.getPeriod().name());
        attendanceDTO.setSubject(attendance.getSubject());

        if(null != attendance.getAttendanceType()){
            attendanceDTO.setAttendanceType(attendance.getAttendanceType().name());
        }

        attendanceDTO.setTeacherId(
                hexEncryptionService.encrypt(String.valueOf(attendance.getTeacherId()))
        );

        attendanceDTO.setDate(attendance.getDate());

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

        attendance.setPresent(attendanceDTO.isPresent());
        attendance.setStandard(attendanceDTO.getStandard());
        attendance.setSection(attendanceDTO.getSection());
        attendance.setPeriod(Period.valueOf(attendanceDTO.getPeriod()));
        attendance.setSubject(attendanceDTO.getSubject());

        attendance.setAttendanceType(
                AttendanceType.valueOf(attendanceDTO.getAttendanceType().toUpperCase())
        );

        attendance.setDate(attendanceDTO.getDate());

        attendance.setTeacherId(
                Integer.parseInt(hexEncryptionService.decrypt(attendanceDTO.getTeacherId()))
        );

        attendance.setStudent(
                studentRepository.getReferenceById(
                        Integer.valueOf(hexEncryptionService.decrypt(attendanceDTO.getStudentId()))
                )
        );

        return attendance;
    }
}
