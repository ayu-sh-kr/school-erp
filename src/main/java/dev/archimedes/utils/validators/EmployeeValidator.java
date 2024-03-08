package dev.archimedes.utils.validators;

import dev.archimedes.global.enums.EmployeeType;
import dev.archimedes.utils.validators.annotations.ValidEmployee;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeValidator implements ConstraintValidator<ValidEmployee, String> {
    @Override
    public void initialize(ValidEmployee constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String employee, ConstraintValidatorContext constraintValidatorContext) {

        if(StringUtils.isBlank(employee)){
            throw new RuntimeException("Employee type field is null");
        }
        for(EmployeeType employeeType: EmployeeType.values()){
            if(employeeType.name().equals(employee.toUpperCase())){
                return true;
            }
        }

        return false;
    }
}
