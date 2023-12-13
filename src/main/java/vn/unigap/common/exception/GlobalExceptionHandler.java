package vn.unigap.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // THE CODE TO HANDLE SPRING VALIDATION
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {
        Map<String, String> fieldsError = new HashMap<>();
        fieldsError.put("message", "Error when validation");
        for (FieldError fieldError : e.getFieldErrors()) {
            fieldsError.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldsError);
    }

    //THE CODE HANDLE CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customInvalidExceptionHandler(CustomException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomResponse.noDataResponse(e.getErrorCode(), e.getHttpStatus(), e.getErrorMessage()));
    }

}
