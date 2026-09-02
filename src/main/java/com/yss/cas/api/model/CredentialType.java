package com.yss.cas.api.model;

/**
 * Kinds of login credentials a user account can have.
 * The values are stored in the "type" discriminator column of YSS_USER_ACCOUNT_CREDENTIALS.
 */
public enum CredentialType {
    PASSWORD,
    OAUTH;

    /** Compile-time constants for @DiscriminatorValue (annotations can't use enum names directly). */
    public static final class Values {
        public static final String PASSWORD = "PASSWORD";
        public static final String OAUTH = "OAUTH";

        private Values() {
        }
    }
}
