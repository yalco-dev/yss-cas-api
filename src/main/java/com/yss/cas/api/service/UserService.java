package com.yss.cas.api.service;

import com.yss.cas.api.model.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ValidationService validationService;


    public void registerNewUser(UserRegistrationRequest request) {
        validationService.validateUserRegistrationRequest(request);
        //check whethre user with this email exists
        //send email to confirming email

        // validate phone number by sending SMS
        //validate inout data for SQL injection, XSS and other
    }
}
