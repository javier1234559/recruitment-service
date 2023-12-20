package vn.unigap.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {
    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ApiException(Integer errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = message;
    }
}