package vn.unigap.common.exception;

import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vn.unigap.common.CustomResponse;
import vn.unigap.common.EnumStatusCode;

//https://howtodoinjava.com/spring-boot/logging-with-lombok/

@Log4j2
@ControllerAdvice
public class ApiExceptionHandler {
//extends ResponseEntityExceptionHandler


    // THE CODE TO HANDLE SPRING VALIDATION
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException e) {
//        Map<String, String> fieldsError = new HashMap<>();
//        fieldsError.put("message", "Error when validation");
        for (FieldError fieldError : e.getFieldErrors()) {
//            fieldsError.put(fieldError.getField(), fieldError.getDefaultMessage());
            captureException(e, HttpStatus.BAD_REQUEST);
        }
        return responseEntity(EnumStatusCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //THE CODE HANDLE ApiException
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        captureException(e, HttpStatus.NOT_FOUND);

        return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
    }

    private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
        return new ResponseEntity<>(
                CustomResponse.builder()
                        .errorCode(errorCode)
                        .statusCode(statusCode.value())
                        .message(msg)
                        .build(),
                statusCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        // Call your captureException method
        captureException(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        // You can customize the response based on your requirements
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void captureException(Exception e, HttpStatusCode status) {
        SentryEvent event = new SentryEvent(e);
        if (status.is5xxServerError()) {
            event.setLevel(SentryLevel.ERROR);
            log.error("Internal exception occurred:", e);
        } else if (status.is4xxClientError()) {
            event.setLevel(SentryLevel.INFO);
            log.info("Debug exception occurred:", e);
        }
        Sentry.captureEvent(event);
    }

}
