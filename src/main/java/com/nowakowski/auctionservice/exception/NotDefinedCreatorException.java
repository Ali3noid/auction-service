package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotDefinedCreatorException extends RuntimeException {
    public NotDefinedCreatorException() {
        super();
    }

    public NotDefinedCreatorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotDefinedCreatorException(final String message) {
        super(message);
    }

    public NotDefinedCreatorException(final Throwable cause) {
        super(cause);
    }
}
