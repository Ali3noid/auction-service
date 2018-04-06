package com.nowakowski.auctionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPriceException extends RuntimeException {
    public WrongPriceException() {
        super();
    }

    public WrongPriceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongPriceException(final String message) {
        super(message);
    }

    public WrongPriceException(final Throwable cause) {
        super(cause);
    }
}
