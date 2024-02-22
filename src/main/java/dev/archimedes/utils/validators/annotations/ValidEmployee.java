package dev.archimedes.utils.validators.annotations;


import dev.archimedes.utils.validators.EmployeeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmployee {
    String message() default "Invalid employee type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
