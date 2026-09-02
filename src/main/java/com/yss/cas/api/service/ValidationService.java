package com.yss.cas.api.service;

import com.yss.cas.api.exception.ValidationException;
import com.yss.cas.api.model.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidationService {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 60;
    private static final int NAME_MIN_LENGTH = 2;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[1-9]\\d{7,14}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$"
            );

    public void validateUserRegistrationRequest(UserRegistrationRequest request) {
        if (request == null) {
            throw new ValidationException("Request cannot be null");
        }
        validatePassword(request.getPassword(), request.getConfirmedPassword());
        validateEmail(request.getEmail());
        validatePhoneNumber(request.getPhoneNumber());
        validateFirstName(request.getFirstName());
        validateLastName(request.getLastName());
    }

    private void validatePassword(String password, String confirmedPassword) {

        if (!StringUtils.hasText(password)) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new ValidationException(String.format("Password must be between %d and %d characters", PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH));
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new ValidationException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }

        if (!password.equals(confirmedPassword)) {
            throw new ValidationException("Passwords do not match");
        }
    }

    private void validateEmail(String email) {

        if (!StringUtils.hasText(email)) {
            throw new ValidationException("Email is required");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            throw new ValidationException("Phone number is required");
        }
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ValidationException("Invalid phone number format");
        }
    }

    private void validateFirstName(String firstName) {
        if (!StringUtils.hasText(firstName)) {
            throw new ValidationException("First name is required");
        }
        if (firstName.length() < NAME_MIN_LENGTH) {
            throw new ValidationException("First name length must be bigger than 1 characters");
        }
    }

    private void validateLastName(String lastName) {
        if (!StringUtils.hasText(lastName)) {
            throw new ValidationException("Last name is required");
        }
        if (lastName.length() < NAME_MIN_LENGTH) {
            throw new ValidationException("Last name length must be bigger than 1 characters");
        }
    }
}
