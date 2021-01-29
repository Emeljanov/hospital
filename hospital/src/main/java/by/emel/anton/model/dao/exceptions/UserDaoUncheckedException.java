package by.emel.anton.model.dao.exceptions;

public class UserDaoUncheckedException extends RuntimeException {
    public UserDaoUncheckedException(String message) {
        super(message);
    }

    public UserDaoUncheckedException() {
    }
}
