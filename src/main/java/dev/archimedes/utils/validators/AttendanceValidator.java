package dev.archimedes.utils.validators;

import dev.archimedes.enums.AttendanceType;
import dev.archimedes.utils.validators.annotations.ValidAttendance;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AttendanceValidator implements ConstraintValidator<ValidAttendance, String> {
    @Override
    public void initialize(ValidAttendance constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String attendance, ConstraintValidatorContext constraintValidatorContext) {
        for (AttendanceType attendanceType: AttendanceType.values()){
            if(attendanceType.name().equals(attendance.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
