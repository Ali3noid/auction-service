package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongDateException extends RuntimeException {
    public WrongDateException() {
        super();
    }

    public WrongDateException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongDateException(final String message) {
        super(message);
    }

    public WrongDateException(final Throwable cause) {
        super(cause);
    }
}
