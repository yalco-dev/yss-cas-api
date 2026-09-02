package com.yss.cas.api.service;

import com.yss.cas.api.exception.UserAlreadyExistsException;
import com.yss.cas.api.model.UserAccountStatus;
import com.yss.cas.api.model.UserRegistrationRequest;
import com.yss.cas.api.model.entity.UserAccountEntity;
import com.yss.cas.api.repository.UserAccountRepository;
import com.yss.cas.api.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistartionService {

    private final Clock clock;
    private final ValidationService validationService;
    private final UserCredentialService userCredentialService;
    private final UserAccountRepository userAccountRepository;


    @Transactional
    public void registerNewUser(UserRegistrationRequest request) {
        validationService.validateUserRegistrationRequest(request);
        log.info("email {}", request.email());
        String email = normalizeEmail(request.email());
        boolean userExists = userAccountRepository.existsByEmail(email);
        if (userExists) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists", email));
        }
        UserAccountEntity userEntity = new UserAccountEntity();
        userEntity.setEmail(email);
        userEntity.setCreatedAt(clock.instant());
        userEntity.setStatus(UserAccountStatus.PENDING_VERIFICATION);
        userEntity = userAccountRepository.save(userEntity);
        userCredentialService.storeUserPasswordCredential(userEntity, request.password());

        //check whethre user with this email exists
        //send email to confirming email

        // validate phone number by sending SMS // no neded yet
        //validate inout data for SQL injection, XSS and other
        //save user data to user service
        // where to save password and username
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
