package dev.archimedes.utils.validators.annotations;

import dev.archimedes.utils.validators.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid date string, date should have pattern: dd:MM:yyyy HH:mm:ss";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
