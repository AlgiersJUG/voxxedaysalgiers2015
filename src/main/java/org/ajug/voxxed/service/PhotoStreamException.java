package org.ajug.voxxed.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Abderrazak BOUADMA
 * Date: 4/1/15
 * Time: 11:25 AM
 */

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PhotoStreamException extends RuntimeException {

    public PhotoStreamException(String message) {
        super(message);
    }

    public PhotoStreamException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
