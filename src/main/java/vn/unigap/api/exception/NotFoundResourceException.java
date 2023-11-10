package vn.unigap.api.exception;

public class NotFoundResourceException extends RuntimeException {
    public  NotFoundResourceException(String message) {
        super(message);
    }
}