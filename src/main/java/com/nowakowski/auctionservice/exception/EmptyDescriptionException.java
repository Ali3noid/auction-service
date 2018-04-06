package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyDescriptionException extends RuntimeException {
    public EmptyDescriptionException() {
        super();
    }

    public EmptyDescriptionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmptyDescriptionException(final String message) {
        super(message);
    }

    public EmptyDescriptionException(final Throwable cause) {
        super(cause);
    }
}
