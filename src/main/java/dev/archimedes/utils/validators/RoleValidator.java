package dev.archimedes.utils.validators;

import dev.archimedes.enums.RoleType;
import dev.archimedes.utils.validators.annotations.ValidRole;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(String roleField, ConstraintValidatorContext constraintValidatorContext) {

        if(StringUtils.isBlank(roleField)){
            throw new RuntimeException("Role Field is null");
        }

        for(RoleType roleType: RoleType.values()){
            if (roleType.name().equals(roleField.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
