package com.yss.cas.api.repository;

import com.yss.cas.api.model.entity.UserVerificationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerificationCodeEntity, Long> {

    Optional<UserVerificationCodeEntity> findFirstByAccountIdOrderByCreatedAtDesc(Long userId);

    Optional<UserVerificationCodeEntity> findFirstByAccountIdAndClosedAtIsNullOrderByCreatedAtDesc(Long userId);

    @Modifying(flushAutomatically = true)
    @Query("UPDATE UserVerificationCodeEntity c SET c.closedAt = :now where c.user.id = :userId and c.closedAt is null")
    int closeOpenCodes(@Param("userId") Long userId, @Param("now") Instant now);

}
