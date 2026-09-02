package com.yss.cas.api.model;

import com.yss.cas.api.validation.PasswordsMatch;
import com.yss.cas.api.validation.ValidationService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordsMatch
public record UserRegistrationRequest (

        @NotBlank(message = "Email is required.")
        @Email(message = "Email must be a valid email address.")
        String email,

        @NotBlank(message = "Password is required.")
        @Pattern(regexp = ValidationService.PASSWORD_REGEX,
                message = "Password must be 12-64 characters and contain an uppercase letter, a lowercase letter, a digit and a special character.")
        String password,

        @NotBlank(message = "Password confirmation is required.")
        String confirmPassword,

        String phoneNumber,

        @NotBlank(message = "First name is required.")
        String firstName,

        @NotBlank(message = "Last name is required.")
        String lastName
) {

}
