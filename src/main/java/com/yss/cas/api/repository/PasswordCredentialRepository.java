package com.yss.cas.api.repository;

import com.yss.cas.api.model.entity.PasswordCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordCredentialRepository extends JpaRepository<PasswordCredentialEntity, Long> {

    Optional<PasswordCredentialEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
