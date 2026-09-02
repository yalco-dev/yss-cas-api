package com.yss.cas.api.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;


public class CustomResponseException extends ResponseStatusException {
    public CustomResponseException(HttpStatusCode status) {
        super(status);
    }

    public CustomResponseException(HttpStatusCode status, @Nullable String reason) {
        super(status, reason);
    }

    public CustomResponseException(int rawStatusCode, @Nullable String reason, @Nullable Throwable cause) {
        super(rawStatusCode, reason, cause);
    }

    public CustomResponseException(HttpStatusCode status, @Nullable String reason, @Nullable Throwable cause) {
        super(status, reason, cause);
    }

    protected CustomResponseException(HttpStatusCode status, @Nullable String reason, @Nullable Throwable cause, @Nullable String messageDetailCode, Object @Nullable [] messageDetailArguments) {
        super(status, reason, cause, messageDetailCode, messageDetailArguments);
    }
}
