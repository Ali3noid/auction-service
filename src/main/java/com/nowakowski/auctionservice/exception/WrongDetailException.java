package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongDetailException extends RuntimeException {
    public WrongDetailException() {
        super();
    }

    public WrongDetailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongDetailException(final String message) {
        super(message);
    }

    public WrongDetailException(final Throwable cause) {
        super(cause);
    }
}
