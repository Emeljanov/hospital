package by.emel.anton.controller;

import by.emel.anton.model.dao.exceptions.TherapyDAOException;
import by.emel.anton.model.dao.exceptions.UserDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDAOException.class)
    public void userDaoExceptionHandler(UserDAOException userDAOException) {
        LOGGER.error(userDAOException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TherapyDAOException.class)
    public void therapyDaoExceptionHandler(TherapyDAOException therapyDAOException) {
        LOGGER.error(therapyDAOException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception exception) {
        LOGGER.error("Controller exception :" + exception.getMessage());
    }

}
