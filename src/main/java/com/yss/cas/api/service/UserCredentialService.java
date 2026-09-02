package com.yss.cas.api.service;

import com.yss.cas.api.exception.CredentialTypeAlreadyExistsException;
import com.yss.cas.api.model.entity.PasswordCredentialEntity;
import com.yss.cas.api.model.entity.UserAccountEntity;
import com.yss.cas.api.repository.PasswordCredentialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

/**
 * The only place that creates, changes or removes credentials, so the rules live in one spot:
 * - at most one password per account
 * - one account per external identity, at most one link per provider per account
 * - an account must always keep at least one way to log in
 * The database enforces the same rules with unique indexes, as a safety net against races.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialService {

    private final PasswordEncoder passwordEncoder;
    private final PasswordCredentialRepository passwordRepository;
    private final Clock clock;

    @Transactional
    public void storeUserPasswordCredential(UserAccountEntity userAccount, String rawPassword) {
        if (passwordRepository.existsByUserId(userAccount.getId())) {
            throw new CredentialTypeAlreadyExistsException("This account already has a password.");
        }
        log.info("Encoding password for user with email {}", userAccount.getEmail());
        String encodedPassword = passwordEncoder.encode(rawPassword);
        PasswordCredentialEntity passwordEntity = new PasswordCredentialEntity();
        passwordEntity.setPassword(encodedPassword);
        passwordEntity.setCreatedAt(clock.instant());
        passwordEntity.setUser(userAccount);
        passwordRepository.save(passwordEntity);
    }
}
