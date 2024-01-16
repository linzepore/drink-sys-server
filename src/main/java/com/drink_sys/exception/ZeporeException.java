package com.drink_sys.exception;

public class ZeporeException extends Exception {

    public ZeporeException() {
        super();
    }

    public ZeporeException(String message) {
        super(message);
    }

    public ZeporeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZeporeException(Throwable cause) {
        super(cause);
    }
}

