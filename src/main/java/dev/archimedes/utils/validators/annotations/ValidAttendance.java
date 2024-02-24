package dev.archimedes.utils.validators.annotations;

import dev.archimedes.utils.validators.AttendanceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttendanceValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAttendance {
    String message() default "Invalid Attendance type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
