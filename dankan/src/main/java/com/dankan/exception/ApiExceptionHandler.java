package com.dankan.exception;

import com.dankan.exception.post.PostNotFoundException;
import com.dankan.exception.report.PostReportNotFoundException;
import com.dankan.exception.report.ReviewReportNotFoundException;
import com.dankan.exception.review.ReviewNotFoundException;
import com.dankan.exception.room.RoomImageNotFoundException;
import com.dankan.exception.room.RoomNotFoundException;
import com.dankan.exception.token.TokenNotFoundException;
import com.dankan.exception.type.*;
import com.dankan.exception.user.UserIdNotFoundException;
import com.dankan.exception.user.UserNameNotFoundException;
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

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0004","Post is not found : post id or address is "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(RoomNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0005","Room is not found : room id or address is "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ReviewNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0006", "Review is not found : review id is " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostReportNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PostReportNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0007", ex.getMessage() + " is duplicated");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewReportNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(ReviewReportNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0008","Review Report is not found : review id is "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPriceTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidPriceTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0009","Invalid price type : "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRoomTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidRoomTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0010","Invalid room type : "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRoomImageTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidRoomImageTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0011","Invalid room image type : "+ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSatisfyException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidSatisfyException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0012", "Invalid satisfy : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameExistException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserNameExistException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0013", "member name: " + ex.getMessage() + " is duplicated");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(EmailNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0014", "member email: " + ex.getMessage() + " is not exit");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PhoneNumberNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(PhoneNumberNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0015", "member phone number: " + ex.getMessage() + " is not exit");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomImageNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleException(RoomImageNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0016", "failed to find room image from room id :  " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOptionTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidOptionTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0017", "Invalid option type : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEtcOptionTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidEtcOptionTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0018", "Invalid option type : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidManagementTypeException.class)
    public ResponseEntity<ApiErrorResponse> handleException(InvalidManagementTypeException ex) {
        ApiErrorResponse response = new ApiErrorResponse("ERROR-0019", "Invalid management type : " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
