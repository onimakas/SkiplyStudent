package com.skiply.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StudentException extends RuntimeException {

    public StudentException(String message) {
        super(message);
    }

}