package by.emel.anton.controller;

import by.emel.anton.model.dao.exceptions.TherapyDaoException;
import by.emel.anton.model.dao.exceptions.UserDaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDaoException.class)
    public void userDaoExceptionHandler(UserDaoException userDaoException) {
        logger.error(userDaoException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TherapyDaoException.class)
    public void therapyDaoExceptionHandler(TherapyDaoException therapyDaoException) {
        logger.error(therapyDaoException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception exception) {
        logger.error("Controller exception :" + exception.getMessage());
    }

}
