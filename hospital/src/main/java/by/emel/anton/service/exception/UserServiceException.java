package by.emel.anton.service.exception;

public class UserServiceException extends RuntimeException{
    public UserServiceException() {
    }

    public UserServiceException(String message) {
        super(message);
    }

}
