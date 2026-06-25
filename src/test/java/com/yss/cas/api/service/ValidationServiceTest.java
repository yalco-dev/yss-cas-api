package com.yss.cas.api.service;

import com.yss.cas.api.exception.ValidationException;
import com.yss.cas.api.model.UserRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @InjectMocks
     private ValidationService validationService;

    @Test
    void shouldAcceptValidPassword() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("Password1!");
        request.setConfirmedPassword("Password1!");

        assertDoesNotThrow(() ->
                validationService.validateUserRegistrationRequest(request));
    }

    @Test
    void shouldFailWhenPasswordIsNull() {

        UserRegistrationRequest request = validRequest();
        request.setPassword(null);

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );

        assertEquals(
                "Password is required",
                exception.getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordIsBlank() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("   ");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );

        assertEquals(
                "Password is required",
                exception.getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordIsTooShort() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("Pas1!");
        request.setConfirmedPassword("Pas1!");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );

        assertEquals(
                "Password must be between 8 and 60 characters",
                exception.getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordHasNoUppercaseLetter() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("password1!");
        request.setConfirmedPassword("password1!");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );

        assertEquals(
                "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character",
                exception.getMessage()
        );
    }

    @Test
    void shouldFailWhenPasswordHasNoLowercaseLetter() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("PASSWORD1!");
        request.setConfirmedPassword("PASSWORD1!");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );
    }

    @Test
    void shouldFailWhenPasswordHasNoDigit() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("Password!");
        request.setConfirmedPassword("Password!");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );
    }

    @Test
    void shouldFailWhenPasswordHasNoSpecialCharacter() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("Password1");
        request.setConfirmedPassword("Password1");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );
    }

    @Test
    void shouldFailWhenPasswordsDoNotMatch() {

        UserRegistrationRequest request = validRequest();
        request.setPassword("Password1!");
        request.setConfirmedPassword("Password2!");

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validationService.validateUserRegistrationRequest(request)
                );

        assertEquals(
                "Passwords do not match",
                exception.getMessage()
        );
    }

    private UserRegistrationRequest validRequest() {

        UserRegistrationRequest request =
                new UserRegistrationRequest();

        request.setEmail("john.doe@test.com");
        request.setPassword("Password1!");
        request.setConfirmedPassword("Password1!");
        request.setPhoneNumber("+359888123456");
        request.setFirstName("John");
        request.setLastName("Doe");

        return request;
    }

}