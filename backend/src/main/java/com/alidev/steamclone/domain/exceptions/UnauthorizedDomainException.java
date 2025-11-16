package com.alidev.steamclone.domain.exceptions;

public class UnauthorizedDomainException extends DomainException {
    public UnauthorizedDomainException(String message) {
        super(message);
    }
}
