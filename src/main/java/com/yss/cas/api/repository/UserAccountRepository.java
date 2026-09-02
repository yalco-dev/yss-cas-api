package com.yss.cas.api.repository;

import com.yss.cas.api.model.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserAccountEntity> findByEmail(String email);

}
