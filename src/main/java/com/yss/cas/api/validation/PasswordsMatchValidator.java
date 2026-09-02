package com.yss.cas.api.validation;

import com.yss.cas.api.model.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, UserRegistrationRequest> {

    @Override
    public boolean isValid(UserRegistrationRequest request, ConstraintValidatorContext context) {
        if (request == null || request.password() == null || request.confirmPassword() == null) {
            return true; // @NotBlank on the fields reports missing values
        }
        if (request.password().equals(request.confirmPassword())) {
            return true;
        }
        // Attach the error to the confirmPassword field so the frontend can show it next to that input.
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("confirmPassword")
                .addConstraintViolation();
        return false;
    }
}
