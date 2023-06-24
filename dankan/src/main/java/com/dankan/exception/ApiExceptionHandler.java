package com.dankan.exception;

import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.exception.user.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNameNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0001","User is not found : nickname is "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserIdNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0002","User is not found : user id is "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(TokenNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0003","Token is not found : user id is " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNameExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0004", "member name: " + ex.getMessage() + " is duplicated");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(EmailNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0005", "member email: " + ex.getMessage() + " is not exit");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhoneNumberNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PhoneNumberNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0005", "member phone number: " + ex.getMessage() + " is not exit");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
