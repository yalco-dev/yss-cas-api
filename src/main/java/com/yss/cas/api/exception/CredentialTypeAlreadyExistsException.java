package com.yss.cas.api.exception;


public class CredentialTypeAlreadyExistsException extends RuntimeException{

    public CredentialTypeAlreadyExistsException(String message) {
        super(message);
    }
}
