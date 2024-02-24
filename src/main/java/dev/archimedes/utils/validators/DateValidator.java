package dev.archimedes.utils.validators;

import dev.archimedes.utils.validators.annotations.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");

        try {
            dateFormat.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
