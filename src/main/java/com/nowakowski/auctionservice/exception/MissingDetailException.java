package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingDetailException extends RuntimeException {
    public MissingDetailException() {
        super();
    }

    public MissingDetailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MissingDetailException(final String message) {
        super(message);
    }

    public MissingDetailException(final Throwable cause) {
        super(cause);
    }
}
