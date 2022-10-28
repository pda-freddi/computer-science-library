package com.pdafr.computer.science.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQueryParameterException extends RuntimeException {
  
    public InvalidQueryParameterException(String message) {
        super(message);
    }
  
}
