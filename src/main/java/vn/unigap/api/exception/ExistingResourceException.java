package vn.unigap.api.exception;

public class ExistingResourceException extends RuntimeException {
    public  ExistingResourceException(String message) {
        super(message);
    }
}