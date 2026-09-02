package com.yss.cas.api.service;

import com.yss.cas.api.config.AppProperties;
import com.yss.cas.api.model.entity.UserAccountEntity;
import com.yss.cas.api.model.entity.UserVerificationCodeEntity;
import com.yss.cas.api.repository.UserVerificationRepository;
import com.yss.cas.api.service.outbox.OutboxWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class UserVerificationService {

    private static final int VERIFICATION_CODE_HIGH_BOUND = 1_000_000;
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final SecureRandom secureRandom;
    private final UserVerificationRepository codeRepository;
    private final AppProperties properties;
    private final Clock clock;
    private final SecretKeySpec hmacKey;
    private final OutboxWriter outboxWriter;

    public UserVerificationService(SecureRandom secureRandom, UserVerificationRepository codeRepository, AppProperties properties, Clock clock, OutboxWriter outboxWriter) {
        this.secureRandom = secureRandom;
        this.codeRepository = codeRepository;
        this.properties = properties;
        this.clock = clock;
        this.outboxWriter = outboxWriter;
        byte[] pepper = Base64.getDecoder().decode(properties.verificationCode().pepper());
        if (pepper.length < 32) {
            throw new IllegalStateException("app.verification-code.pepper must be at least 32 bytes (Base64-encoded)");
        }
        this.hmacKey = new SecretKeySpec(pepper, HMAC_ALGORITHM);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void issueAndSendVerificationCode(UserAccountEntity user) {
        Instant now = clock.instant();
        long userId = user.getId();
        boolean cooldown = codeRepository.findFirstByAccountIdOrderByCreatedAtDesc(userId)
                .map(last -> last.getCreatedAt().plus(properties.verificationCode().resendDelay()).isAfter(now))
                .orElse(false);
        if (cooldown) {
            log.info("Can not send verification code again for user {}. Resend cooldown active", user.getEmail());
            return;
        }
        codeRepository.closeOpenCodes(userId, now);
        String code = issueCode();
        Instant expiresAt = now.plus(properties.verificationCode().ttl());
        UserVerificationCodeEntity codeEntity = new UserVerificationCodeEntity();
        codeEntity.setUser(user);
        codeEntity.setCodeHash(hash(code));
        codeEntity.setExpiresAt(expiresAt);
        codeEntity.setCreatedAt(now);
        codeRepository.save(codeEntity);
    }

    private String hash(String code) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM); // Mac is not thread-safe -> new instance per call
            mac.init(hmacKey);
            return HexFormat.of().formatHex(mac.doFinal(code.getBytes(StandardCharsets.US_ASCII)));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("HMAC computation failed", e);
        }
    }

    private String issueCode() {
        int code = secureRandom.nextInt(VERIFICATION_CODE_HIGH_BOUND);
        return String.format(Locale.ROOT, "%06d", code);
    }


//    /** Creates a new code (unless the resend cooldown is active) and queues the email event. */
//    @Transactional(propagation = Propagation.MANDATORY)
//    public void issueAndSendVerificationCode(UserAccount account) {
//
//        Instant now = clock.instant();
//
//        boolean inCooldown = codes.findFirstByUserIdOrderByCreatedAtDesc(account.getId())
//                .map(last -> last.getCreatedAt().plus(props.resendCooldown()).isAfter(now))
//                .orElse(false);
//        if (inCooldown) {
//            log.info("Verification code not re-sent for user {}: resend cooldown active", account.getId());
//            return;
//        }
//
//        codes.closeOpenCodes(account.getId(), now); // only the newest code may be valid
//
//        String code = generateCode();
//        Instant expiresAt = now.plus(props.ttl());
//        codes.save(new EmailVerificationCode(account.getId(), hash(code), expiresAt, now));
//
//        outbox.write(Topics.EMAIL_VERIFICATION_REQUESTED, account.getId().toString(),
//                EmailVerificationRequestedEvent.TYPE,
//                new EmailVerificationRequestedEvent(UUID.randomUUID(), now, account.getId(),
//                        account.getEmail(), account.getUsername(), code, expiresAt));
//    }
}
