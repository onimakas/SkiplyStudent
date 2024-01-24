package com.skiply.student.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class StudentExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(),ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleStudentNotFoundException(StudentNotFoundException ex) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(),ex.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateStudentException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateStudentException(DuplicateStudentException ex) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(),ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorDetails> handleStudentException(StudentException ex) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(),ex.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(ex.getBindingResult()));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<String>> handleBindException(BindException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(ex.getBindingResult()));
    }

    private List<String> getValidationErrors(BindingResult bindingResult) {
        List<String> validationErrors = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String errorMessage = fieldError.getField() + ": " + fieldError.getDefaultMessage();
            validationErrors.add(errorMessage);
        }

        return validationErrors;
    }
}
