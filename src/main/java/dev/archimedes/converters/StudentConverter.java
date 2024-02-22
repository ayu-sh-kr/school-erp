package dev.archimedes.converters;

import dev.archimedes.dtos.StudentDTO;
import dev.archimedes.entities.Student;
import dev.archimedes.enums.RoleType;
import dev.archimedes.repositories.StudentRepository;
import dev.archimedes.service.contract.Converter;
import dev.archimedes.service.contract.EncryptionService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("StudentConverter")
@RequiredArgsConstructor
public class StudentConverter implements Converter<Student, StudentDTO> {

    private final StudentRepository studentRepository;

    private final EncryptionService hexEncryptionService;

    @Override
    public StudentDTO convert(Student student, StudentDTO studentDTO) {

        if(null == studentDTO){
            studentDTO = new StudentDTO();
        }

        if(null != student.getId()){
            studentDTO.setId(
                    hexEncryptionService.encrypt(String.valueOf(student.getId()))
            );
        }

        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentEmail(student.getStudentEmail());
        studentDTO.setFatherName(student.getFatherName());
        studentDTO.setMotherName(student.getMotherName());
        studentDTO.setSiblingName(student.getSiblingName());
        studentDTO.setStandard(student.getStandard());
        studentDTO.setSection(student.getSection());
        studentDTO.setRoleType(student.getRoleType().name());

        return studentDTO;
    }

    @Override
    public Student reverseConvert(StudentDTO studentDTO, Student student) {

        if(null == student){
            student = new Student();
        }

        if(StringUtils.isNotBlank(studentDTO.getId())){
            student.setId(
                    Integer.valueOf(hexEncryptionService.decrypt(studentDTO.getId()))
            );
        }

        student.setStudentName(studentDTO.getStudentName());
        student.setStudentEmail(studentDTO.getStudentEmail());
        student.setFatherName(studentDTO.getFatherName());
        student.setMotherName(studentDTO.getMotherName());
        student.setSiblingName(studentDTO.getSiblingName());
        student.setStandard(studentDTO.getStandard());
        student.setSection(studentDTO.getSection());

        student.setRoleType(
                RoleType.valueOf(studentDTO.getRoleType().toUpperCase())
        );

        student = studentRepository.save(student);
        return student;
    }
}
