package dev.archimedes.utils.validators;

import dev.archimedes.global.enums.RoleType;
import dev.archimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleField, ConstraintValidatorContext constraintValidatorContext) {

        for(RoleType roleType: RoleType.values()){
            if (roleType.name().equals(roleField.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
