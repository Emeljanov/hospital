package by.emel.anton.controller;

import by.emel.anton.model.dao.exceptions.TherapyDaoUncheckedException;
import by.emel.anton.model.dao.exceptions.UserDaoUncheckedException;
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
    @ExceptionHandler(UserDaoUncheckedException.class)
    public void userDaoExceptionHandler(UserDaoUncheckedException userDaoUncheckedException) {
        LOGGER.error(userDaoUncheckedException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TherapyDaoUncheckedException.class)
    public void therapyDaoExceptionHandler(TherapyDaoUncheckedException therapyDaoUncheckedException) {
        LOGGER.error(therapyDaoUncheckedException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception exception) {
        LOGGER.error("Controller exception :" + exception.getMessage());
    }

}
